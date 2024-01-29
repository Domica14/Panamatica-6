package com.example.matematica;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SeleccionDificultad extends AppCompatActivity {

    //Declaracion de variables para el control de los elementos del activity
    private ImageView opcionFacil, opcionDificil;
    private Button btnMenuPrincipal;
    private String modo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_dificultad);

        //Asociacion de variables con los elementos del acttivity
        opcionFacil = findViewById(R.id.opcionFacil);
        opcionDificil = findViewById(R.id.opcionDificil);
        btnMenuPrincipal = findViewById(R.id.btnMenu);

        //Listener para realizar accion al presionar el boton de menu
        btnMenuPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Metodo para realizar el cambio de activity
                startActivity(new Intent(SeleccionDificultad.this, MainActivity.class));
                fade();         //Llamada al metodo de transicion
            }
        });

        //Listeners para realizar accion al presionar una de la imagenes de opcion
        opcionFacil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Asignacion de dificultad a variable de modo
                modo = "Facil";
                //Metodo para realizar el cambio de activity
                startActivity(new Intent(SeleccionDificultad.this, Lecciones.class));
                fade();
            }
        });
        opcionDificil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Asignacion de dificultad a variable de modo
                modo = "Dificil";
                //Metodo para realizar el cambio de activity
                startActivity(new Intent(SeleccionDificultad.this, Lecciones.class));
                fade();
            }
        });
    }

    //Metodo que se encarga de la transicion entre activities
    public void fade(){
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);         //Metodo para cambiar la transicion
        finish();       //Se finaliza la activity actual
    }
}