package com.ericknathan.symbian.bookala;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ericknathan.symbian.bookala.database.SQLiteProvider;
import com.ericknathan.symbian.bookala.helpers.LoginHelper;

public class SignInActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        usernameInput = findViewById(R.id.input_user_username);
        passwordInput = findViewById(R.id.input_user_password);
        Button singInButton = findViewById(R.id.button_user_signin);
        Button singUpButton = findViewById(R.id.button_user_signup);

        usernameInput.setText("ericknathan");
        passwordInput.setText("12345");

        singInButton.setOnClickListener(view -> {
            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();
            int userId = SQLiteProvider.getInstance(this).login(username, password);

            if(userId > 0) {
                LoginHelper.setUserId(userId);
                startActivity(new Intent(
                        SignInActivity.this,
                        HomeActivity.class
                ));
            } else {
                Toast.makeText(this, "Usuário ou senha incorretos!", Toast.LENGTH_LONG).show();
            }

        });

        singUpButton.setOnClickListener(view -> {
            startActivity(new Intent(
                    SignInActivity.this,
                    SignUpActivity.class
            ));
        });
    }
}