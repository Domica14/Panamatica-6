package com.example.matematica.menu;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.matematica.R;
import com.example.matematica.juegos.JuegoX0;
import com.example.matematica.unidad1.Div;
import com.example.matematica.unidad1.DivEZ;
import com.example.matematica.unidad1.SeleccionDificultad;

public class SeleccionUnidad extends AppCompatActivity {
    //Declaracion de variables para manejar los elementos de la vista
    private ImageView unidad1;              //A futuro se pueden agregar las del resto de unidades
    private Button btnAtras;
    //Provicional para acceder a los juegos
    private ImageView unidad2, unidad3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_unidad);

        //Asociacion de variables con los elementos de la vista
        unidad1 = findViewById(R.id.unidad1);
        btnAtras = findViewById(R.id.btnRegresar);
        unidad2 = findViewById(R.id.unidad2);           //Provisional para juego
        unidad3 = findViewById(R.id.unidad3);           //Provisional para experimentar problemas


        //Listeners para que se realize una accion al presionar la imagen
        unidad1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SeleccionUnidad.this, SeleccionDificultad.class));
                fade();
            }
        });

        //Provisional para juego
        unidad2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SeleccionUnidad.this, JuegoX0.class));
                fade();
            }
        });

        unidad3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SeleccionUnidad.this, Div.class));
                fade();
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SeleccionUnidad.this, MainActivity.class));
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