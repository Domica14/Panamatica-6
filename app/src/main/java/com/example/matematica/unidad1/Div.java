package com.example.matematica.unidad1;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
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

public class Div extends AppCompatActivity {

    TextView txtPregunta, txtResultado, Respuesta, ResPista;

    Button btnFinal, btnVolver, btnContinuar, btnPista;
    Button btnVerificar;
    double respuestaCorrecta;

    MediaPlayer mp, mp2;

    int respuestasCorrectas = 0, Count = 1;

    private SharedPreferences sharedPreferences; // SharedPreferences para almacenar los puntos
    private SharedPreferences.Editor editor; // Editor de SharedPreferences

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_div);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);      //Bloquea la orientacion de pantalla

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        txtPregunta = findViewById(R.id.txtPregunta);
        txtResultado = findViewById(R.id.txtResultado);
        Respuesta = findViewById(R.id.Respuesta);
        btnVerificar = findViewById(R.id.btnVerificar);
        btnFinal = findViewById(R.id.btnFinal);
        btnVolver = findViewById(R.id.btnVolver);
        btnContinuar = findViewById(R.id.btnNext);
        ResPista = findViewById(R.id.ResPista);
        btnPista = findViewById(R.id.btnPista);
        btnFinal.setEnabled(false);
        btnVolver.setEnabled(false);
        btnContinuar.setEnabled(false);

        mp = MediaPlayer.create(this, R.raw.button);
        mp2 = MediaPlayer.create(this, R.raw.soundb);

        int pistas = sharedPreferences.getInt("pistas", 0);
        btnPista.setText("Pistas: " + pistas);

        if(pistas >= 1){

            btnPista.setVisibility(View.VISIBLE);

        }

        generarOperacion();

        //Texto el cual nos dará a conocer el intento en el que nos encontramos.
        txtResultado.setText("Problema: " + Count);

        btnPista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pistas = sharedPreferences.getInt("pistas", 0);
                if (pistas > 0) {
                    // Reducir el número de pistas
                    pistas--;
                    editor.putInt("pistas", pistas);
                    editor.apply();

                    // Actualizar el texto del botón de pistas
                    btnPista.setText("Pistas: " + pistas);

                    // Lógica para mostrar una pista al usuario
                    mostrarPista();

                    // Ocultar el botón de pistas si ya no hay pistas disponibles
                    if (pistas == 0) {
                        btnPista.setVisibility(View.INVISIBLE);
                    }
                }

            }
        });

        /*Funcionamiento del Botón Reiniciar, el cual nos permitirá comenzar desde el inicio los problemas.
          Este solo aparecerá cuando se terminen los 5 intentos.*/
        btnFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarActivity();
                fade();
            }
        });

        //Funcionamiento del boton volver, en caso de querer volver a la elección de unidad.
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoConfirmacion();
            }
        });

        //Funcionamiento del boton Continuar, el cual nos permitirá continuar con el flujo de ventanas.
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambioJuego();
            }
        });

        //Funcionamiento para el botón de verificar, el cual nos permitirá saber si la respuesta es correcta.
        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarRespuesta();
            }
        });
    }

    /*Generacion de numeros aleatorios para los problemas.
      Utilizamos esta funcion para que se pueda generar una operación completa.*/
    private void generarOperacion() {
        Random random = new Random();
        int numero1 = random.nextInt(12) + 1; //Generación aleatoria del 1 al 12.
        int numero2 = random.nextInt(12) + 1; //Generación aleatoria del 1 al 12.
        respuestaCorrecta = (double) numero1 / numero2;

        txtPregunta.setText(numero1 + " / " + numero2 + " = ?");
    }

    //Método que se encargará de verificar si nuestra respuesta es correcta.
    private void verificarRespuesta() {
        String respuestaStr = Respuesta.getText().toString().trim();

        // Verificar si el campo de respuesta no está vacío
        if (!respuestaStr.isEmpty()) {

            //Si el campo no esta vacío, redondeamos la respuesta del usuario y la respuesta verdadera a solo 1 decimal.
            double respuestaUsuario = Double.parseDouble(respuestaStr);
            respuestaUsuario = Math.round(respuestaUsuario * 10.0) / 10.0; // Redondear a 1 decimal
            respuestaCorrecta = Math.round(respuestaCorrecta * 10.0) / 10.0; // Redondear a 1 decimal

            // Incrementar el contador para llevar control de intentos.
            Count++;

            //Se compara la respuesta del usuario con la respuesta verdera para dar un veredicto.
            if (respuestaUsuario == respuestaCorrecta) {
                mostrarToast("¡Correcto!");
                respuestasCorrectas++;
                mp.start();
            } else {
                mostrarToast("Incorrecto. La respuesta correcta es " + respuestaCorrecta);
                mp2.start();
            }

            /*Mientras el contador sea menor o igual a 5 se seguiran generando operaciones.
              Una vez se culminen los 5 intentos, apareceran ciertos botones y nuestro resultado final.*/
            if (Count <= 5) {

                txtResultado.setText("Problema: " + Count);
                int pistas = sharedPreferences.getInt("pistas", 0);

                if(pistas >= 1){

                    btnPista.setVisibility(View.VISIBLE);


                } else {

                    btnPista.setVisibility(View.INVISIBLE);

                }

                ResPista.setVisibility(View.INVISIBLE);

                generarOperacion();
            } else {

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
                btnVerificar.setVisibility(View.GONE);
                btnPista.setVisibility(View.GONE);
                Respuesta.setVisibility(View.GONE);
                ResPista.setVisibility(View.GONE);

                TextView txtResultadoFinal = findViewById(R.id.txtResultadoFinal);
                txtResultadoFinal.setVisibility(View.VISIBLE);
                txtResultadoFinal.setText("Resultado: " + respuestasCorrectas + "/5");
            }

            //Si el campo de respuesta esta vacío se mostrará un mensaje el cual nos alertará que esta vacío y no avanzará el contador.
        } else {
            // Mostrar un mensaje indicando que el campo de respuesta está vacío
            mostrarToast("Por favor, ingresa tu respuesta antes de verificar.");
        }
    }

    private void mostrarPista() {
        double rango = 3;
        double respuestaMinima = respuestaCorrecta - rango + (int)(Math.random() * 3); // Agrega un valor aleatorio al mínimo
        double respuestaMaxima = respuestaCorrecta + rango - (int)(Math.random() * 3); // Resta un valor aleatorio al máximo

        // Muestra un mensaje con el rango de respuestas
        String mensajePista = "La respuesta está en el rango de " + Math.round(respuestaMinima) + " a " + Math.round(respuestaMaxima) + "\nRecuerda que el resultado puede ser decimal.";
        ResPista.setText(mensajePista);
        ResPista.setVisibility(View.VISIBLE);
        btnPista.setVisibility(View.INVISIBLE);
    }

    //Método el cual nos permite generar mensajes emergentes al usuario.
    private void mostrarToast(String mensaje) {
        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    //Método que permite que, al apretar el botón back del celular se pueda ejecutar cierta acción.
    public void onBackPressed() {
        mostrarDialogoConfirmacion();
    }

    //Para animaciones entre activitys.
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

    //Método que nos permitirá mostrar un alertDialog a la hora de querer retroceder mientras estamos en los problemas
    //o al llegar al de los intentos cuando seleccionamos el boton "Atras".
    private void mostrarDialogoConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Volver a la selección de unidad y perder el progreso?").setTitle("Confirmación");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(Div.this, SeleccionUnidad.class));
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
                    Intent intent = new Intent(Div.this, JuegoX0.class);
                    intent.putExtra("proximaActivity", 6);
                    startActivity(intent);
                    fade();
                    finish();
                    break;
                case 1:
                    Intent intent2 = new Intent(Div.this, JuegoAdivinanzas.class);
                    intent2.putExtra("proximaActivity", 6);
                    startActivity(intent2);
                    fade();
                    finish();
                    break;
            }
        } else {
            startActivity(new Intent(Div.this, Lecciones.class));
            fade();
            finish();
        }
    }
}