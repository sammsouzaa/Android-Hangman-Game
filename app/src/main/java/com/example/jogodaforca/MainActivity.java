package com.example.jogodaforca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.example.jogodaforca.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    boolean DoublePressToExit = false;

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.howtoplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, howtoplay.class));
            }
        });

        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, typegame.class));
            }
        });

    }

    @Override
    public void onBackPressed() {

        if(DoublePressToExit){

            AlertDialog.Builder alertDialogClose = new AlertDialog.Builder(MainActivity.this);
            alertDialogClose.setTitle("Exit App");
            alertDialogClose.setMessage("Deseja fechar o aplicativo?");
            alertDialogClose.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                }
            });
            alertDialogClose.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialogClose.show();
            toast.cancel();

        }
        else {
            DoublePressToExit = true;

            toast = Toast.makeText(this, "Clique novamente", Toast.LENGTH_SHORT);
            toast.show();

            android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DoublePressToExit = false;
                }
            }, 1500);

        }
    }
}