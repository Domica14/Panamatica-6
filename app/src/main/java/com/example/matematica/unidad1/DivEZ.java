package com.example.matematica.unidad1;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.matematica.R;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DivEZ extends AppCompatActivity {

    TextView txtPregunta, txtResultado;
    Button[] btnOpciones;
    double respuestaCorrecta;
    Set<Double> respuestasGeneradas; // Conjunto para mantener las respuestas generadas

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

        generarOperacion();

        for (Button btn : btnOpciones) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verificarRespuesta((Button) v);
                }
            });
        }
    }

    // Dividendo y Divisor con límite de 12, de manera aleatoria
    private void generarOperacion() {
        Random random = new Random();
        int numero1 = random.nextInt(100) + 1;
        int numero2 = random.nextInt(12) + 1;
        respuestaCorrecta = (double) numero1 / numero2;
        respuestaCorrecta = Math.round(respuestaCorrecta * 10.0) / 10.0; // Redondear a un decimal

        txtPregunta.setText(numero1 + " / " + numero2 + " = ?");

        respuestasGeneradas.clear(); // Limpiar el conjunto antes de generar nuevas respuestas

        // Colocar la respuesta correcta en uno de los botones
        int botonRespuestaCorrecta = random.nextInt(4); // Número aleatorio entre 0 y 3
        btnOpciones[botonRespuestaCorrecta].setText(String.format("%.1f", respuestaCorrecta));
        respuestasGeneradas.add(respuestaCorrecta); // Agregar la respuesta correcta al conjunto

        // Colocar respuestas incorrectas en los otros botones
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


    private void verificarRespuesta(Button opcionSeleccionada) {
        double respuestaUsuario = Double.parseDouble(opcionSeleccionada.getText().toString());
        respuestaUsuario = Math.round(respuestaUsuario * 10.0) / 10.0; // Redondear a un decimal
        if (respuestaUsuario == respuestaCorrecta) {
            txtResultado.setText("¡Correcto!");
        } else {
            txtResultado.setText("Incorrecto. La respuesta correcta es " + respuestaCorrecta);
        }
    }
}
