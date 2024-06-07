package com.example.final_project_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Button b1, b2;
    EditText ed1;
    EditText ed2;

    CheckBox rememberme;
    TextView register,forgetpass;
    int counter = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register = findViewById(R.id.textviewRegister);
        b1 = findViewById(R.id.buttonLogin);
        ed1 = findViewById(R.id.edLogin);
        ed2 = findViewById(R.id.edPassword);
        rememberme = findViewById(R.id.checkBox);
        b2 = findViewById(R.id.buttonCancel);
        forgetpass = findViewById(R.id.textviewForgetPass);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean rememberMe = preferences.getBoolean("rememberMe", false);

        // Nếu đã đăng nhập trước đó và tích vào "Remember Me", tự động điền thông tin đăng nhập
        if (rememberMe) {
            String savedUsername = preferences.getString("username", "");
            String savedPassword = preferences.getString("password", "");
            ed1.setText(savedUsername);
            ed2.setText(savedPassword);
        }

        // Trong phần onClick của nút b1 (buttonLogin)
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin đăng nhập từ người dùng
                String username = ed1.getText().toString();
                String password = ed2.getText().toString();

                // Kiểm tra thông tin đăng nhập
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    // Lấy mật khẩu từ SharedPreferences
                    SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    String savedPassword = preferences.getString(username, "");

                    // Kiểm tra trạng thái của ô Remember Me
                    boolean rememberMe = rememberme.isChecked();

                    if (password.equals(savedPassword)) {
                        Toast.makeText(getApplicationContext(),
                                "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));

                        // Lưu thông tin đăng nhập nếu ô Remember Me đã được tích vào
                        if (rememberMe) {
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("rememberMe", true);
                            editor.putString("username", username);
                            editor.putString("password", password);
                            editor.apply();
                        } else {
                            // Nếu không tích vào Remember Me, xoá thông tin đăng nhập từ SharedPreferences
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("rememberMe", false);
                            editor.remove("username");
                            editor.remove("password");
                            editor.apply();
                        }

                        // Redirect đến Activity khác (hoặc thực hiện các hành động khác tùy ý)
                    } else {
                        counter--;
                        if (counter >= 0) {
                            Toast.makeText(getApplicationContext(),
                                    "Số lần thử còn lại: " + counter, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Hết số lần thử!", Toast.LENGTH_SHORT).show();
                            b1.setEnabled(false);
                        }
                    }
                }
            }
        });




        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgetPassActivity.class));
            }
        });
    }
}
