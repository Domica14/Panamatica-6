package com.example.matematica.menu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.matematica.R;

public class Tienda extends AppCompatActivity {

    private ImageView btnRecompensa1;
    private Button btnRegresar;
    private TextView txtPuntos;
    private int puntos;         //Para cambiar cuando se tenga el sistema de puntos
    private int pistas;         //Cantidad de pistas con las que cuenta el jugador

    private MediaPlayer Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);      //Bloquea la orientacion de pantalla

        btnRecompensa1 = findViewById(R.id.recompensa1);
        btnRegresar = findViewById(R.id.btnRegresarTienda);
        txtPuntos = findViewById(R.id.txtPuntos);

        Btn = MediaPlayer.create(this, R.raw.popsound);

        //Obtencion de puntos y pistas
        //puntos = getIntent().getExtras().getInt("puntos");
        //pistas = getIntent().getExtras().getInt("pistas");

        txtPuntos.setText("Puntos\n"+puntos);

        btnRecompensa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Btn.start();
                if (puntos>=25){
                    puntos-=25;
                    pistas+=1;
                }
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Btn.start();
                Intent intent = new Intent(Tienda.this, SeleccionUnidad.class);
                intent.putExtra("puntos", puntos);
                intent.putExtra("pistas", pistas);
                startActivity(intent);
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
        Intent intent = new Intent(Tienda.this, SeleccionUnidad.class);
        intent.putExtra("puntos", puntos);
        intent.putExtra("pistas", pistas);
        startActivity(intent);
        fade();
        finish();
    }

    //Metodo que se encarga de la transicion entre activities
    public void fade(){
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);         //Metodo para cambiar la transicion
        finish();       //Se finaliza la activity actual
    }
}