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
import com.example.matematica.menu.SeleccionUnidad;

import java.util.Random;
import java.util.HashSet;
import java.util.Set;

public class RestaEZ extends AppCompatActivity {

    TextView txtPregunta, txtResultado;
    Button[] btnOpciones;

    Button btnFinal, btnVolver, btnContinuar;

    int respuestaCorrecta, Count = 1;
    Set<Integer> respuestasAsignadas = new HashSet<>();
    int respuestasCorrectas = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resta_ez);

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

        generarOperacion();

        txtResultado.setText("Intento: " + Count);

        btnFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarActivity();
                fade();
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoConfirmacion();
            }
        });

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        for (Button btn : btnOpciones) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verificarRespuesta((Button) v);
                }
            });
        }
    }

    private void generarOperacion() {
        Random random = new Random();
        int numero1 = random.nextInt(100);
        int numero2 = random.nextInt(100);
        respuestaCorrecta = numero1 - numero2;

        txtPregunta.setText(numero1 + " - " + numero2 + " = ?");

        respuestasAsignadas.clear();

        int botonRespuestaCorrecta = random.nextInt(4);
        btnOpciones[botonRespuestaCorrecta].setText(String.valueOf(respuestaCorrecta));
        respuestasAsignadas.add(respuestaCorrecta);

        for (int i = 0; i < 4; i++) {
            if (i != botonRespuestaCorrecta) {
                int respuestaIncorrecta;
                do {
                    respuestaIncorrecta = generarRespuestaIncorrecta();
                } while (respuestasAsignadas.contains(respuestaIncorrecta));
                btnOpciones[i].setText(String.valueOf(respuestaIncorrecta));
                respuestasAsignadas.add(respuestaIncorrecta);
            }
        }
    }

    private int generarRespuestaIncorrecta() {
        Random random = new Random();
        int rango = 20;
        return respuestaCorrecta + random.nextInt(rango) - rango / 2;
    }

    private void verificarRespuesta(Button opcionSeleccionada) {
        int respuestaUsuario = Integer.parseInt(opcionSeleccionada.getText().toString());
        Count++;

        if (respuestaUsuario == respuestaCorrecta) {
            mostrarToast("¡Correcto!");
            respuestasCorrectas++;
        } else {
            mostrarToast("Incorrecto. La respuesta correcta es " + respuestaCorrecta);
        }

        if (Count <= 5) {
            txtResultado.setText("Intento: " + Count);
            generarOperacion();
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

    private void mostrarToast(String mensaje) {
        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public void onBackPressed() {
        mostrarDialogoConfirmacion();
    }

    public void fade() {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void reiniciarActivity() {
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(intent);
    }

    private void mostrarDialogoConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Volver a la selección de unidad y perder el progreso?").setTitle("Confirmación");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(RestaEZ.this, SeleccionUnidad.class));
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
}
