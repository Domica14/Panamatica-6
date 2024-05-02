package com.example.matematica.unidad1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.matematica.R;

public class TeoriaMultiDivAdv extends AppCompatActivity {

    private Button btnRegresar, btnSiguiente;

    MediaPlayer Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teoria_multi_div_adv);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);      //Bloquea la orientacion de pantalla

        btnRegresar = findViewById(R.id.btnRegresarMultiDiv2);
        btnSiguiente = findViewById(R.id.btnSiguienteMultiDiv2);
        Btn = MediaPlayer.create(this, R.raw.popsound);

        //Al presionar el boton se regresa a la pantalla de seleccion de lecciones
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Btn.start();
                startActivity(new Intent(TeoriaMultiDivAdv.this, LeccionesAdv.class));
                fade();
                finish();
            }
        });

        //Al presionar el boton se prosigue a los problemas
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Btn.start();
                startActivity(new Intent(TeoriaMultiDivAdv.this, Mult.class));
                fade();
                finish();
            }
        });
    }

    /*Se sobrescribe el metodo de onBackPressed para que realize el retroceso a la activity anterior al presionar
      el boton de atras del celular
     */
    @Override
    public void onBackPressed(){
        startActivity(new Intent(TeoriaMultiDivAdv.this, LeccionesAdv.class));
        fade();
        finish();
    }

    //Metodo que se encarga de la transicion entre activities
    public void fade(){
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);         //Metodo para cambiar la transicion
        finish();       //Se finaliza la activity actual
    }
}
