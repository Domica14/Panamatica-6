package com.example.matematica.unidad1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.matematica.R;
import com.example.matematica.juegos.JuegoAdivinanzas;
import com.example.matematica.juegos.JuegoX0;
import com.example.matematica.menu.SeleccionUnidad;

import java.util.Random;
import java.util.HashSet;
import java.util.Set;

public class SumaEZ extends AppCompatActivity {

    TextView txtPregunta, txtResultado;

    MediaPlayer mp, mp2;

    Button[] btnOpciones;

    Button btnFinal, btnVolver, btnContinuar;

    int respuestaCorrecta, Count = 1;
    Set<Integer> respuestasAsignadas = new HashSet<>();
    int respuestasCorrectas = 0;

    private SharedPreferences sharedPreferences; // SharedPreferences para almacenar los puntos
    private SharedPreferences.Editor editor; // Editor de SharedPreferences

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suma_ez);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //Bloquea la orientacion de pantalla

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        txtPregunta = findViewById(R.id.txtPregunta);
        txtResultado = findViewById(R.id.txtResultado);
        btnOpciones = new Button[]{
                findViewById(R.id.btnOpcion1),
                findViewById(R.id.btnOpcion2),
                findViewById(R.id.btnOpcion3),
                findViewById(R.id.btnOpcion4)
        };

        btnFinal = findViewById(R.id.btnFinal);
        btnVolver = findViewById(R.id.btnVolver);
        btnContinuar = findViewById(R.id.btnNext);
        btnFinal.setEnabled(false);
        btnVolver.setEnabled(false);
        btnContinuar.setEnabled(false);

        mp = MediaPlayer.create(this, R.raw.button);
        mp2 = MediaPlayer.create(this, R.raw.soundb);

        generarOperacion();

        //Contador de intentos
        txtResultado.setText("Problema: " + Count);

        //Funcionamiento del boton Reiniciar
        btnFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarActivity();
                fade();
            }
        });

        //Funcionamiento del boton Volver
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoConfirmacion();
            }
        });

        //Funcionamiento del boton Continuar
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambioJuego();
            }
        });

        //Funcionamiento de los botones de posibles respuestas
        for (Button btn : btnOpciones) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verificarRespuesta((Button) v);
                }
            });
        }
    }

    //Método que genera la operacion a resolver
    private void generarOperacion() {
        Random random = new Random();
        int numero1 = random.nextInt(100) + 1; //Generación Aleatoria del 1 al 100
        int numero2 = random.nextInt(100) + 1; //Generación Aleatoria del 1 al 100
        respuestaCorrecta = numero1 + numero2;

        txtPregunta.setText(numero1 + " + " + numero2 + " = ?");

        respuestasAsignadas.clear(); // Limpiar el conjunto antes de generar nuevas respuestas

        // Colocar la respuesta correcta en uno de los botones
        int botonRespuestaCorrecta = random.nextInt(4); // Número aleatorio entre 0 y 3
        btnOpciones[botonRespuestaCorrecta].setText(String.valueOf(respuestaCorrecta));
        respuestasAsignadas.add(respuestaCorrecta); // Agregar la respuesta correcta al conjunto

        //Ciclo que se repetirá hasta que todas las opciones incorrectas esten ubicadas.
        for (int i = 0; i < 4; i++) {
            if (i != botonRespuestaCorrecta) {
                int respuestaIncorrecta;
                do {
                    respuestaIncorrecta = generarRespuestaIncorrecta();
                } while (respuestasAsignadas.contains(respuestaIncorrecta)); // Verificar si la respuesta ya está en el conjunto
                btnOpciones[i].setText(String.valueOf(respuestaIncorrecta));
                respuestasAsignadas.add(respuestaIncorrecta); // Agregar la respuesta incorrecta al conjunto
            }
        }
    }

    //Método que se encarga de generar las respuestas incorrectas.
    private int generarRespuestaIncorrecta() {
        Random random = new Random();
        int rango = 20;
        return respuestaCorrecta + random.nextInt(rango) - rango / 2; //Creamos respuestas incorrectas que esten algo cerca de la verdadera para que haya mas cercania
    }

    //Método que se encarga de verificar si el botón seleccionado es el correcto o no.
    private void verificarRespuesta(Button opcionSeleccionada) {
        int respuestaUsuario = Integer.parseInt(opcionSeleccionada.getText().toString());
        Count++;

        for (Button btn : btnOpciones) {
            btn.setEnabled(false);
        }

        //Se compara la seleccion del usuario con la respuesta verdadera para dar un veredicto.
        if (respuestaUsuario == respuestaCorrecta) {
            mostrarToast("¡Correcto!");
            respuestasCorrectas++;
            mp.start();
            opcionSeleccionada.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light)); // Cambia el color de fondo del botón a verde
        } else {
            mostrarToast("Incorrecto. La respuesta correcta es " + respuestaCorrecta);
            mp2.start();
            opcionSeleccionada.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light)); // Cambia el color de fondo del botón a rojo
        }

        //Mientras el contador se mantenga debajo o igual a 5, se seguiran efectuando las operaciones.
        if (Count <= 5) {

            // Agregar un retraso antes de generar una nueva operación
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    for (Button btn : btnOpciones) {
                        btn.setEnabled(true);
                    }

                    txtResultado.setText("Problema: " + Count);

                    generarOperacion();

                    int colorFondo = Color.parseColor("#F68D2E");
                    for (Button btn : btnOpciones) {
                        btn.setBackgroundColor(colorFondo);
                    }

                }
            }, 1000); // 1000 milisegundos (1 segundo) de retraso antes de generar la próxima operación


            //Una ves pase de 5, apareceran los botones que tendrán cada uno una función.
            //Aquí podremos ver nuestro resultado de igual manera.
        }

        else {

            if (respuestasCorrectas == 5) {
                // Sumar puntos
                int puntosActuales = sharedPreferences.getInt("puntos", 0);
                puntosActuales += 25;
                editor.putInt("puntos", puntosActuales);
                editor.apply();
            } else if (respuestasCorrectas == 4) {
                // Sumar puntos
                int puntosActuales = sharedPreferences.getInt("puntos", 0);
                puntosActuales += 15;
                editor.putInt("puntos", puntosActuales);
                editor.apply();
            } else if (respuestasCorrectas == 3) {
                // Sumar puntos
                int puntosActuales = sharedPreferences.getInt("puntos", 0);
                puntosActuales += 10;
                editor.putInt("puntos", puntosActuales);
                editor.apply();
            } else if (respuestasCorrectas == 2) {
                // Sumar puntos
                int puntosActuales = sharedPreferences.getInt("puntos", 0);
                puntosActuales += 5;
                editor.putInt("puntos", puntosActuales);
                editor.apply();
            } else if (respuestasCorrectas == 1) {
                // Sumar puntos
                int puntosActuales = sharedPreferences.getInt("puntos", 0);
                puntosActuales += 1;
                editor.putInt("puntos", puntosActuales);
                editor.apply();
            } else {
                // Sumar puntos
                int puntosActuales = sharedPreferences.getInt("puntos", 0);
                puntosActuales += 0;
                editor.putInt("puntos", puntosActuales);
                editor.apply();
            }

            btnFinal.setEnabled(true);
            btnFinal.setVisibility(View.VISIBLE);
            btnVolver.setEnabled(true);
            btnVolver.setVisibility(View.VISIBLE);
            btnContinuar.setEnabled(true);
            btnContinuar.setVisibility(View.VISIBLE);
            txtPregunta.setVisibility(View.GONE);
            txtResultado.setVisibility(View.GONE);
            findViewById(R.id.llBotonesContainer).setVisibility(View.GONE);

            TextView txtResultadoFinal = findViewById(R.id.txtResultadoFinal);
            txtResultadoFinal.setVisibility(View.VISIBLE);
            txtResultadoFinal.setText("Resultado: " + respuestasCorrectas + "/5");


        }
    }

    //Método para el uso de mensajes emergentes
    private void mostrarToast(String mensaje) {
        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    //Método para el uso del boton back del mismo celular
    public void onBackPressed() {
        mostrarDialogoConfirmacion();
    }

    //Método para el uso de animaciones en el flujo del activity
    public void fade() {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    // Agrega banderas al intent para limpiar la pila de actividades
    // y comenzar una nueva tarea al reiniciar la actividad, la cual se fectuará al presionar el boton Reiniciar.
    private void reiniciarActivity() {
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(intent);
    }

    //Método para el uso del AlertDialog
    private void mostrarDialogoConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Volver a la selección de unidad y perder el progreso?").setTitle("Confirmación");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(SumaEZ.this, SeleccionUnidad.class));
                fade();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Maneja el cambio hacia los juegos
    private void cambioJuego(){
        //Se crea una instancia de random y se genera un numero aleatorio para la seleccion de juego
        Random random = new Random();
        int juego = random.nextInt(2);

        //Si tiene 4 o mas respuestas correctas aparece un juego
        if (respuestasCorrectas>=4){
            switch (juego){
                case 0:
                    Intent intent = new Intent(SumaEZ.this, JuegoX0.class);
                    intent.putExtra("proximaActivity", 2);
                    startActivity(intent);
                    fade();
                    finish();
                    break;
                case 1:
                    Intent intent2 = new Intent(SumaEZ.this, JuegoAdivinanzas.class);
                    intent2.putExtra("proximaActivity", 2);
                    startActivity(intent2);
                    fade();
                    finish();
                    break;
            }
        } else {
            startActivity(new Intent(SumaEZ.this, RestaEZ.class));
            fade();
            finish();
        }
    }
}

