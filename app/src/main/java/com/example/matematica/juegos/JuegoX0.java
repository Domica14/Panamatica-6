package com.example.matematica.juegos;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.matematica.R;
import com.example.matematica.menu.MainActivity;
import com.example.matematica.menu.SeleccionUnidad;

import java.util.ArrayList;

public class JuegoX0 extends AppCompatActivity {

    //Declaracion de variables para manejar los elementos de la vista
    //Los ImageView de cuadros manejan cada uno de los espacios donde se colocaran las X y los 0
    private ImageView cuadro1, cuadro2, cuadro3, cuadro4, cuadro5, cuadro6, cuadro7, cuadro8, cuadro9;
    private Button btnSalir;
    //
    private static int puntaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_x0);

        //Asociacion de variables con los elementos de la vista

        //Boton
        btnSalir = findViewById(R.id.btnSalir);

        //ImageView (Cuadros)
        //Fila 1
        cuadro1 = findViewById(R.id.cuadro1);
        cuadro2 = findViewById(R.id.cuadro2);
        cuadro3 = findViewById(R.id.cuadro3);
        //Fila 2
        cuadro4 = findViewById(R.id.cuadro4);
        cuadro5 = findViewById(R.id.cuadro5);
        cuadro6 = findViewById(R.id.cuadro6);
        //Fila 3
        cuadro7 = findViewById(R.id.cuadro7);
        cuadro8 = findViewById(R.id.cuadro8);
        cuadro9 = findViewById(R.id.cuadro9);

        juego();

        //Listener para salir del juego
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JuegoX0.this, SeleccionUnidad.class));
                fade();
            }
        });

    }

    private void juego(){
        //Al iniciar el juego se crea una lista que manejara las opciones seleccionadas por el usuario
        ArrayList<Integer> usuarioOpciones = new ArrayList<Integer>();
        //Tambien se tiene uno para el contrincante
        ArrayList<Integer> contrincanteOpciones = new ArrayList<Integer>();

        //Se colocan los listeners a cada cuadro
        cuadro1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se reemplaza la imagen por la qe contiene el O
                cuadro1.setImageResource(R.drawable.tresenrayao);
                usuarioOpciones.add(1);
            }
        });
        cuadro2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuadro2.setImageResource(R.drawable.tresenrayao);
                usuarioOpciones.add(2);
            }
        });
        cuadro3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuadro3.setImageResource(R.drawable.tresenrayao);
                usuarioOpciones.add(3);
            }
        });
        cuadro4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuadro4.setImageResource(R.drawable.tresenrayao);
                usuarioOpciones.add(4);
            }
        });
        cuadro5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuadro5.setImageResource(R.drawable.tresenrayao);
                usuarioOpciones.add(5);
            }
        });
        cuadro6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuadro6.setImageResource(R.drawable.tresenrayao);
                usuarioOpciones.add(6);
            }
        });
        cuadro7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuadro7.setImageResource(R.drawable.tresenrayao);
                usuarioOpciones.add(7);
            }
        });
        cuadro8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuadro8.setImageResource(R.drawable.tresenrayao);
                usuarioOpciones.add(8);
            }
        });
        cuadro9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuadro9.setImageResource(R.drawable.tresenrayao);
                usuarioOpciones.add(9);
            }
        });
    }

    /*Se sobrescribe el metodo de onBackPressed para que realize el retroceso a la activity anterior al presionar
      el boton de atras del celular
     */
    @Override
    public void onBackPressed(){
        startActivity(new Intent(JuegoX0.this, SeleccionUnidad.class));
        fade();
        finish();
    }

    //Metodo que se encarga de la transicion entre activities
    public void fade(){
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);         //Metodo para cambiar la transicion
        finish();       //Se finaliza la activity actual
    }
}