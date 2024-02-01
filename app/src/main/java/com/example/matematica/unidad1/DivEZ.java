package com.example.matematica.unidad1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.matematica.R;

import java.util.Random;

public class DivEZ extends AppCompatActivity {

    TextView txtPregunta, txtResultado;
    Button[] btnOpciones;
    double respuestaCorrecta;

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

    //Dividendo y Divisor con limite de 12, de manera aleatoria
    private void generarOperacion() {
        Random random = new Random();
        int numero1 = random.nextInt(12) + 1;
        int numero2 = random.nextInt(12) + 1;
        respuestaCorrecta = (double) numero1 / numero2;

        txtPregunta.setText(numero1 + " / " + numero2 + " = ?");

        // Colocar la respuesta correcta en uno de los botones
        int botonRespuestaCorrecta = random.nextInt(4); // Número aleatorio entre 0 y 3
        btnOpciones[botonRespuestaCorrecta].setText(String.format("%.1f", respuestaCorrecta));

        // Colocar respuestas incorrectas en los otros botones
        for (int i = 0; i < 4; i++) {
            if (i != botonRespuestaCorrecta) {
                double respuestaIncorrecta;
                do {
                    respuestaIncorrecta = generarRespuestaIncorrecta();
                } while (respuestaIncorrecta == respuestaCorrecta);
                btnOpciones[i].setText(String.format("%.1f", respuestaIncorrecta));
            }
        }
    }

    private double generarRespuestaIncorrecta() {
        Random random = new Random();
        double respuestaIncorrecta;
        int divisorMaximo = 12;
        do {
            int divisor = random.nextInt(divisorMaximo) + 1;
            respuestaIncorrecta = (double) (random.nextInt(12) + 1) / divisor;
        } while (respuestaIncorrecta == respuestaCorrecta);
        return respuestaIncorrecta;
    }

    private void verificarRespuesta(Button opcionSeleccionada) {
        double respuestaUsuario = Double.parseDouble(opcionSeleccionada.getText().toString());
        if (Math.abs(respuestaUsuario - respuestaCorrecta) < 0.001) {
            txtResultado.setText("¡Correcto!");
        } else {
            txtResultado.setText("Incorrecto. La respuesta correcta es " + respuestaCorrecta);
        }
    }
}
