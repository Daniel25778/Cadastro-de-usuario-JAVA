package com.example.software_house_cadastro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import database.SQLHelper;
import helpers.DateFormat;

public class CadastroUsuario extends AppCompatActivity {

    //REPRESENTAÇÃO DOS CAMPOS DA ACTIVITY

    private EditText txtNome;
    private EditText txtSobrenome;
    private EditText txtLogin;
    private EditText txtSenha;
    private Button buttonTextCadastroUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);


        //CAPTURA DOS COMPONENTES GRAFICOS DA ACTIVITY


        txtNome = findViewById(R.id.txtNome);
        txtLogin = findViewById(R.id.txtlogin);
        txtSenha = findViewById(R.id.txtSenha);
        txtSobrenome = findViewById(R.id.txtSobrenome);
        buttonTextCadastroUsuario = findViewById(R.id.buttonTextCadastroUsuario);

        // TRATAMENTO DO EVENTO DE CLIQUE NO BOTÃO

        buttonTextCadastroUsuario.setOnClickListener( view-> {
            if (!validate()) {
                Toast.makeText(this, "PREENCHA TODOS OS CAMPOS", Toast.LENGTH_SHORT).show();
                return;
            }

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.titulo_cadastro_usuario))
                    .setMessage(getString(R.string.mensagem_cadastro_usuario))
                    .setPositiveButton(R.string.salvar, (dialog1, which)->{
                        //AÇÃO DO POSITIVE BUTTON
                        String nome =  txtNome.getText().toString();
                        String sobrenome =  txtSobrenome.getText().toString();
                        String login =  txtLogin.getText().toString();
                        String senha =  txtSenha.getText().toString();
                        DateFormat dataFormatada = new DateFormat();
                        String created_date = dataFormatada.getDateFormat();

                        long cod_usuario = SQLHelper.getInstance(this)
                                .addUser(nome, sobrenome,senha,login, created_date);

                        if (cod_usuario > 0){
                            Toast.makeText(this, "CADASTRO REALIZADO COM SUCESSO", Toast.LENGTH_LONG).show();


                            Intent telaCadastroEndereco = new Intent(
                                    CadastroUsuario.this,
                                    CadastroEndereco.class
                            );

                            telaCadastroEndereco.putExtra("COD_USUARIO", cod_usuario);

                            startActivity(telaCadastroEndereco);


                        }else{
                            Toast.makeText(this, "HOUVE UM ERRO AO REALIZAR O CADASTRO DE USUÁRIO", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton(R.string.cancelar, (dialog1, which)->{}).create();

            dialog.show();

        });
    }




    // Método de validação

    private boolean validate(){


        return(
                !txtNome.getText().toString().isEmpty() &&
                        !txtSobrenome.getText().toString().isEmpty() &&
                        !txtLogin.getText().toString().isEmpty() &&
                        !txtSenha.getText().toString().isEmpty()


        );


    }


}
