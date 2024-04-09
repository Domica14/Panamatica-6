package com.example.matematica.unidad1;

import android.content.DialogInterface;
import android.content.Intent;
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

public class Suma extends AppCompatActivity {

    TextView txtPregunta, txtResultado, Respuesta;

    Button btnFinal, btnVolver, btnContinuar;
    Button btnVerificar;
    int respuestaCorrecta, Count = 1;

    MediaPlayer mp, mp2;

    int respuestasCorrectas = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suma);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);      //Bloquea la orientacion de pantalla

        txtPregunta = findViewById(R.id.txtPregunta);
        txtResultado = findViewById(R.id.txtResultado);
        Respuesta = findViewById(R.id.Respuesta);
        btnVerificar = findViewById(R.id.btnVerificar);
        btnFinal = findViewById(R.id.btnFinal);
        btnVolver = findViewById(R.id.btnVolver);
        btnContinuar = findViewById(R.id.btnNext);
        btnFinal.setEnabled(false);
        btnVolver.setEnabled(false);
        btnContinuar.setEnabled(false);

        mp = MediaPlayer.create(this, R.raw.button);
        mp2 = MediaPlayer.create(this, R.raw.soundb);

        generarOperacion();

        //Contador de Intentos
        txtResultado.setText("Intento: " + Count);

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

        //Funcionamiento del boton Verificar
        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarRespuesta();
            }
        });
    }

    //Generacion de numeros aleatorios para los problemas
    private void generarOperacion() {
        Random random = new Random();
        int numero1 = random.nextInt(100) + 1; //Generación Aleatoria del 1 al 100
        int numero2 = random.nextInt(100) + 1; //Generación Aleatoria del 1 al 100
        respuestaCorrecta = numero1 + numero2;

        txtPregunta.setText(numero1 + " + " + numero2 + " = ?");
    }

    //Método que verifica si la respuesta del usuario es correcta
    private void verificarRespuesta() {
        String respuestaStr = Respuesta.getText().toString().trim();

        // Verificar si el campo de respuesta no está vacío
        if (!respuestaStr.isEmpty()) {
            // Incrementar el contador
            Count++;

            int respuestaUsuario = Integer.parseInt(respuestaStr);

            //Compara la respuesta del usuario con la verdarera para dar un veredicto.
            if (respuestaUsuario == respuestaCorrecta) {
                mostrarToast("¡Correcto!");
                respuestasCorrectas++;
                mp.start();
            } else {
                mostrarToast("Incorrecto. La respuesta correcta es " + respuestaCorrecta);
                mp2.start();
            }

            //Mientras los intentos sean menor o igual a 5, se seguiran creando operaciones.
            if (Count <= 5) {
                txtResultado.setText("Intento: " + Count);
                generarOperacion();
                //Una vez pase de 5, apareceran algunos botones con sus respectivas acciones
                //Ademas de que podremos ver nuestro resultado.
            } else {
                btnFinal.setEnabled(true);
                btnFinal.setVisibility(View.VISIBLE);
                btnVolver.setEnabled(true);
                btnVolver.setVisibility(View.VISIBLE);
                btnContinuar.setEnabled(true);
                btnContinuar.setVisibility(View.VISIBLE);
                txtPregunta.setVisibility(View.GONE);
                txtResultado.setVisibility(View.GONE);
                btnVerificar.setVisibility(View.GONE);
                Respuesta.setVisibility(View.GONE);

                TextView txtResultadoFinal = findViewById(R.id.txtResultadoFinal);
                txtResultadoFinal.setVisibility(View.VISIBLE);
                txtResultadoFinal.setText("Resultado: " + respuestasCorrectas + "/5");
            }

        } else {
            // Mostrar un mensaje indicando que el campo de respuesta está vacío
            mostrarToast("Por favor, ingresa tu respuesta antes de verificar.");
        }
    }

    //Metodo para el uso de mensajes emergentes
    private void mostrarToast(String mensaje) {
        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    //Metodo para el uso del boton back del mismo celular.
    public void onBackPressed() {
        mostrarDialogoConfirmacion();
    }

    //Metodo para las animaciones en el flujo del activity
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

    //Metodo para el uso del AlertDialog
    private void mostrarDialogoConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Volver a la selección de unidad y perder el progreso?").setTitle("Confirmación");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(Suma.this, SeleccionUnidad.class));
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
                    Intent intent = new Intent(Suma.this, JuegoX0.class);
                    intent.putExtra("proximaActivity", 4);
                    startActivity(intent);
                    fade();
                    finish();
                    break;
                case 1:
                    Intent intent2 = new Intent(Suma.this, JuegoAdivinanzas.class);
                    intent2.putExtra("proximaActivity", 4);
                    startActivity(intent2);
                    fade();
                    finish();
                    break;
            }
        } else {
            startActivity(new Intent(Suma.this, Resta.class));
            fade();
            finish();
        }
    }
}



