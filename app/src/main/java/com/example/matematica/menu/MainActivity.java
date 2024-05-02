package com.example.matematica.menu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.matematica.R;

public class MainActivity extends AppCompatActivity {

    //Declaracion de variables para manejar los elementos de la vista
    private Button btnEmpezar;

    private MediaPlayer mediaPlayer, Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);      //Bloquea la orientacion de pantalla

        //Asociacion de la variable declarada con el elemento de la vista
        btnEmpezar = findViewById(R.id.btnEmpezar);

        mediaPlayer = MediaPlayer.create(this, R.raw.backgroundsound);
        mediaPlayer.setLooping(true);
        Btn = MediaPlayer.create(this, R.raw.popsound);


        //Listener para determinar la accion a realizar al presionar el boton
        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Btn.start();
                fade();           //Llamado al metodo de la transicion
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

    //Metodo que se encarga de la transicion entre activities
    public void fade(){
        //Metodo para realizar el cambio de activity
        startActivity(new Intent(MainActivity.this, SeleccionUnidad.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);         //Metodo para cambiar la transicion
        finish();       //Se finaliza la activity actual
    }
}
