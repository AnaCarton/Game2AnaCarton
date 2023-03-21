package com.example.game2anacarton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//Esta es la clase que se va a ejecutar cuando se termine el juego
public class GameOver extends AppCompatActivity {
    //Se definen las variables necesarias
    private TextView tvPuntos, tvMensaje;
    //Un MediaPlayer para que suene la música de fondo cuando se ejecute el GameOver
    MediaPlayer musicaFin;
    //Unos botones para darle opciones diferentes al jugador
    Button menu, salir, restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Se pinta el layout
        setContentView(R.layout.game_over);

        //Se asocian las variables definidas con los elementos del layout
        tvPuntos = findViewById(R.id.textViewPuntos);
        tvMensaje = findViewById(R.id.tvMensaje);
        menu = findViewById(R.id.buttonMenu);
        salir = findViewById(R.id.buttonSalir);
        //restart = findViewById(R.id.buttonTryAgain);

        //Se crea un intent para obtener la información enviada desde MainActivity
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        //Se obtienen los valores de la puntuación, el nombre del jugador y del estado de la partida (el resultado) my se guardan en variables
        String puntos = extra.getString("puntuacion");
        String nombre = extra.getString("nombre");
        String estado = extra.getString("estado");


        //Se establece el texto del textView con los puntos obtenidos por el jugador
        tvPuntos.setText("Tus puntos: " + puntos);

        //Se comprueba si el estado de la partida es "victoria"
        if(estado.equals("victoria")){
            //Si el jugador ha ganado, se establece un texto para el textView con el mensaje congratulatorio
            tvMensaje.setText("¡Enhorabuena, " + nombre +"!");
            //Al ser una victoria, se crea un MediaPlayer asociándole la canción de victoria
            musicaFin = MediaPlayer.create(GameOver.this,R.raw.end);
            //Se inicia la música de victoria
            musicaFin.start();
            //Cuando termina la música, se vuelve a reproducir (en bucle)
            musicaFin.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    musicaFin.start();
                }
            });
        }
        //Si el estado de la partida es derrota (es decir, si el jugador ha perdido)
        if(estado.equals("derrota")){
            //Se establece un texto para el textView que informa de la derrota
            tvMensaje.setText("¡Oh, no, " + nombre +"!");
            //Se asocia el MediaPlayer con otro archivo de mp3 que tiene un sonido de derrota
            musicaFin = MediaPlayer.create(GameOver.this,R.raw.bad);
            //Se inicia la música
            musicaFin.start();
            //Una vez terminada la reproducción de la música de derrota, se hace release del MediaPlayer porque no queremos que se siga reproduciendo
            musicaFin.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    musicaFin.release();
                }
            });
        }

        //Listeners para los diferentes botones

        //BOTÓN PARA VOLVER AL MENÚ PRINCIPAL
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Cuando el usuario hace clic sobre el botón de menú, se para la música de fondo (si era
                musicaFin.stop();
                musicaFin.release();
                //Se crea un Intent y se ejecuta el menu principal segunda actividad
                Intent intent = new Intent(GameOver.this, MenuPrincipal.class);

                startActivity(intent);
            }
        });

        //BOTÓN DE SALIR
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Si la música está sonando, se para y se hace release del MediaPlayer
                if(musicaFin.isPlaying()){
                    musicaFin.stop();
                    musicaFin.release();
                }
                //SE CIERRA LA APP
                finishAffinity();
            }
        });

        }


}
