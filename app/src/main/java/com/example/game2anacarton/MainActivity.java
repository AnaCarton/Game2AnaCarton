package com.example.game2anacarton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewTreeObserver;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    //Se definen las variables que se van a utilizar:
    public Juego juego;
    private Handler handler = new Handler();
    int velocidadDonut = 0;
    int velocidadStrawberry = 0;
    int velocidadBanana = 0;
    int velocidadCherry = 0;
    int velocidadCandy = 0;
    String estado = "";
    String puntos = "";
    int puntosJuego = 0;

    //Método onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se obtiene el Intent de la Actividad Madre (MenuPrincipal)
        Intent intent = getIntent();
        //Con el intent, se obtienen los datos enviados desde el menú principal con getExtras()
        Bundle extra = intent.getExtras();
        //Se obtienen los valores de dificultad y el nombre del jugador
        String valorSpinnerDificultad = extra.getString("dificultad");
        String nombreJugador = extra.getString("nombre");


        //según el valor de dificultad, determinamos la velocidad de las frutas y las golosinas

        //VALORES PARA EL NIVEL FÁCIL
        if (valorSpinnerDificultad.equals("0")){
            velocidadBanana = 11;
            velocidadStrawberry = 10;
            velocidadCandy = 10;
            velocidadDonut = 12;
            velocidadCherry = 13;
        }
        //VALORES PARA EL NIVEL MEDIO
        if (valorSpinnerDificultad.equals("1")){
            velocidadDonut = 16;
            velocidadBanana = 20;
            velocidadStrawberry = 17;
            velocidadCandy = 15;
            velocidadCherry = 16;
        }
        //VALORES PARA EL NIVEL DIFÍCIL
        if (valorSpinnerDificultad.equals("2")){
            velocidadCandy = 20;
            velocidadBanana = 25;
            velocidadDonut = 20;
            velocidadStrawberry = 23;
            velocidadCherry = 21;
        }
        //Se asocia la variable de tipo Juego con el elemento correspondiente del layout
        juego = (Juego) findViewById(R.id.Pantalla);
        //Se define un ViewTreeObserver para el juego
        ViewTreeObserver obs = juego.getViewTreeObserver();
        obs.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                //El ancho y el alto solo se pueden calcular una vez pintado el layout
                // de ahí que se calculen en el listener
                juego.ancho = juego.getWidth();
                juego.alto = juego.getHeight();
                juego.posX=juego.ancho/2;
                juego.posY=juego.alto-50;
                juego.radio=50;
                //También se definen las posiciones de las frutas y golosinas

            }
        });


        //Se define un objeto de tipo Timer que va a servir para cronometrizar una tarea
        //que se va a ir repitiendo cada cierto intervalo de tiempo (en este caso, se ejecuta cada 20 milisegundos)
        Timer timer = new Timer();

            timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    //En el método run determinamos lo que queremos que ocurra con las frutas/golosinas cada vez que se ejecute
                    public void run() {
                        //Cada 20 milisegundos movemos las golosinas/chuches la distancia determinada por la variable "velocidad..."
                        // ese valor se resta a la posición del elemento porque queremos que ascienda
                        juego.posStrawberryY -= velocidadStrawberry;
                        juego.posBananaY-=velocidadBanana;
                        juego.posDonutY -= velocidadDonut;
                        juego.posCherryY -= velocidadCherry;
                        juego.posCandyY -= velocidadCandy;
                        //Se refresca la pantalla y se  llama al método draw() de la vista Juego
                        juego.invalidate();

                    }
                });
                //Se obtiene el valor de los puntos de Juego y se va actualizando tras cada "refresh" de la pantalla

                puntos = juego.puntuacion.toString();
                puntosJuego = Integer.parseInt(puntos);
                //Se define un nuevo intent para enviar datos de MainActivity a GameOver.class
                Intent intent2 = new Intent(MainActivity.this, GameOver.class);

                //Definimos los distintos escenarios para el GameOver

                //Si los puntos obtenidos superan los 100, se entiende como que el jugador ha ganado la partida
                if(puntosJuego >= 50) {
                    //El estado pasa a "victoria", ya que luego se pasará este valor a la siguiente Actividad
                    estado = "victoria";
                    System.out.println("se han pasao los puntos por arriba o por abajo" + juego.puntuacion.toString());
                    //Se añaden extras al intent para enviar la información relevante a GameOver.class: puntos, nombre del jugador y si ha ganado o perdido
                    intent2.putExtra("puntuacion", String.valueOf(puntosJuego));
                    intent2.putExtra("nombre", nombreJugador);
                    intent2.putExtra("estado", estado);
                    //Se cancela el timer para que deje de refrescar la pantalla cada 20 milisegundos, ya que el juego ha terminado
                    timer.cancel();
                    //Se para la música de fondo
                    juego.musicaFondo.stop();
                    //Se pasa a la actividad GameOver, pasándole los datos del intent2
                    startActivity(intent2);
                    //MainActivity.this.finish();
                }
                if(puntosJuego < 0 ) {
                    //Si los puntos son negativos, se pasa el estado a "derrota"
                    estado = "derrota";
                    //Se añaden extras al intent para enviar la información relevante a GameOver.class: puntos, nombre del jugador y si ha ganado o perdido
                    intent2.putExtra("puntuacion", String.valueOf(puntosJuego));
                    intent2.putExtra("nombre", nombreJugador);
                    intent2.putExtra("estado", estado);
                    //Se cancela el timer para que deje de refrescar la pantalla cada 20 milisegundos, ya que el juego ha terminado
                    timer.cancel();
                    //Se para la música de fondo
                    juego.musicaFondo.stop();
                    //Se pasa a la actividad GameOver, pasándole los datos del intent2
                    startActivity(intent2);
                    //MainActivity.this.finish();

                }


            }
        }, 0, 20);


    }


}