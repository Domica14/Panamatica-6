package com.example.matematica.menu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.matematica.R;

public class SeleccionUnidad extends AppCompatActivity {
    //Declaracion de variables para manejar los elementos de la vista
    private ImageView unidad1;              //A futuro se pueden agregar las del resto de unidades
    private Button btnAtras, btnTienda;
    //Provicional para acceder a los juegos

    private MediaPlayer Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_unidad);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);      //Bloquea la orientacion de pantalla

        //Asociacion de variables con los elementos de la vista
        unidad1 = findViewById(R.id.unidad1);
        btnAtras = findViewById(R.id.btnRegresar);
        btnTienda = findViewById(R.id.btnTienda);
        Btn = MediaPlayer.create(this, R.raw.popsound);


        //Listeners para que se realize una accion al presionar la imagen
        unidad1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Btn.start();
                startActivity(new Intent(SeleccionUnidad.this, SeleccionDificultad.class));
                fade();
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Btn.start();
                startActivity(new Intent(SeleccionUnidad.this, MainActivity.class));
                fade();
            }
        });

        btnTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Btn.start();
                startActivity(new Intent(SeleccionUnidad.this, Tienda.class));
                fade();
            }
        });
    }

    /*Se sobrescribe el metodo de onBackPressed para que realize el retroceso a la activity anterior al presionar
      el boton de atras del celular
     */
    @Override
    public void onBackPressed(){
        startActivity(new Intent(SeleccionUnidad.this, MainActivity.class));
        fade();
        finish();
    }

    //Metodo que se encarga de la transicion entre activities
    public void fade(){
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);         //Metodo para cambiar la transicion
        finish();       //Se finaliza la activity actual
    }
}