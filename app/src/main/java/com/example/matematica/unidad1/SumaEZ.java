package com.example.matematica.unidad1;

import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.matematica.R;

import java.util.Random;

public class SumaEZ extends AppCompatActivity {

    TextView txtPregunta, txtResultado;
    Button[] btnOpciones;
    int respuestaCorrecta, Count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suma_ez);

        txtPregunta = findViewById(R.id.txtPregunta);
        txtResultado = findViewById(R.id.txtResultado);
        btnOpciones = new Button[]{
                findViewById(R.id.btnOpcion1),
                findViewById(R.id.btnOpcion2),
                findViewById(R.id.btnOpcion3),
                findViewById(R.id.btnOpcion4)


        };

            generarOperacion();

        txtResultado.setText("Intento: " + Count);

        for (Button btn : btnOpciones) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verificarRespuesta((Button) v);
                }
            });
        }
    }

    //Generacion de numeros aleatorios para los problemas
    private void generarOperacion() {
        Random random = new Random();
        int numero1 = random.nextInt(100);
        int numero2 = random.nextInt(100);
        respuestaCorrecta = numero1 + numero2;

        txtPregunta.setText(numero1 + " + " + numero2 + " = ?");

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

            Count = Count + 1;


        if (respuestaUsuario == respuestaCorrecta) {
            mostrarToast("¡Correcto!");
        } else {
            mostrarToast("Incorrecto. La respuesta correcta es " + respuestaCorrecta);
        }

        txtResultado.setText("Intento: " + Count);

        if(Count <= 5) {

            generarOperacion(); // Generar una nueva operación después de verificar la respuesta

        }else{

            startActivity(new Intent(SumaEZ.this, RestaEZ.class));
            fade();

        }

    }


    private void mostrarToast(String mensaje) {
        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 100); // Ajusta la posición del Toast
        toast.show();
    }

    public void fade(){
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);         //Metodo para cambiar la transicion
        finish();       //Se finaliza la activity actual
    }


}
