package com.example.jogodaforca;

import static java.lang.System.in;
import static java.lang.System.setErr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.ErrorManager;
import java.util.logging.Handler;

import com.example.jogodaforca.databinding.ActivityMainBinding;
import com.example.jogodaforca.databinding.ActivityTelajogoBinding;
import com.google.android.material.snackbar.Snackbar;

public class telajogo extends AppCompatActivity {

    ActivityTelajogoBinding binding;
    String gamemode;
    String typegame;

    ArrayList<String> frutasLista = new ArrayList<>();
    ArrayList<String> escolasLista = new ArrayList<>();
    ArrayList<String> paisesLista = new ArrayList<>();
    ArrayList<String> coresLista = new ArrayList<>();


    String StringPalavraEscolhida;

    char [] CharArrayPalavraEscolhida;

    char [] CharArrayPalavraDisplayed;

    String StringPalavraDisplayed;

    int vidas = 6;

    String letrasUsadas;

    char [] entradas;

    final String win_text = "Você Ganhou!!";
    final String lose_text = "Você Perdeu!!";

    final String resultadoWin = "Parabéns";
    final String resultadoLose = "Que pena";

    final String textResultadoWin = "Você venceu!!!";
    final String textResultadoLose = "Você perdeu!!!";

    Snackbar snackbar;

    ImageButton cancelButton;
    Button voltarMenu;
    Button jogarNovamente;

    boolean DoublePressToExit = false;

