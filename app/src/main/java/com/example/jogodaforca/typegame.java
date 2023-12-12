package com.example.jogodaforca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.jogodaforca.databinding.ActivityMainBinding;
import com.example.jogodaforca.databinding.ActivityTypegameBinding;
import com.google.android.material.snackbar.Snackbar;

public class typegame extends AppCompatActivity {

    ActivityTypegameBinding binding;

    String gamemode;
    String a;
    View v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTypegameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.frutasMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               gamemode = "Modo Frutas";
               a = "frutas";
               startTela();
            }
        });

        binding.escolaMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gamemode = "Modo Escolas";
                a = "escolas";
                startTela();
            }
        });

        binding.paisMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gamemode = "Modo País";
                a = "paises";
                startTela();
            }
        });

        binding.coresMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gamemode = "Modo Cores";
                a = "cores";
                startTela();
            }
        });
    }

    private void startTela() {

        Intent intent = new Intent(typegame.this, telajogo.class);
        intent.putExtra("gamemode", gamemode);
        intent.putExtra("a", a);
        startActivity(intent);

        Toast.makeText(this, "Jogo Iniciado", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(typegame.this, MainActivity.class));

    }
}