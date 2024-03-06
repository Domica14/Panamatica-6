package com.example.matematica.unidad1;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
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

public class DivEZ extends AppCompatActivity {

    TextView txtPregunta, txtResultado;
    Button[] btnOpciones;

    Button btnFinal, btnVolver, btnContinuar;

    int Count = 1;

    double respuestaCorrecta;
    int respuestasCorrectas = 0;
    Set<Double> respuestasGeneradas; // Conjunto para mantener las respuestas generadas

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_div_ez);

        txtPregunta = findViewById(R.id.txtPregunta);
        txtResultado = findViewById(R.id.txtResultado);
        btnOpciones = new Button[]{
                findViewById(R.id.btnOpcion1),
                findViewById(R.id.btnOpcion2),
                findViewById(R.id.btnOpcion3),
                findViewById(R.id.btnOpcion4)
        };

        respuestasGeneradas = new HashSet<>(); // Inicializar el conjunto

        btnFinal = findViewById(R.id.btnFinal);
        btnVolver = findViewById(R.id.btnVolver);
        btnContinuar = findViewById(R.id.btnNext);

        btnFinal.setEnabled(false);
        btnFinal.setVisibility(View.INVISIBLE);
        btnVolver.setEnabled(false);
        btnVolver.setVisibility(View.INVISIBLE);
        btnContinuar.setEnabled(false);
        btnContinuar.setVisibility(View.INVISIBLE);

        generarOperacion();

        //Texto que permite ver el intento en el que se encuentra el usuario.
        txtResultado.setText("Intento: " + Count);

        //Funcionamiento para el boton de reiniciar.
        btnFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarActivity();
                fade();
            }
        });

        //Funcionamiento para el boton Volver.
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoConfirmacion();
            }
        });

        //Funcionamiento para el boton continuar.
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambioJuego();
            }
        });

        //Funcionamiento para los botones que contienen la posible respuesta.
        for (Button btn : btnOpciones) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verificarRespuesta((Button) v);
                }
            });
        }
    }

    //Dividendo y Divisor con límite de 12, de manera aleatoria
    private void generarOperacion() {
        Random random = new Random();
        int numero1 = random.nextInt(12) + 1; //Generación Aleatotia del 1 al 12
        int numero2 = random.nextInt(12) + 1; //Generación Aleatotia del 1 al 12
        respuestaCorrecta = (double) numero1 / numero2;
        respuestaCorrecta = Math.round(respuestaCorrecta * 10.0) / 10.0; //La respuesta correcta se redondea para evitar numeros muy extensos.

        txtPregunta.setText(numero1 + " / " + numero2 + " = ?");

        respuestasGeneradas.clear(); // Limpiar el conjunto antes de generar nuevas respuestas

        // Colocar la respuesta correcta en uno de los botones
        int botonRespuestaCorrecta = random.nextInt(4); // Número aleatorio entre 0 y 3
        btnOpciones[botonRespuestaCorrecta].setText(String.format("%.1f", respuestaCorrecta));
        respuestasGeneradas.add(respuestaCorrecta); // Agregar la respuesta correcta al conjunto

        //Ciclo que se repetirá hasta que todas las opciones incorrectas esten ubicadas.
        for (int i = 0; i < 4; i++) {
            if (i != botonRespuestaCorrecta) {
                double respuestaIncorrecta;
                do {
                    respuestaIncorrecta = generarRespuestaIncorrecta();
                } while (respuestasGeneradas.contains(respuestaIncorrecta)); // Verificar si la respuesta ya está en el conjunto
                respuestaIncorrecta = Math.round(respuestaIncorrecta * 10.0) / 10.0; // Redondear a un decimal
                btnOpciones[i].setText(String.format("%.1f", respuestaIncorrecta));
                respuestasGeneradas.add(respuestaIncorrecta); // Agregar la respuesta incorrecta al conjunto
            }
        }
    }

    //Método que se encarga de generar las respuestas incorrectas.
    private double generarRespuestaIncorrecta() {
        Random random = new Random();
        double rango = 1.0; // Define el rango de diferencia permitido
        double respuestaIncorrecta;
        Set<Double> respuestasIntentadas = new HashSet<>(); // Conjunto para evitar respuestas duplicadas

        do {
            double diferencia = (random.nextDouble() * 2 - 1) * rango; // Genera una diferencia aleatoria dentro del rango
            respuestaIncorrecta = respuestaCorrecta + diferencia;
            respuestaIncorrecta = Math.round(respuestaIncorrecta * 10.0) / 10.0; // Redondear a un decimal
            respuestasIntentadas.add(respuestaIncorrecta); // Agrega la respuesta intentada al conjunto
        } while (respuestasIntentadas.size() < 4 && (respuestasGeneradas.contains(respuestaIncorrecta) || respuestaIncorrecta == respuestaCorrecta)); // Verifica si la respuesta ya está en el conjunto o es igual a la respuesta correcta

        if (respuestasIntentadas.size() == 4) {
            // Si todas las respuestas dentro del rango están intentadas y no se puede encontrar una única respuesta única, se selecciona una respuesta aleatoria que no esté en el conjunto de respuestas generadas.
            do {
                respuestaIncorrecta = (random.nextDouble() * 2 - 1) * rango; // Genera una respuesta aleatoria dentro del rango
                respuestaIncorrecta = Math.round(respuestaIncorrecta * 10.0) / 10.0; // Redondear a un decimal
            } while (respuestasGeneradas.contains(respuestaIncorrecta));
        }

        return respuestaIncorrecta;
    }

    //Método que se encarga de verificar si el botón seleccionado es el correcto o no.
    private void verificarRespuesta(Button opcionSeleccionada) {
        double respuestaUsuario = Double.parseDouble(opcionSeleccionada.getText().toString());
        respuestaUsuario = Math.round(respuestaUsuario * 10.0) / 10.0; // Redondear a un decimal
        Count++;

        //Se compara la seleccion del usuario con la respuesta verdadera para dar un veredicto.
        if (respuestaUsuario == respuestaCorrecta) {
            mostrarToast("¡Correcto!");
            respuestasCorrectas++;
        } else {
            mostrarToast("Incorrecto. La respuesta correcta es " + respuestaCorrecta);
        }

        //Mientras el contador se mantenga debajo o igual a 5, se seguiran efectuando las operaciones.
        if (Count <= 5) {
            txtResultado.setText("Intento: " + Count);
            generarOperacion();

            //Una ves pase de 5, apareceran los botones que tendrán cada uno una función.
            //Aquí podremos ver nuestro resultado de igual manera.
        } else {
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

    //Método para el uso de los mensajes emergentes.
    private void mostrarToast(String mensaje) {
        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    //Método para el boton de back del mismo celular, y que este pueda efectuar cierta acción.
    public void onBackPressed() {
        mostrarDialogoConfirmacion();
    }

    //Animacion para el flujo de la activity.
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

    //Método para el uso del alertDialog para cuando se desee retroceder.
    private void mostrarDialogoConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Volver a la selección de unidad y perder el progreso?").setTitle("Confirmación");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(DivEZ.this, SeleccionUnidad.class));
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
                    Intent intent = new Intent(DivEZ.this, JuegoX0.class);
                    intent.putExtra("proximaActivity", 6);
                    startActivity(intent);
                    fade();
                    finish();
                    break;
                case 1:
                    Intent intent2 = new Intent(DivEZ.this, JuegoAdivinanzas.class);
                    intent2.putExtra("proximaActivity", 6);
                    startActivity(intent2);
                    fade();
                    finish();
                    break;
            }
        } else {
            startActivity(new Intent(DivEZ.this, Lecciones.class));
            fade();
            finish();
        }
    }
}

