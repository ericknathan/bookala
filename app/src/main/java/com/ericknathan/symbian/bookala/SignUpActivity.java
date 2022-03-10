package com.ericknathan.symbian.bookala;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ericknathan.symbian.bookala.database.SQLiteProvider;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameInput;
    private EditText surnameInput;
    private EditText usernameInput;
    private EditText passwordInput;
    private EditText passwordConfirmInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameInput = findViewById(R.id.input_user_name);
        surnameInput = findViewById(R.id.input_user_surname);
        usernameInput = findViewById(R.id.input_user_username);
        passwordInput = findViewById(R.id.input_user_password);
        passwordConfirmInput = findViewById(R.id.input_user_confirm_password);
        Button redirectToAddressActivityButton = findViewById(R.id.button_address_register);

        nameInput.setText("Erick");
        surnameInput.setText("Nathan");
        usernameInput.setText("ericknathan");
        passwordInput.setText("12345");
        passwordConfirmInput.setText("12345");

        redirectToAddressActivityButton.setOnClickListener(view -> {
            String nameInputValue = nameInput.getText().toString();
            String surnameInputValue = surnameInput.getText().toString();
            String usernameInputValue = usernameInput.getText().toString();
            String passwordInputValue = passwordInput.getText().toString();
            String confirmPasswordInputValue = passwordConfirmInput.getText().toString();

            boolean isValid = validate(
                    nameInputValue,
                    surnameInputValue,
                    usernameInputValue,
                    passwordInputValue,
                    confirmPasswordInputValue
            );

            if(isValid) {
                long registeredUser = SQLiteProvider.getInstance(this).addUser(
                        nameInputValue,
                        surnameInputValue,
                        usernameInputValue,
                        passwordInputValue
                );

                if(registeredUser != 0) {
                    Intent intent  = new Intent(SignUpActivity.this, RegisterAddressActivity.class);
                    intent.putExtra("cod_usuario", Integer.parseInt(String.valueOf(registeredUser)));
                    intent.putExtra("back_activity", "signin");
                    startActivity(intent);

                    Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(
                            SignUpActivity.this,
                            SignInActivity.class
                    ));
                    Toast.makeText(this, "Ocorreu um erro ao cadastrar usuário", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validate(String name, String surname, String username, String password, String confirmPassword) {
        if(name.isEmpty() || surname.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos.", Toast.LENGTH_LONG).show();
            return false;
        } else if(!password.equals(confirmPassword)) {
            Toast.makeText(this,"As senhas não coincidem.", Toast.LENGTH_LONG).show();
            return false;
        } else if(SQLiteProvider.getInstance(this).usernameAlreadyExists(username)) {
            Toast.makeText(this,"Este usuário já está cadastrado em minha base de dados!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}