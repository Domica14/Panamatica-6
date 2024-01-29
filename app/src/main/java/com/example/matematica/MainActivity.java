package com.example.matematica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //Declaracion de variables para manejar los elementos de la vista
    private Button btnEmpezar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Asociacion de la variable declarada con el elemento de la vista
        btnEmpezar = findViewById(R.id.btnEmpezar);

        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fade(btnEmpezar);
            }
        });
    }

    //Metodo que se encarga de la transicion entre activities
    public void fade(View button){
        //Metodo para realizar el cambio de activity
        startActivity(new Intent(MainActivity.this, SeleccionDificultad.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);         //Metodo para cambiar la transicion
    }
}