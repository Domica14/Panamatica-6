package com.example.matematica.unidad1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.matematica.R;
import com.example.matematica.menu.MainActivity;
import com.example.matematica.menu.SeleccionUnidad;

public class LeccionesAdv extends AppCompatActivity {

    private ImageView leccionAdv1, leccionAdv2;
    private Button Atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecciones_adv);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);      //Bloquea la orientacion de pantalla

        leccionAdv1 = findViewById(R.id.imageView);
        leccionAdv2 = findViewById(R.id.imageView2);
        Atras = findViewById(R.id.Atras);

        //Funcionamiento del boton de Atras, volviendo asi a la seleccion de dificultad
        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LeccionesAdv.this, SeleccionDificultad.class));
                fade();
            }
        });

        /*Una vez se toque la lección 1, la acción mandará al material que contiene dicha lección
         esto en modo facil*/
        leccionAdv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LeccionesAdv.this, TeoriaAdiSusAdv.class));
                fade();
            }
        });


        /*Una vez se toque la lección 2, la acción mandará al material que contiene dicha lección
         esto en modo facil*/
        leccionAdv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LeccionesAdv.this, TeoriaMultiDivAdv.class));
                fade();
            }
        });


    }

    /*Se sobrescribe el metodo de onBackPressed para que realize el retroceso a la activity anterior al presionar
      el boton de atras del celular
     */
    public void onBackPressed(){
        startActivity(new Intent(LeccionesAdv.this, SeleccionDificultad.class));
        fade();
        finish();
    }

    //Metodo que se encarga de la transicion entre activities
    public void fade(){
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);         //Metodo para cambiar la transicion
        finish();       //Se finaliza la activity actual
    }

}