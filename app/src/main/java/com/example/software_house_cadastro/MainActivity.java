package com.example.software_house_cadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import database.SQLHelper;
import helpers.Login;

public class MainActivity extends AppCompatActivity {

    private EditText txtLogin;
    private EditText txtSenha;
    private Button buttonLogar;
    private Button buttonCadastar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtLogin = findViewById(R.id.txtlogin);
        txtSenha = findViewById(R.id.txtSenha);
        buttonLogar = findViewById(R.id.buttonEntrar);
        buttonCadastar = findViewById(R.id.buttonCadastrar);

        buttonCadastar.setOnClickListener(view -> {
            Intent telaCadastro = new Intent(
                    MainActivity.this, CadastroUsuario.class
            );
            startActivity(telaCadastro);
        });

        buttonLogar.setOnClickListener(view -> {
            String login = txtLogin.getText().toString();
            String senha = txtSenha.getText().toString();

            int cod_usuario = SQLHelper
                    .getInstance(this)
                    .login(login, senha);
            if (cod_usuario > 0){

                Login.setCod_usuario(cod_usuario);

                startActivity(new Intent(MainActivity.this, Feed.class));

            }else{
                Toast.makeText(this, "Dados de login  incorretos", Toast.LENGTH_SHORT).show();
            }
        });

    }
}