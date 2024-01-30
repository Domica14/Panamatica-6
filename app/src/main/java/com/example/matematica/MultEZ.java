package com.example.matematica;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class MultEZ extends AppCompatActivity {

    TextView txtPregunta, txtResultado;
    Button[] btnOpciones;
    int respuestaCorrecta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mult_ez);

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

    //Numeros aleatorios del 1 al 12 (Podría Variar)
    private void generarOperacion() {
        Random random = new Random();
        int numero1 = random.nextInt(12) + 1;
        int numero2 = random.nextInt(12) + 1;
        respuestaCorrecta = numero1 * numero2;

        txtPregunta.setText(numero1 + " x " + numero2 + " = ?");

        // Colocar la respuesta correcta en uno de los botones
        int botonRespuestaCorrecta = random.nextInt(4); // Número aleatorio entre 0 y 3
        btnOpciones[botonRespuestaCorrecta].setText(String.valueOf(respuestaCorrecta));

        // Colocar respuestas incorrectas en los otros botones
        for (int i = 0; i < 4; i++) {
            if (i != botonRespuestaCorrecta) {
                int respuestaIncorrecta = generarRespuestaIncorrecta();
                btnOpciones[i].setText(String.valueOf(respuestaIncorrecta));
            }
        }
    }

    private int generarRespuestaIncorrecta() {
        Random random = new Random();
        int respuestaIncorrecta;
        do {
            int rango = 20; // Rango de números para respuestas incorrectas
            respuestaIncorrecta = respuestaCorrecta + random.nextInt(rango) - rango / 2;
        } while (respuestaIncorrecta == respuestaCorrecta);
        return respuestaIncorrecta;
    }

    private void verificarRespuesta(Button opcionSeleccionada) {
        int respuestaUsuario = Integer.parseInt(opcionSeleccionada.getText().toString());
        if (respuestaUsuario == respuestaCorrecta) {
            txtResultado.setText("¡Correcto!");
        } else {
            txtResultado.setText("Incorrecto. La respuesta correcta es " + respuestaCorrecta);
        }
    }
}
