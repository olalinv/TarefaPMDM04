package com.robottitto.tarefapmdm04.ui.user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.robottitto.tarefapmdm04.api.core.AppSharedPreferences;
import com.robottitto.tarefapmdm04.api.user.UserModelService;
import com.robottitto.tarefapmdm04.api.user.model.User;
import com.robottitto.tarefapmdm04.ui.core.ActivityUtil;
import com.robottitto.tarefapmdm04.ui.core.CameraUtil;
import com.robottitto.tarefapmdm04.ui.core.ImageUtil;

public class EditProfileActivity extends CameraUtil {

    private final static String TAG = "EditProfileActivity";
    private final static String ADMIN_ROLE = "Administrador";
    private final static String CLIENT_ROLE = "Cliente";
    private final static String[] roles = {CLIENT_ROLE, ADMIN_ROLE};
    private Context context;
    private UserModelService userModelService;
    private User user;

    private ImageView ivImage;
    private Button btLoadImage;
    private EditText etName;
    private EditText etSurname;
    private EditText etEmail;
    private EditText etUsername;
    private EditText etPassword1;
    private EditText etPassword2;
    private Spinner spRole;
    private Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // View elements
        ivImage = findViewById(R.id.ivImage);
        btLoadImage = findViewById(R.id.btLoadImage);
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword1 = findViewById(R.id.etPassword1);
        etPassword2 = findViewById(R.id.etPassword2);
        spRole = findViewById(R.id.spRole);
        btSave = findViewById(R.id.btSave);

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
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String surname = etSurname.getText().toString();
                String email = etEmail.getText().toString();
                String password1 = etPassword1.getText().toString();
                String password2 = etPassword2.getText().toString();
                int role = spRole.getSelectedItemPosition();
                byte[] image = ImageUtil.imageViewToArray(ivImage);
                // TODO Add more fields validations
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(surname) && !TextUtils.isEmpty(email)) {
                    if (!password1.equals(password2)) {
                        showToast(getString(R.string.error_password_not_match));
                    } else {
                        user.setName(name);
                        user.setSurname(surname);
                        user.setEmail(email);
                        if (!"".equals(password1)) {
                            user.setPassword(password1);
                        }
                        user.setRole(role);
                        user.setImage(image);
                        try {
                            int result = userModelService.updateUser(user);
                            if (result > 0) {
                                showToast(getString(R.string.user_updated));
                                showToast(userModelService.getUser(user.getUsername()).toString());
                                go(ProfileActivity.class, null, null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showToast(getString(R.string.error) + ": " + e.getMessage());
                        }
                    }
                } else {
                    showToast(getString(R.string.error_empty_fields));
                }
            }
        });

        // Initialization
        userModelService = UserModelService.get(this);
        try {
            user = userModelService.findUserById(getUserDetails("UID"));
            if (null != user) {
                if (null != user.getImage()) {
                    ivImage.setImageBitmap(ImageUtil.arrayToImageView(user.getImage()));
                }
                etName.setText(user.getName());
                etSurname.setText(user.getSurname());
                etEmail.setText(user.getEmail());
                etUsername.setText(user.getUsername());
                spRole.setSelection(user.getRole());
            }
        } catch (Exception e) {
            e.printStackTrace();
            ActivityUtil.showToast(context, getString(R.string.error) + ": " + e.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void loadImage() {
        requestPermission();
        final CharSequence[] opciones = {"Tomar Foto", "Cargar Imagen", "Cancelar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(EditProfileActivity.this);
        alertOpciones.setTitle("Seleccione una opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")) {
                    loadImageFromCamera();
                } else {
                    if (opciones[i].equals("Cargar Imagen")) {
                        loadImageFromSD();
                    } else {
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();
    }

    private int getUserDetails(String key) {
        SharedPreferences sharedPref = getSharedPreferences(AppSharedPreferences.PREFS_NAME, Context.MODE_PRIVATE);
        int uid = sharedPref.getInt(key, 0);
        return uid;
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
