package com.example.matematica.menu;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.matematica.R;

public class MainActivity extends AppCompatActivity {

    // Declaración de variables para manejar los elementos de la vista
    private Button btnEmpezar;
    private MediaPlayer mediaPlayer, Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Bloquea la orientación de pantalla

        // Asociación de la variable declarada con el elemento de la vista
        btnEmpezar = findViewById(R.id.btnEmpezar);

        mediaPlayer = MediaPlayer.create(this, R.raw.backgroundsound);
        mediaPlayer.setLooping(true);
        Btn = MediaPlayer.create(this, R.raw.popsound);

        // Reinicia SharedPreferences al inicio de la aplicación
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        reiniciarSharedPreferences(sharedPreferences);

        // Listener para determinar la acción a realizar al presionar el botón
        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Btn.start();
                fade(); // Llamado al método de la transición
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pausa la reproducción de música cuando la actividad está en pausa
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reanuda la reproducción de música cuando la actividad se reanuda
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Libera los recursos del MediaPlayer cuando la actividad se destruye
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // Método que se encarga de la transición entre activities
    public void fade() {
        // Método para realizar el cambio de activity
        startActivity(new Intent(MainActivity.this, SeleccionUnidad.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out); // Método para cambiar la transición
        finish(); // Se finaliza la activity actual
    }

    private void reiniciarSharedPreferences(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Borrar todos los datos
        editor.apply();
    }
}
