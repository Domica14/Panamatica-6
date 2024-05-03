package com.example.matematica.unidad1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.matematica.R;
import com.example.matematica.menu.SeleccionDificultad;

public class Lecciones extends AppCompatActivity {

    private ImageView leccion1, leccion2;
    private Button Atras;

    MediaPlayer Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecciones);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);      //Bloquea la orientacion de pantalla

        leccion1 = findViewById(R.id.imageView);
        leccion2 = findViewById(R.id.imageView2);
        Atras = findViewById(R.id.Atras);
        Btn = MediaPlayer.create(this, R.raw.popsound);


        //Funcionamiento del boton de Atras, volviendo asi a la seleccion de dificultad
        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Btn.start();
                startActivity(new Intent(Lecciones.this, SeleccionDificultad.class));
                fade();
            }
        });

        /*Una vez se toque la lección 1, la acción mandará al material que contiene dicha lección.
         Esto en modo facil*/
        leccion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Btn.start();
                startActivity(new Intent(Lecciones.this, TeoriaAdiSus.class));
                fade();
            }
        });


        /*Una vez se toque la lección 2, la acción mandará al material que contiene dicha lección
         esto en modo facil*/
        leccion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Btn.start();
                startActivity(new Intent(Lecciones.this, TeoriaMultiDiv.class));
                fade();
            }
        });


    }

    /*Se sobrescribe el metodo de onBackPressed para que realize el retroceso a la activity anterior al presionar
      el boton de atras del celular
     */
    public void onBackPressed(){
        startActivity(new Intent(Lecciones.this, SeleccionDificultad.class));
        fade();
        finish();
    }

    //Metodo que se encarga de la transicion entre activities
    public void fade(){
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);         //Metodo para cambiar la transicion
        finish();       //Se finaliza la activity actual
    }

}