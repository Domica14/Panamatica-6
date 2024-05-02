package com.example.matematica.juegos;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.matematica.R;
import com.example.matematica.menu.SeleccionUnidad;
import com.example.matematica.unidad1.*;

import java.util.*;

public class JuegoAdivinanzas extends AppCompatActivity implements View.OnClickListener {

    //Declaracion de variables para manejar los elementos de la vista
    private Button btnContinuar;
    private TextView txtAdivinanza;      //Mostrara la adivinanza
    private TextView txtResultado;      //Mostrara si se equivoco o no
    private Button btnRespuesta1, btnRespuesta2, btnRespuesta3, btnRespuesta4;


    //Guarda el valor de la proxima activity
    private int activity;

    //Se declara un HashMap para manejar las adivinanzas

    //--------------------------------------ADIVINANZAS------------------------------------------------------------------------------
    private HashMap<String, String> adivinanzas = new HashMap<String, String>(){{
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

    //------------------------------------------RESPUESTAS------------------------------------------------------------------------------
    private String[][] respuestas = {{"Pez","Zorro","Alce","Ballena"}, {"Hambre","Agujero","Vacio","Agua"},
                                    {"Perro","Gato","Llaves","Alarma"}, {"Cerdo","Carretera","Autopista","Rio"},
                                    {"Huevo","Nieve","Sal","Arroz"}, {"Sardinas","Dedos","Manada","Perritos"},
                                    {"Mimo","Caminadora","Reloj","Bicicleta"}, {"Llama","Vela","Ascuas","Sol"},
                                    {"Estrellas","Lampara","Velas","Luces"}, {"Dormilona","Cama","Carpa","Sabana"},
                                    {"Perro","Ave","Pollito","Pez"}};

    //------------------------------------------------------------------------------------------------------------------------------------

    //Se define un array que contendra todas las llaves
    private final String[] cantidadAdivinanzas = adivinanzas.keySet().toArray(new String[0]);

    //Se crea una variable que tomara la key de la adivinanza a mostrar
    String adivinanzaEscogida;

    //Se crea un set para almacenar el orden de las respuestas
    Set<Integer> respuestasColoca = new HashSet<>();

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

        //Botones
        btnContinuar = findViewById(R.id.btnContinuarAdi);

        //Respuestas
        btnRespuesta1 = findViewById(R.id.btnRespuesta1);
        btnRespuesta2 = findViewById(R.id.btnRespuesta2);
        btnRespuesta3 = findViewById(R.id.btnRespuesta3);
        btnRespuesta4 = findViewById(R.id.btnRespuesta4);

        btnRespuesta1.setOnClickListener(this);
        btnRespuesta2.setOnClickListener(this);
        btnRespuesta3.setOnClickListener(this);
        btnRespuesta4.setOnClickListener(this);

        //Se prepara el juego
        configuracionJuego();

        //Listener para cuando se presiona el boton de salir
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proximaActivity();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnRespuesta1) {
            juego(String.valueOf(btnRespuesta1.getText()));
        } else if (id == R.id.btnRespuesta2) {
            juego(String.valueOf(btnRespuesta2.getText()));
        } else if (id == R.id.btnRespuesta3) {
            juego(String.valueOf(btnRespuesta3.getText()));
        } else if (id == R.id.btnRespuesta4) {
            juego(String.valueOf(btnRespuesta4.getText()));
        }
    }

    //Maneja la preparacion del juego
    public void configuracionJuego(){
        //Se crea una instancia de la clase Random
        Random random = new Random();

        //Se genera el numero aleatorio
        int seleccionAdivinanza = random.nextInt(cantidadAdivinanzas.length);

        adivinanzaEscogida = cantidadAdivinanzas[seleccionAdivinanza+1];      //Se guarda la adivinanza

        //Se muestra la adivinanza
        txtAdivinanza.setText(adivinanzas.get(adivinanzaEscogida));

        int ordenRespuestas;
        
        int setRespuestas = 0;
        
        //Se comprueba el set de respuestas
        for (int i=0; i<11; i++){
            for (int j=0; j<4; j++){
                if (respuestas[i][j].equals(adivinanzaEscogida)){
                    setRespuestas = i;
                    break;
                }
            }
        }
        
        do {
            ordenRespuestas = random.nextInt(5);
            respuestasColoca.add(ordenRespuestas);
        } while (respuestasColoca.size() <= 4); 

        Object[] respuestasColocaA = respuestasColoca.toArray();

        btnRespuesta1.setText(respuestas[setRespuestas][(int) respuestasColocaA[0]]);
        btnRespuesta2.setText(respuestas[setRespuestas][(int) respuestasColocaA[1]]);
        btnRespuesta3.setText(respuestas[setRespuestas][(int) respuestasColocaA[2]]);
        btnRespuesta4.setText(respuestas[setRespuestas][(int) respuestasColocaA[3]]);

    }

    //Maneja la logica del juego (mostrar adivinanzas, evaluar)
    public void juego(String respuesta){

        //Se desactivan los botones
        btnRespuesta1.setEnabled(false);
        btnRespuesta2.setEnabled(false);
        btnRespuesta3.setEnabled(false);
        btnRespuesta4.setEnabled(false);

        //Si se equivoca
        if (!respuesta.equalsIgnoreCase(adivinanzaEscogida)) {
            txtResultado.setText("Incorrecto, la respuesta era: "+adivinanzaEscogida);
        } else {        //Si acierta
            txtResultado.setText("CORRECTO! La respuesta es: "+adivinanzaEscogida);
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