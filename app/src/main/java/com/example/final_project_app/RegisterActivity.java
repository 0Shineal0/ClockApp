package com.example.final_project_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;
public class RegisterActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button registerButton,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        back = findViewById(R.id.buttonCancel);
        usernameEditText = findViewById(R.id.edLogin);
        passwordEditText = findViewById(R.id.edPassword);
        registerButton = findViewById(R.id.buttonLogin);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    // Lấy danh sách tài khoản đăng ký từ SharedPreferences
                    SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    Set<String> registeredAccounts = preferences.getStringSet("accounts", new HashSet<>());

                    // Kiểm tra xem tài khoản đã tồn tại chưa
                    if (registeredAccounts.contains(username)) {
                        Toast.makeText(RegisterActivity.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Thêm tài khoản mới vào danh sách
                        registeredAccounts.add(username);

                        // Lưu lại danh sách tài khoản vào SharedPreferences
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putStringSet("accounts", registeredAccounts);
                        editor.putString(username, password); // Lưu mật khẩu theo tên tài khoản
                        editor.apply();

                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        finish(); // Kết thúc Activity đăng ký và quay lại Activity trước đó (MainActivity)
                    }
                }
            }
        });
    }
}