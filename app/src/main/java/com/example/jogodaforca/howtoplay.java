package com.example.jogodaforca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jogodaforca.databinding.ActivityHowtoplayBinding;
import com.example.jogodaforca.databinding.ActivityMainBinding;

public class howtoplay extends AppCompatActivity {

    ActivityHowtoplayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHowtoplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(howtoplay.this, MainActivity.class));
            }
        });

        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(howtoplay.this, typegame.class));
            }
        });
    }
}