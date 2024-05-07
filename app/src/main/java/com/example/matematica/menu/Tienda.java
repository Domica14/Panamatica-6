package com.example.matematica.menu;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.matematica.R;

public class Tienda extends AppCompatActivity {

    private SharedPreferences sharedPreferences; // SharedPreferences para almacenar los puntos y pistas
    private SharedPreferences.Editor editor; // Editor de SharedPreferences
    private ImageView btnRecompensa1;
    private Button btnRegresar;
    private TextView txtPuntos, txtPistas;
    private int pistas; // Cantidad de pistas con las que cuenta el jugador
    private MediaPlayer Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Bloquea la orientacion de pantalla

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        btnRecompensa1 = findViewById(R.id.recompensa1);
        btnRegresar = findViewById(R.id.btnRegresarTienda);
        txtPuntos = findViewById(R.id.txtPuntos);
        txtPistas = findViewById(R.id.Pistas);

        Btn = MediaPlayer.create(this, R.raw.popsound);

        // Obtener los puntos y pistas guardados de SharedPreferences y mostrarlos
        int puntos = sharedPreferences.getInt("puntos", 0); // Para llevar el control de puntos.
        pistas = sharedPreferences.getInt("pistas", 0); // Recuperar el número de pistas
        txtPuntos.setText("Puntos\n" + puntos);
        txtPistas.setText("Pistas\n" + pistas);

        btnRecompensa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Btn.start();
                int puntos = sharedPreferences.getInt("puntos", 0);
                if (puntos >= 25) {
                    puntos -= 25;
                    pistas += 1;
                    // Guardar los puntos y pistas actualizados en SharedPreferences
                    editor.putInt("puntos", puntos);
                    editor.putInt("pistas", pistas);
                    editor.apply();
                    txtPuntos.setText("Puntos\n" + puntos);
                    txtPistas.setText("Pistas\n" + pistas);
                } else {
                    mostrarToast("No tienes los puntos necesarios...");
                }
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Btn.start();
                Intent intent = new Intent(Tienda.this, SeleccionDificultad.class);
                startActivity(intent);
                fade();
                finish();
            }
        });
    }

    /* Se sobrescribe el método onBackPressed para que realize el retroceso a la activity anterior al presionar
      el botón de atrás del celular */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Tienda.this, SeleccionDificultad.class);
        intent.putExtra("pistas", pistas);
        startActivity(intent);
        fade();
        finish();
    }

    // Método para el uso de los mensajes emergentes.
    private void mostrarToast(String mensaje) {
        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    // Método que se encarga de la transición entre activities
    public void fade() {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out); // Método para cambiar la transición
        finish(); // Se finaliza la activity actual
    }
}
