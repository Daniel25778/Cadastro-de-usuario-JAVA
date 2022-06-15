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

public class CadastroEndereco extends AppCompatActivity {

    //REPRESENTAÇÃO DOS CAMPOS DA ACTIVITY

    private EditText txtCep;
    private EditText txtNumero;
    private EditText txtComplemento;
    private Button buttonTextCadastroEndereco;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco);


        //CAPTURA DOS COMPONENTES GRAFICOS DA ACTIVITY

        txtCep = findViewById(R.id.txtCep);
        txtNumero = findViewById(R.id.txtNumero);
        txtComplemento = findViewById(R.id.txtComplemento);
        buttonTextCadastroEndereco = findViewById(R.id.buttonTextCadastroEndereco);

        // TRATAMENTO DO EVENTO DE CLIQUE NO BOTÃO

        buttonTextCadastroEndereco.setOnClickListener( view-> {
            if (!validate()) {
                Toast.makeText(this, "PREENCHA TODOS OS CAMPOS", Toast.LENGTH_SHORT).show();
                return;
            }

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.titulo_cadastro_endereco))
                    .setMessage(getString(R.string.mensagem_cadastro_endereco))
                    .setPositiveButton(R.string.salvar, (dialog1, which)->{
                        //AÇÃO DO POSITIVE BUTTON
                        String cep =  txtCep.getText().toString();
                        String numero =  txtNumero.getText().toString();
                        String complemento =  txtComplemento.getText().toString();


                        getIntent().hasExtra("COD_USUARIO");
                        Bundle extras = getIntent().getExtras();
                        int cod_usuario = extras.getInt("COD_USUARIO");


                        boolean cadastroEndereco = SQLHelper.getInstance(this)
                                .addEndereco(cod_usuario,cep, complemento,numero);

                        if (cadastroEndereco){
                            Toast.makeText(this, "ENDEREÇO CADASTRADO COM SUCESSO", Toast.LENGTH_LONG).show();


                            Intent telaFeed = new Intent(
                                    CadastroEndereco.this,
                                    Feed.class
                            );

                            startActivity(telaFeed);


                        }else{
                            Toast.makeText(this, "HOUVE UM ERRO AO REALIZAR O CADASTRO DE ENDERECO", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton(R.string.cancelar, (dialog1, which)->{}).create();

            dialog.show();

        });
    }





    // Método de validação

    private boolean validate(){


        return(
                !txtCep.getText().toString().isEmpty() &&
                        !txtComplemento.getText().toString().isEmpty() &&
                        !txtNumero.getText().toString().isEmpty()
        );

    }

}
