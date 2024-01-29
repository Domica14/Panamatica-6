package com.example.matematica;

import android.content.Intent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Mensaje extends AppCompatActivity {
    private TextView txtMensaje;
    private static final int TIEMPO = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);

        txtMensaje = findViewById(R.id.txtMensaje);


    }

    //
    public void cambioMensaje(){
        txtMensaje.postDelayed(new Runnable() {
            @Override
            public void run() {
                fade();
            }
        }, TIEMPO);
    }

    //Metodo que se encarga de la transicion del mensaje en la misma activity
    public void fade(){
        //Metodo para realizar el cambio de activity
        txtMensaje.setText("");
        startActivity(new Intent(this, Mensaje.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);         //Metodo para cambiar la transicion
    }
}