package com.example.jeferson.appdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btpf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btpf = (Button) findViewById(R.id.btCadastro);
        btpf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCadastro();
            }
        });
    }

    void loadCadastro(){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, Cadastro.class);
        startActivity(intent);
        finish();
    }
}
