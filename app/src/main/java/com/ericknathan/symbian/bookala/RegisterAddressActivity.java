package com.ericknathan.symbian.bookala;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ericknathan.symbian.bookala.database.SQLiteProvider;

public class RegisterAddressActivity extends AppCompatActivity {

    private EditText cepInput;
    private EditText numberInput;
    private EditText complementInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_address);

        cepInput = findViewById(R.id.input_address_cep);
        numberInput = findViewById(R.id.input_address_number);
        complementInput = findViewById(R.id.input_address_complement);
        Button finishRegisterButton = findViewById(R.id.button_user_finish_register);

        cepInput.setText("06331-110");
        numberInput.setText("295");
        complementInput.setText("Teste");

        finishRegisterButton.setOnClickListener(view -> {
            String cepInputValue = cepInput.getText().toString();
            String numberInputValue = numberInput.getText().toString();
            String complementInputValue = complementInput.getText().toString();
            Bundle extras = getIntent().getExtras();
            int userId = extras.getInt("cod_usuario");
            String backActivity = extras.getString("back_activity");

            boolean isValid = validate(
                    cepInputValue,
                    numberInputValue
            );

            if(isValid) {
                AlertDialog dialogBuilder = new AlertDialog.Builder(this)
                        .setTitle("Cadastro de endereço")
                        .setMessage("Deseja cadastrar o endereço de CEP " + cepInputValue + "?")
                        .setPositiveButton(
                                "Cadastrar",
                                (dialog, which) -> {
                                    boolean registeredAddress = SQLiteProvider.getInstance(this).registerAddress(
                                            userId,
                                            cepInputValue,
                                            numberInputValue,
                                            complementInputValue
                                    );

                                    Class redirectActivity = !backActivity.equals("signin") ? HomeActivity.class : SignInActivity.class;

                                    if(registeredAddress) {
                                        startActivity(new Intent(
                                                RegisterAddressActivity.this,
                                                redirectActivity
                                        ));
                                        Toast.makeText(this, "Endereço cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(this, "Não foi possível cadastrar endereço, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                                    }

                                    dialog.cancel();
                                }
                        )
                        .setNegativeButton(
                                "Cancelar",
                                (dialog, which) -> {
                                    dialog.cancel();
                                }
                        ).create();

                dialogBuilder.show();
            }
        });
    }

    private boolean validate(String cep, String number) {
        if(cep.isEmpty() || number.isEmpty()) {
            Toast.makeText(this, "Os campos de CEP e número devem ser preenchidos.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}