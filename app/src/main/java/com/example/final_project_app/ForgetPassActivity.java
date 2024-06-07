package com.example.final_project_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgetPassActivity extends AppCompatActivity {
    EditText usernameEditText, newPasswordEditText;
    Button confirmButton, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fogetpass_activity);

        usernameEditText = findViewById(R.id.edLogin);
        newPasswordEditText = findViewById(R.id.edPassword);
        confirmButton = findViewById(R.id.buttonChange);
        back = findViewById(R.id.buttonCancel);
        newPasswordEditText.setVisibility(View.GONE);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(ForgetPassActivity.this, "Vui lòng nhập tên đăng nhập!", Toast.LENGTH_SHORT).show();
                } else {
                    // Lấy mật khẩu từ SharedPreferences
                    SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    String savedPassword = preferences.getString(username, "");

                    if (TextUtils.isEmpty(savedPassword)) {
                        Toast.makeText(ForgetPassActivity.this, "Tài khoản không tồn tại!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Xác nhận mật khẩu thành công, hiển thị trường nhập mật khẩu mới
                        Toast.makeText(ForgetPassActivity.this, "Xác nhận thành công!", Toast.LENGTH_SHORT).show();
                        usernameEditText.setVisibility(View.GONE);
                        newPasswordEditText.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Nếu đang trong chế độ hiển thị trường nhập mật khẩu mới
                if (newPasswordEditText.getVisibility() == View.VISIBLE) {
                    String username = usernameEditText.getText().toString();
                    String newPassword = newPasswordEditText.getText().toString();

                    if (TextUtils.isEmpty(newPassword)) {
                        Toast.makeText(ForgetPassActivity.this, "Vui lòng nhập mật khẩu mới!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Lưu mật khẩu mới vào SharedPreferences
                        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(username, newPassword);
                        editor.apply();

                        Toast.makeText(ForgetPassActivity.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                        finish(); // Kết thúc Activity và quay lại Activity trước đó (MainActivity hoặc LoginActivity)
                    }
                } else {
                    startActivity(new Intent(ForgetPassActivity.this, LoginActivity.class));
                }
            }
        });
    }
}