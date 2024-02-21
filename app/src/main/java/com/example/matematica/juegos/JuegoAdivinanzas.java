package com.example.matematica.juegos;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.matematica.R;
import com.example.matematica.menu.SeleccionUnidad;

import java.util.HashMap;

public class JuegoAdivinanzas extends AppCompatActivity {

    //Declaracion de variables para manejar los elementos de la vista
    private Button btnSalir, btnAdivinar;
    private TextView txtAdivinanza;      //Mostrara la adivinanza
    private EditText respuesta;         //Mostrara si la persona se equivoco o acerto

    //Se declara un HashMap para manejar las adivinanzas

    //--------------------------------------ADIVINANZAS----------------------------------------------
    HashMap<String, String> adivinanzas = new HashMap<String, String>(){{
        put("Pez", "¿Que es algo y nada a la vez?");
    }};

    //-----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_adivinanzas);

        //Asociacion de variables con los elementos de la vista

        //Textos
        txtAdivinanza = findViewById(R.id.txtAdivinanza);
        respuesta = findViewById(R.id.etxtRespuesta);

        //Botones
        btnAdivinar = findViewById(R.id.btnSalirAdi);
        btnSalir = findViewById(R.id.btnSalirAdi);

        //Listener para cuando se presione el boton de adivinar (se evalua la respuesta)
        btnAdivinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                juego();
            }
        });

        //Listener para cuando se presiona el boton de salir
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JuegoAdivinanzas.this, SeleccionUnidad.class));
                fade();
            }
        });
    }

    //Maneja la logica del juego (mostrar adivinanzas, evaluar)
    public void juego(){

    }

    /*Se sobrescribe el metodo de onBackPressed para que realize el retroceso a la activity anterior al presionar
      el boton de atras del celular
     */
    @Override
    public void onBackPressed () {
        startActivity(new Intent(JuegoAdivinanzas.this, SeleccionUnidad.class));
        fade();
        finish();
    }

    //Metodo que se encarga de la transicion entre activities
    public void fade () {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);         //Metodo para cambiar la transicion
        finish();       //Se finaliza la activity actual
    }
}