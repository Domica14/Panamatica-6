package com.example.matematica.unidad1;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.matematica.R;

import java.util.Random;

public class Mult extends AppCompatActivity {

    TextView txtPregunta, txtResultado, Respuesta;
    Button btnVerificar;
    int respuestaCorrecta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mult);

        txtPregunta = findViewById(R.id.txtPregunta);
        txtResultado = findViewById(R.id.txtResultado);
        Respuesta = findViewById(R.id.Respuesta);
        btnVerificar = findViewById(R.id.btnVerificar);

        generarOperacion();

        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarRespuesta();
            }
        });
    }

    //Numeros aleatorios del 1 al 12 (Podría Variar)
    private void generarOperacion() {
        Random random = new Random();
        int numero1 = random.nextInt(12) + 1;
        int numero2 = random.nextInt(12) + 1;
        respuestaCorrecta = numero1 * numero2;

        txtPregunta.setText(numero1 + " x " + numero2 + " = ?");
    }

    private void verificarRespuesta() {
        String respuestaStr = Respuesta.getText().toString().trim();
        if (!respuestaStr.isEmpty()) {
            int respuestaUsuario = Integer.parseInt(respuestaStr);
            if (respuestaUsuario == respuestaCorrecta) {
                txtResultado.setText("¡Correcto!");
            } else {
                txtResultado.setText("Incorrecto. La respuesta correcta es " + respuestaCorrecta);
            }
        }
    }
}
