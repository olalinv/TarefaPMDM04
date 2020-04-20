package com.robottitto.tarefapmdm04.ui.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.robottitto.tarefapmdm04.R;
import com.robottitto.tarefapmdm04.api.user.UserModelService;
import com.robottitto.tarefapmdm04.api.user.model.User;
import com.robottitto.tarefapmdm04.ui.core.CameraUtil;
import com.robottitto.tarefapmdm04.ui.core.ImageUtil;

public class RegisterActivity extends CameraUtil {

    private final static String TAG = "RegisterActivity";
    private final static String ADMIN_ROLE = "Administrador";
    private final static String CLIENT_ROLE = "Cliente";
    private final static String[] roles = {CLIENT_ROLE, ADMIN_ROLE};
    private UserModelService userModelService;

    private ImageView ivImage;
    private Button btLoadImage;
    private EditText etName;
    private EditText etSurname;
    private EditText etEmail;
    private EditText etUsername;
    private EditText etPassword;
    private Spinner spRole;
    private Button btCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialization
        userModelService = UserModelService.get(this);

        // View elements
        ivImage = findViewById(R.id.ivImage);
        btLoadImage = findViewById(R.id.btLoadImage);
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        spRole = findViewById(R.id.spRole);
        btCreateAccount = findViewById(R.id.btCreateAccount);

        // Spinner options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRole.setAdapter(adapter);

        // Events
        btLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage();
            }
        });
        btCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String surname = etSurname.getText().toString();
                String email = etEmail.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                int role = spRole.getSelectedItemPosition();
                byte[] image = ImageUtil.imageViewToArray(ivImage);
                // TODO Add more fields validations
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(surname) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    User user = new User(name, surname, email, username, password, role, image);
                    try {
                        long result = userModelService.addUser(user);
                        if (result > 0) {
                            showToast(getString(R.string.user_created));
                            showToast(userModelService.getUser(user.getUsername()).toString());
                            go(LoginActivity.class, null, null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showToast(getString(R.string.error) + ": " + e.getMessage());
                    }
                } else {
                    showToast(getString(R.string.error_empty_fields));
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void loadImage() {
        requestPermission();
        final CharSequence[] options = {getString(R.string.take_photo), getString(R.string.load_image), getString(R.string.cancel)};
        final AlertDialog.Builder alertOptions = new AlertDialog.Builder(RegisterActivity.this);
        alertOptions.setTitle(getString(R.string.choose_option));
        alertOptions.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (options[i].equals(getString(R.string.take_photo))) {
                    loadImageFromCamera();
                } else {
                    if (options[i].equals(getString(R.string.load_image))) {
                        loadImageFromSD();
                    } else {
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOptions.show();
    }

    private void go(Class<?> activityClass, String paramId, String paramValue) {
        Intent intent = new Intent(this, activityClass);
        if (null != paramId) {
            intent.putExtra(paramId, paramValue);
        }
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast toast =
                Toast.makeText(getApplicationContext(),
                        message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 120);
        toast.show();
    }

}
