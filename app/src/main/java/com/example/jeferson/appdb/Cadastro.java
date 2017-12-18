//******************************************************

//Instituto Federal de São Paulo - Campus Sertãozinho

//Disciplina......: M4DADM

//Programação de Computadores e Dispositivos Móveis

//Aluno...........: Jeferson Rodrigues

//******************************************************
package com.example.jeferson.appdb;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class Cadastro extends AppCompatActivity {
    // OBJETIVO.......: Declarar as variaveis
    private DBHelper dh;
    EditText tfNome, tfCPF, tfIdade, tfTelefone, tfEmail;
    Button btInserir, btListar, btVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        // OBJETIVO.......:  Obter dados dos campos
        this.dh = new DBHelper(this);
        tfNome = (EditText) findViewById(R.id.tfNome);
        tfCPF = (EditText) findViewById(R.id.tfCPF);
        tfIdade = (EditText) findViewById(R.id.tfIdade);
        tfTelefone = (EditText) findViewById(R.id.tfTelefone);
        tfEmail = (EditText) findViewById(R.id.tfEmail);

        btInserir = (Button) findViewById(R.id.btInserir);
        btListar = (Button) findViewById(R.id.btListar);
        btVoltar = (Button) findViewById(R.id.btVoltar);

        // OBJETIVO.......: Botão Inserir chamando metodo
        btInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserirRegistro();
            }
        });

        // OBJETIVO.......: Botao Listar chamando metodo
        btListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarRegistros();

            }
        });

        // OBJETIVO.......: Botão Voltar chamando metodo
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltarInicio();
            }
        });
    }
    // OBJETIVO.......: Metodo para listar registros
    void listarRegistros(){
        List<Contato> contatos = dh.recuperaDados();
        if (contatos == null){
            AlertDialog.Builder adb = new AlertDialog.Builder(Cadastro.this);
            adb.setTitle("Mensagem ");
            adb.setMessage("Não ha registro cadastrado");
            adb.show();

            return;
        }

            for (int i = 0; i < contatos.size(); i++) {
                Contato contato = (Contato) contatos.get(i);
                AlertDialog.Builder adb = new AlertDialog.Builder(Cadastro.this);
                adb.setTitle("Registro " + i);
                adb.setMessage("Nome: " + contato.getNome() + "\nCPF: " + contato.getCpf() + "\nIdade: " + contato.getIdade() + "\nTelefone: " + contato.getTelefone() + "\nE-mail: " + contato.getEmail());
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                adb.show();
            }

    }
    // OBJETIVO.......: Metodo para inserir registro
    void inserirRegistro(){
        if (tfNome.getText().length() > 0 && tfCPF.getText().length() > 0 && tfIdade.getText().length() > 0 && tfTelefone.getText().length() > 0 && tfEmail.getText().length() > 0){
            dh.insert(tfNome.getText().toString(),tfCPF.getText().toString(),tfIdade.getText().toString(),tfTelefone.getText().toString(),tfEmail.getText().toString());
            AlertDialog.Builder adb = new AlertDialog.Builder(Cadastro.this);
            adb.setTitle("Sucesso ");
            adb.setMessage("Cadastro efetuado com sucesso");
            adb.show();

            tfNome.setText("");
            tfCPF.setText("");
            tfIdade.setText("");
            tfTelefone.setText("");
            tfEmail.setText("");

        }
        else{
            AlertDialog.Builder adb = new AlertDialog.Builder(Cadastro.this);
            adb.setTitle("Erro ");
            adb.setMessage("Cadastro NÃO efetuado, preencha todos os campos");
            adb.show();

            tfNome.setText("");
            tfCPF.setText("");
            tfIdade.setText("");
            tfTelefone.setText("");
            tfEmail.setText("");
        }
    }
    // OBJETIVO.......: Metodo para voltar a MainActivity
    void voltarInicio(){
        Intent intent = new Intent();
        intent.setClass(Cadastro.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
