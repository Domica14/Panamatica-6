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
import java.util.Random;

//Se implementa la interfaz View.onClickListener para poder manejar varios botones
public class JuegoX0 extends AppCompatActivity implements View.OnClickListener {

    //Declaracion de variables para manejar los elementos de la vista
    //Los ImageView de cuadros manejan cada uno de los espacios donde se colocaran las X y los 0
    private ImageView cuadro1, cuadro2, cuadro3, cuadro4, cuadro5, cuadro6, cuadro7, cuadro8, cuadro9;
    private Button btnSalir;
    //
    private static int puntaje;

    //Al iniciar el juego se crea una lista que manejara las opciones seleccionadas por el usuario
    ArrayList<Integer> usuarioOpciones = new ArrayList<Integer>();
    //Tambien se tiene uno para el contrincante
    ArrayList<Integer> contrincanteOpciones = new ArrayList<Integer>();

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

        //Listener para salir del juego
        btnSalir.setOnClickListener(this);      //Se hace una llamada al metodo onClick

        //Listener de botones para cuadros, primera fila
        cuadro1.setOnClickListener(this);
        cuadro2.setOnClickListener(this);
        cuadro3.setOnClickListener(this);

        //Listener de botones para cuadros, segunda fila
        cuadro4.setOnClickListener(this);
        cuadro5.setOnClickListener(this);
        cuadro6.setOnClickListener(this);

        //Listener de botones para cuadros, tercera fila
        cuadro7.setOnClickListener(this);
        cuadro8.setOnClickListener(this);
        cuadro9.setOnClickListener(this);
    }

    /*
    Se sobreescribe el metodo onClick para poder manejar las distintas imagenes como botones de una manera
    mas simple
    */
    @Override
    public void onClick(View v){
        //If para manejar cada accion a realizar cuando se presiona una imagen, switch causaba errores
        int id = v.getId();
        //Primera fila
        if (id == R.id.cuadro1) {
            //Se reemplaza la imagen por la que contiene el O
            cuadro1.setImageResource(R.drawable.tresenrayao);
            cuadro1.setEnabled(false);          //Se desactiva el boton
            usuarioOpciones.add(1);
        } else if (id == R.id.cuadro2) {
            cuadro2.setImageResource(R.drawable.tresenrayao);
            usuarioOpciones.add(2);
        } else if (id == R.id.cuadro3) {
            cuadro3.setImageResource(R.drawable.tresenrayao);
            usuarioOpciones.add(3);
        //Segunda fila
        } else if (id == R.id.cuadro4) {
            cuadro4.setImageResource(R.drawable.tresenrayao);
            usuarioOpciones.add(4);
        } else if (id == R.id.cuadro5) {
            cuadro5.setImageResource(R.drawable.tresenrayao);
            usuarioOpciones.add(5);
        } else if (id == R.id.cuadro6) {
            cuadro6.setImageResource(R.drawable.tresenrayao);
            usuarioOpciones.add(6);
        //Tercera fila
        } else if (id == R.id.cuadro7) {
            cuadro7.setImageResource(R.drawable.tresenrayao);
            usuarioOpciones.add(7);
        } else if (id == R.id.cuadro8) {
            cuadro8.setImageResource(R.drawable.tresenrayao);
            usuarioOpciones.add(8);
        } else if (id == R.id.cuadro9) {
            cuadro9.setImageResource(R.drawable.tresenrayao);
            usuarioOpciones.add(9);
        //Boton de salir
        } else if (id == R.id.btnSalir) {
            startActivity(new Intent(JuegoX0.this, SeleccionUnidad.class));
            fade();
            finish();
        }
    }

    public void juego(){
        Random random = new Random();
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