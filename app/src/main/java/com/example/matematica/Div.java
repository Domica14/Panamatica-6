package com.example.matematica;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class Div extends AppCompatActivity {

    TextView txtPregunta, txtResultado, Respuesta;
    Button btnVerificar;
    double respuestaCorrecta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_div);

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

    //Dividendo y Divisor con limite de 12, de manera aleatoria
    private void generarOperacion() {
        Random random = new Random();
        int numero1 = random.nextInt(12) + 1;
        int numero2 = random.nextInt(12) + 1;
        respuestaCorrecta = (double) numero1 / numero2;

        txtPregunta.setText(numero1 + " / " + numero2 + " = ?");
    }

    private void verificarRespuesta() {
        String respuestaStr = Respuesta.getText().toString().trim();
        if (!respuestaStr.isEmpty()) {
            double respuestaUsuario = Double.parseDouble(respuestaStr);
            if (Math.abs(respuestaUsuario - respuestaCorrecta) < 0.001) {
                txtResultado.setText("¡Correcto!");
            } else {
                txtResultado.setText("Incorrecto. La respuesta correcta es " + respuestaCorrecta);
            }
        }
    }
}