    Toast toast;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelajogoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        View alertCustomDialog = LayoutInflater.from(telajogo.this).inflate(R.layout.custom_dialog, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(telajogo.this);
        alertDialog.setView(alertCustomDialog);

        cancelButton = alertCustomDialog.findViewById(R.id.cancelID);
        voltarMenu = alertCustomDialog.findViewById(R.id.btn_backMenuID);
        jogarNovamente = alertCustomDialog.findViewById(R.id.btn_playAgainID);

        final AlertDialog dialog = alertDialog.create();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        voltarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                startActivity(new Intent(telajogo.this, MainActivity.class));
            }
        });

        jogarNovamente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                startActivity(new Intent(telajogo.this, typegame.class));
            }
        });

        parametrosIniciais();
        iniciarJogo();

        binding.enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String entrada = binding.entradaLetra.getText().toString();

                if(!entrada.isEmpty()){

                    binding.entradaLetra.setText("");

                    entradas = entrada.toCharArray();

                    for(int i = 0; i < entradas.length; i++){

                        verificarLetraNaPalavra(entradas[i]);

                    }

                    if (!StringPalavraDisplayed.contains("_")){

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    }

                }else{
                    snackbar = Snackbar.make(v, "Digite uma letra antes de Enviar", Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });

        binding.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(telajogo.this, MainActivity.class));
            }
        });
    }
    @Override
    public void onBackPressed() {

        if(DoublePressToExit){

            AlertDialog.Builder alertDialogClose = new AlertDialog.Builder(telajogo.this);
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

    private void verificarLetraNaPalavra(char letter){

        if(StringPalavraEscolhida.indexOf(letter) >= 0){

            if (StringPalavraDisplayed.indexOf(letter) < 0){

                revelarLetraNaTela(letter);

                mostrarNaTela();

                toast = Toast.makeText(this, "Essa letra esta na palavra!", Toast.LENGTH_SHORT);
                toast.show();

            }

        }else{
            toast = Toast.makeText(this, "Essa letra NÃO esta na palavra!", Toast.LENGTH_SHORT);
            toast.show();
            decreaseAndDisplayTriesLeft();

            if(vidas == 0){
                binding.pernaE.setVisibility(View.VISIBLE);
                Toast.makeText(this, lose_text, Toast.LENGTH_SHORT).show();
                binding.forcaescolhida.setText(StringPalavraEscolhida);

                AlertDialog.Builder alertDialogLose = new AlertDialog.Builder(telajogo.this);
                alertDialogLose.setTitle("Que pena!!");
                alertDialogLose.setMessage("Você perdeu...");
                alertDialogLose.setPositiveButton("Tente Novamente", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(telajogo.this, typegame.class));
                    }
                });
                alertDialogLose.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogLose.show();
            }

        }if(letrasUsadas.indexOf(letter) < 0){
            letrasUsadas += letter + " ";
            binding.letrasUsadasID.setText(letrasUsadas);
        }

    }
    private void decreaseAndDisplayTriesLeft(){
        if(vidas > 0){
            vidas = vidas -1;
            if(vidas == 5){binding.cabeca.setVisibility(View.VISIBLE);}
            else if(vidas == 4){binding.corpo.setVisibility(View.VISIBLE);}
            else if(vidas == 3){binding.bracoD.setVisibility(View.VISIBLE);}
            else if(vidas == 2){binding.bracoE.setVisibility(View.VISIBLE);}
            else if(vidas == 1){binding.pernaD.setVisibility(View.VISIBLE);}
        }
    }

    private void revelarLetraNaTela(char letter) {

        int indexOfLetter = StringPalavraEscolhida.indexOf(letter);

        while(indexOfLetter >= 0){
            CharArrayPalavraDisplayed[indexOfLetter] = StringPalavraEscolhida.charAt(indexOfLetter);
            indexOfLetter = StringPalavraEscolhida.indexOf(letter, indexOfLetter + 1);
        }
        StringPalavraDisplayed = String.valueOf(CharArrayPalavraDisplayed);
    }

    private void iniciarJogo() {

        CharArrayPalavraEscolhida = StringPalavraEscolhida.toCharArray();

        CharArrayPalavraDisplayed = new char[CharArrayPalavraEscolhida.length ];

        for(int i = 0; i < CharArrayPalavraEscolhida.length  ; i++){

            CharArrayPalavraDisplayed[i] = ('_');

        }

        StringPalavraDisplayed = String.valueOf(CharArrayPalavraDisplayed);

        mostrarNaTela();

        binding.entradaLetra.setText("");

        letrasUsadas = " ";

        binding.letrasUsadasID.setText(letrasUsadas);

    }

    private void mostrarNaTela() {

        String formattedString = "";

        for(char character:CharArrayPalavraDisplayed){
            formattedString += character + " ";
        }
        binding.forcaescolhida.setText(formattedString);

    }
    private void sortearPalavra() {

        if (typegame.equals("frutas")){
            StringPalavraEscolhida = frutasLista.get(new Random().nextInt(10));
        }else if (typegame.equals("cores")){
            StringPalavraEscolhida = coresLista.get(new Random().nextInt(10));
        }else if (typegame.equals("paises")){
            StringPalavraEscolhida = paisesLista.get(new Random().nextInt(10));
        }else if (typegame.equals("escolas")){
            StringPalavraEscolhida = escolasLista.get(new Random().nextInt(10));
        }else{
            Toast.makeText(this, "Erro ao Sortear uma Palavra", Toast.LENGTH_SHORT).show();
        }
    }

    private void criarListas() {

        frutasLista.add("banana"); frutasLista.add("maça");
        frutasLista.add("pera"); frutasLista.add("melancia");
        frutasLista.add("abacate"); frutasLista.add("melao");
        frutasLista.add("uva"); frutasLista.add("laranja");
        frutasLista.add("ameixa"); frutasLista.add("abacaxi");

        escolasLista.add("lapis"); escolasLista.add("borracha");
        escolasLista.add("mesa"); escolasLista.add("cadeira");
        escolasLista.add("estojo"); escolasLista.add("quadro");
        escolasLista.add("giz"); escolasLista.add("apagador");
        escolasLista.add("canetas"); escolasLista.add("mochilas");

        paisesLista.add("frança"); paisesLista.add("espanha");
        paisesLista.add("iraque"); paisesLista.add("china");
        paisesLista.add("italia"); paisesLista.add("turquia");
        paisesLista.add("mexico"); paisesLista.add("alemanha");
        paisesLista.add("tailandia"); paisesLista.add("inglaterra");

        coresLista.add("verde"); coresLista.add("vermelho");
        coresLista.add("preto"); coresLista.add("amarelo");
        coresLista.add("violeta"); coresLista.add("laranja");
        coresLista.add("branco"); coresLista.add("rosa");
        coresLista.add("marrom"); coresLista.add("ouro");

    }

    private void parametrosIniciais() {

        Intent intent = getIntent();
        gamemode = intent.getStringExtra("gamemode");
        typegame = intent.getStringExtra("a");

        binding.gamemode.setText(gamemode);

        criarListas();
        sortearPalavra();
    }
}