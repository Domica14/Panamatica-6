package com.example.matematica.juegos;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.matematica.R;
import com.example.matematica.menu.MainActivity;
import com.example.matematica.menu.SeleccionUnidad;
import com.example.matematica.unidad1.*;

import java.util.*;

//Se implementa la interfaz View.onClickListener para poder manejar varios botones
public class JuegoX0 extends AppCompatActivity implements View.OnClickListener {

    //Declaracion de variables para manejar los elementos de la vista
    //Los ImageView de cuadros manejan cada uno de los espacios donde se colocaran las X y los 0
    private ImageView cuadro1, cuadro2, cuadro3, cuadro4, cuadro5, cuadro6, cuadro7, cuadro8, cuadro9;
    private Button btnContinuar;
    private TextView txtResultado;

    //Al iniciar el juego se crea un set que manejara las opciones seleccionadas por el usuario y otro del contrincante
    private Set<Integer> usuarioOpciones = new HashSet<>();
    private Set<Integer> contrincanteOpciones = new HashSet<>();

    //Lista que contiene las combinanciones de respuestas
    private int[][] combinaciones = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {1, 5, 9}, {3, 5, 7}, {1, 4, 7},
                                     {2, 5, 8}, {3, 6, 9}};

    //Guarda el valor de la proxima activity
    private int activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_x0);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);      //Bloquea la orientacion de pantalla

        //Se obtiene el valor de la proxima activity
        activity = getIntent().getExtras().getInt("proximaActivity");

        //Asociacion de variables con los elementos de la vista

        //TextView
        txtResultado= findViewById(R.id.txtGanador);

        //Boton
        btnContinuar = findViewById(R.id.btnContinuar);

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
        btnContinuar.setOnClickListener(this);      //Se hace una llamada al metodo onClick

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
    public void onClick(View v) {
        //Boton de salir
        int id = v.getId();
        juego(id);
        if (id == R.id.btnContinuar) {
            proximaActivity();
        }
    }

    //Metodo que maneja el funcionamiento del juego por la parte del contrincante
    public void juego(int id){

        //Turno usuario
        if (usuarioOpciones.size() == contrincanteOpciones.size()){
            turnoUsuario(id);
        }
        //Turno contrincante
        if (contrincanteOpciones.size() < usuarioOpciones.size() && usuarioOpciones.size() <= 5){
            //Para evitar mas turnos de los posibles se establece el limite
            if (usuarioOpciones.size() == 5){
                txtResultado.setText("Empate :/");
            } else {            //En caso de no haber llegado al maximo se ejecuta normal
                turnoContrincante();
            }
        }
        //Confirma que se hayan seleccionado un minimo de tres opciones para verificar el ganador
            if (buscarGanador(usuarioOpciones)){
                txtResultado.setText("Ganaste!");
            } else if (buscarGanador(contrincanteOpciones)) {
                txtResultado.setText("Perdiste :(");
            }
    }

    //Metodo que maneja el funcionamiento del turno del usuario
    public void turnoUsuario(int id){
        //Primera fila
        if (id == R.id.cuadro1) {
            //Se reemplaza la imagen por la que contiene el O
            cuadro1.setImageResource(R.drawable.tresenrayao);
            cuadro1.setEnabled(false);          //Se desactiva el boton
            usuarioOpciones.add(1);
        } else if (id == R.id.cuadro2) {
            cuadro2.setImageResource(R.drawable.tresenrayao);
            cuadro2.setEnabled(false);          //Se desactiva el boton
            usuarioOpciones.add(2);
        } else if (id == R.id.cuadro3) {
            cuadro3.setImageResource(R.drawable.tresenrayao);
            cuadro3.setEnabled(false);          //Se desactiva el boton
            usuarioOpciones.add(3);
            //Segunda fila
        } else if (id == R.id.cuadro4) {
            cuadro4.setImageResource(R.drawable.tresenrayao);
            cuadro4.setEnabled(false);          //Se desactiva el boton
            usuarioOpciones.add(4);
        } else if (id == R.id.cuadro5) {
            cuadro5.setImageResource(R.drawable.tresenrayao);
            cuadro5.setEnabled(false);          //Se desactiva el boton
            usuarioOpciones.add(5);
        } else if (id == R.id.cuadro6) {
            cuadro6.setImageResource(R.drawable.tresenrayao);
            cuadro6.setEnabled(false);          //Se desactiva el boton
            usuarioOpciones.add(6);
            //Tercera fila
        } else if (id == R.id.cuadro7) {
            cuadro7.setImageResource(R.drawable.tresenrayao);
            cuadro7.setEnabled(false);          //Se desactiva el boton
            usuarioOpciones.add(7);
        } else if (id == R.id.cuadro8) {
            cuadro8.setImageResource(R.drawable.tresenrayao);
            cuadro8.setEnabled(false);          //Se desactiva el boton
            usuarioOpciones.add(8);
        } else if (id == R.id.cuadro9) {
            cuadro9.setImageResource(R.drawable.tresenrayao);
            cuadro9.setEnabled(false);          //Se desactiva el boton
            usuarioOpciones.add(9);
        }
    }

    //Metodo que maneja el funcionamiento del turno del contrincante
    public void turnoContrincante(){
        //Se crea una instancia de la clase Random
        Random random = new Random();

        //Definicion de limites de los numeros aleatorios
        int min = 1;
        int max = 9;
        int finCiclo = 0;   //Variable centinela para terminar el ciclo de generacion de opciones

        //Maneja las opciones que selecciona el contrincante (se generan automaticamente)
        while (finCiclo == 0){
            int opcionContrincante = random.nextInt(max - min + 1) + min;
            //Se verifica que la opcion generada no haya sido seleccionada anteriormente
            if (!usuarioOpciones.contains(opcionContrincante) && !contrincanteOpciones.contains(opcionContrincante)){
                switch (opcionContrincante){
                    case 1:
                        cuadro1.setImageResource(R.drawable.tresenrayax);
                        cuadro1.setEnabled(false);
                        contrincanteOpciones.add(1);
                        break;
                    case 2:
                        cuadro2.setImageResource(R.drawable.tresenrayax);
                        cuadro2.setEnabled(false);
                        contrincanteOpciones.add(2);
                        break;
                    case 3:
                        cuadro3.setImageResource(R.drawable.tresenrayax);
                        cuadro3.setEnabled(false);
                        contrincanteOpciones.add(3);
                        break;
                    case 4:
                        cuadro4.setImageResource(R.drawable.tresenrayax);
                        cuadro4.setEnabled(false);
                        contrincanteOpciones.add(4);
                        break;
                    case 5:
                        cuadro5.setImageResource(R.drawable.tresenrayax);
                        cuadro5.setEnabled(false);
                        contrincanteOpciones.add(5);
                        break;
                    case 6:
                        cuadro6.setImageResource(R.drawable.tresenrayax);
                        cuadro6.setEnabled(false);
                        contrincanteOpciones.add(6);
                        break;
                    case 7:
                        cuadro7.setImageResource(R.drawable.tresenrayax);
                        cuadro7.setEnabled(false);
                        contrincanteOpciones.add(7);
                        break;
                    case 8:
                        cuadro8.setImageResource(R.drawable.tresenrayax);
                        cuadro8.setEnabled(false);
                        contrincanteOpciones.add(8);
                        break;
                    case 9:
                        cuadro9.setImageResource(R.drawable.tresenrayax);
                        cuadro9.setEnabled(false);
                        contrincanteOpciones.add(9);
                        break;
                }
                finCiclo = 1;
            }
        }
    }

    //Metodo encargado de buscar al ganador, se pasa como parametro el set de opciones
    public boolean buscarGanador(Set opciones){
        for (int i=0; i<combinaciones.length; i++){     //Se recorren los sub-arrays
            int coincidencias = 0;              //Contiene la cantidad de coincidencias
            for (int j=0; j<combinaciones[i].length; j++){      //Se recorren los elementos de los sub-arrays
                if (opciones.contains(combinaciones[i][j])){     //Se verifica si se contiene el elemento
                    coincidencias+=1;
                    if (coincidencias==3){              //Si coinciden tres elementos retorna true
                        return true;
                    }
                }
            }
        }
        return false;    //Retorna false de no alcanzar las 3 coincidencias
    }

    //Maneja el cambio de activities desde los juegos
    public void proximaActivity(){
        switch (activity){
            case 2:
                startActivity(new Intent(JuegoX0.this, RestaEZ.class));
                fade();
                finish();
                break;
            case 3:
                startActivity(new Intent(JuegoX0.this, DivEZ.class));
                fade();
                finish();
                break;
            case 4:
                startActivity(new Intent(JuegoX0.this, Resta.class));
                fade();
                finish();
                break;
            case 5:
                startActivity(new Intent(JuegoX0.this, Div.class));
                fade();
                finish();
                break;
            case 6:
                startActivity(new Intent(JuegoX0.this, Lecciones.class));
                fade();
                finish();
                break;
            case 7:
                startActivity(new Intent(JuegoX0.this, LeccionesAdv.class));
                fade();
                finish();
                break;
        }
    }

    /*Se sobrescribe el metodo de onBackPressed para que realize el retroceso a la activity anterior al presionar
      el boton de atras del celular
     */
    @Override
    public void onBackPressed () {
        startActivity(new Intent(JuegoX0.this, SeleccionUnidad.class));
        fade();
        finish();
    }

    //Metodo que se encarga de la transicion entre activities
    public void fade () {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);         //Metodo para cambiar la transicion
        finish();       //Se finaliza la activity actual
    }
}