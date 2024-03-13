package com.example.matematica.juegos;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.matematica.R;
import com.example.matematica.menu.SeleccionUnidad;
import com.example.matematica.unidad1.*;

import java.util.HashMap;
import java.util.Random;

public class JuegoAdivinanzas extends AppCompatActivity {

    //Declaracion de variables para manejar los elementos de la vista
    private Button btnContinuar, btnAdivinar;
    private TextView txtAdivinanza;      //Mostrara la adivinanza
    private TextView txtResultado;      //Mostrara si se equivoco o no
    private EditText respuesta;         //Mostrara si la persona se equivoco o acerto

    //Se define un contador para saber cuantas veces se ha equivocado el usuario
    private int fallos = 0;

    //Guarda el valor de la proxima activity
    private int activity;

    //Se declara un HashMap para manejar las adivinanzas

    //--------------------------------------ADIVINANZAS------------------------------------------------------------------------------
    HashMap<String, String> adivinanzas = new HashMap<String, String>(){{
        put("Pez", "¿Que es algo y nada a la vez?");
        put("Agujero","¿Qué cosa es que cuanto más le quitas más grande es?");
        put("Llaves", "No muerde ni ladra, pero tiene dientes y la casa guarda. ¿Que es?");
        put("Rio", "Es tan largo como un camino, y gruñe como un cochino");
        put("Huevo", "Es blanco como la sal. Fácil de abrir, pero no lo puedes cerrar");
        put("Dedos", "Uno larguito, dos más bajitos, otro chico y flaco, y otro gordonazo");
        put("Reloj", "¿Cuál es aquel pobrecito, siempre andando, siempre andando, y no sale de su sitio?");
        put("Sol", "Grande, muy grande, mayor que la Tierra. Arde y no se quema, quema y no es candela");
        put("Estrellas", "Muchas lamparitas muy bien colgaditas, siempre encandiladas, y nadie las atiza");
        put("Cama", "¿Quién será la desvelada, lo puedes tú discurrir, de día y noche acostada, sin poder nunca dormir?");
        // 10 Adivinanzas
        put("Pollito", "Madre me labró una casa sin puertas y sin ventanas, y cuando quiero salir rompo antes la muralla");

    }};

    //------------------------------------------------------------------------------------------------------------------------------

    //Se define un array que contendra todas las llaves
    private String[] cantidadAdivinanzas = adivinanzas.keySet().toArray(new String[0]);

    //Se crea una variable que tomara la key de la adivinanza a mostrar
    String adivinanzaEscogida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_adivinanzas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);      //Bloquea la orientacion de pantalla

        //Se obtiene el valor de la proxima activity
        activity = getIntent().getExtras().getInt("proximaActivity");

        //Asociacion de variables con los elementos de la vista

        //Textos
        txtAdivinanza = findViewById(R.id.txtAdivinanza);
        txtResultado = findViewById(R.id.txtResultadoAdi);
        respuesta = findViewById(R.id.etxtRespuesta);

        //Botones
        btnAdivinar = findViewById(R.id.btnAdivinar);
        btnContinuar = findViewById(R.id.btnContinuarAdi);

        //Se prepara el juego
        configuracionJuego();

        //Listener para cuando se presione el boton de adivinar (se evalua la respuesta)
        btnAdivinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                juego();
            }
        });

        //Listener para cuando se presiona el boton de salir
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proximaActivity();
            }
        });
    }

    //Maneja la preparacion del juego
    public void configuracionJuego(){
        //Se crea una instancia de la clase Random
        Random random = new Random();

        //Se genera el numero aleatorio
        int seleccionAdivinanza = random.nextInt(cantidadAdivinanzas.length);

        adivinanzaEscogida = cantidadAdivinanzas[seleccionAdivinanza];      //Se guarda la adivinanza

        //Se muestra la adivinanza
        txtAdivinanza.setText(adivinanzas.get(adivinanzaEscogida));
    }

    //Maneja la logica del juego (mostrar adivinanzas, evaluar)
    public void juego(){
        //Se declara una variable para guardar la respuesta del usuario
        String respuestaUsuario = respuesta.getText().toString();

        //En caso de equivocarse tendra tres intentos
        if (!respuestaUsuario.equalsIgnoreCase(adivinanzaEscogida)) {
            txtResultado.setText("Respuesta equivocada :(");
            fallos++;
        } else {        //Si acierta
            txtResultado.setText("CORRECTO! La respuesta es: "+adivinanzaEscogida);
            respuesta.setEnabled(false);        //Se desactiva la opcion de escribir
            btnAdivinar.setEnabled(false);      //Se desactiva el boton
        }

        //Al alcanzar los tres intentos pierde
        if (fallos == 3){
            txtResultado.setText("Incorrecto, la respuesta era: "+adivinanzaEscogida);
            respuesta.setEnabled(false);        //Se desactiva la opcion de escribir
            btnAdivinar.setEnabled(false);      //Se desactiva el boton
        }
    }

    //Maneja el cambio de activities desde los juegos
    public void proximaActivity(){
        switch (activity){
            case 2:
                startActivity(new Intent(JuegoAdivinanzas.this, RestaEZ.class));
                fade();
                finish();
                break;
            case 3:
                startActivity(new Intent(JuegoAdivinanzas.this, DivEZ.class));
                fade();
                finish();
                break;
            case 4:
                startActivity(new Intent(JuegoAdivinanzas.this, Resta.class));
                fade();
                finish();
                break;
            case 5:
                startActivity(new Intent(JuegoAdivinanzas.this, Div.class));
                fade();
                finish();
                break;
            case 6:
                startActivity(new Intent(JuegoAdivinanzas.this, Lecciones.class));
                fade();
                finish();
                break;
            case 7:
                startActivity(new Intent(JuegoAdivinanzas.this, LeccionesAdv.class));
                fade();
                finish();
                break;
        }
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