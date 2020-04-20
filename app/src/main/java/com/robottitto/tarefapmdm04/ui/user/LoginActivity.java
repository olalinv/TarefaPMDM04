package com.robottitto.tarefapmdm04.ui.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.robottitto.tarefapmdm04.R;
import com.robottitto.tarefapmdm04.api.core.AppSharedPreferences;
import com.robottitto.tarefapmdm04.api.user.UserModelService;
import com.robottitto.tarefapmdm04.api.user.enums.Role;
import com.robottitto.tarefapmdm04.api.user.model.User;
import com.robottitto.tarefapmdm04.ui.core.ActivityUtil;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private final static String TAG = "LoginActivity";
    private static Context context;
    private UserModelService userModelService;
    private EditText etUsername;
    private EditText etPassword;
    private Button btLogin;
    private Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = getApplicationContext();

        // Initialization
        userModelService = UserModelService.get(this);

        // View elements
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btRegister);

        // Events
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                // TODO Add more fields validations
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    try {
                        User user = userModelService.getUser(username);
                        if (null != user) {
                            if (user.getPassword().equals(password)) {
                                saveUserDetails(user.getUserId());
                                if (Role.CUSTOMER.getRole() == user.getRole()) {
                                    ActivityUtil.go(context, ProfileActivity.class);
                                }
                                if (Role.ADMIN.getRole() == user.getRole()) {
                                    ActivityUtil.go(context, ProfileActivity.class);
                                }
                            } else {
                                ActivityUtil.showToast(context, getString(R.string.error_invalid_credentials));
                            }
                        } else {
                            ActivityUtil.showToast(context, getString(R.string.error_invalid_credentials));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ActivityUtil.showToast(context, getString(R.string.error) + ": " + e.getMessage());
                    }
                } else {
                    ActivityUtil.showToast(context, getString(R.string.error_empty_fields));
                }
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.go(context, RegisterActivity.class);
            }
        });
    }

    private void saveUserDetails(int uid) {
        SharedPreferences sharedPref = getSharedPreferences(AppSharedPreferences.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("UID", uid);
        editor.apply();
    }

}
