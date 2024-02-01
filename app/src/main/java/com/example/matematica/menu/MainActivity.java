package com.example.matematica.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.matematica.R;

public class MainActivity extends AppCompatActivity {

    //Declaracion de variables para manejar los elementos de la vista
    private Button btnEmpezar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Asociacion de la variable declarada con el elemento de la vista
        btnEmpezar = findViewById(R.id.btnEmpezar);

        //Listener para determinar la accion a realizar al presionar el boton
        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fade();           //Llamado al metodo de la transicion
            }
        });
    }

    //Metodo que se encarga de la transicion entre activities
    public void fade(){
        //Metodo para realizar el cambio de activity
        startActivity(new Intent(MainActivity.this, SeleccionUnidad.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);         //Metodo para cambiar la transicion
        finish();       //Se finaliza la activity actual
    }
}