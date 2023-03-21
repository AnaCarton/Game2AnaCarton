package com.example.game2anacarton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

//Se define la clase Juego, que extiende de View
public class Juego extends View {
    //Se definen las variables necesarias
    //Las variables para el ancho y el alto de la pantalla
    public int ancho,alto;

    //Las variables que determinarán la posición en los ejes X e Y de todos los elementos (frutas, golosinas, cesta...)
    public int posX,posY,radio, posStrawberryX, posStrawberryY, posDonutX, posDonutY, posBananaX, posBananaY,posCandyX, posCandyY, posCherryX, posCherryY;
    private GestureDetector gestos;

    //Se definen los RectF
    private RectF rectCesta;
    private RectF rectStrawberry;
    private RectF rectDonut;
    private RectF rectBanana;
    private RectF rectCandy;
    private RectF rectCherry;
    static Context context = Juego.context ;
    private String dificultad = "";
    private RectF rectMoneda;
    private RectF rectCosaMalaa;
    public Integer puntuacion=0;
    private Random random = new Random();
    MediaPlayer musicaFondo, sonidoColision, sonidoSuccess, musicaGameOver;
    Bitmap imageCherries, imageBanana, imageDonut, imageCandy, imageFondo, imageStrawberry, imageBasket;

    public Juego(Context context) {
        super(context);
        this.context = context;
    }
    public Juego(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        musicaFondo = MediaPlayer.create(context,R.raw.aloha);
        musicaFondo.start();
        //mantiene el loop del soundtrack
        musicaFondo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                musicaFondo.start();
            }
        });
    }


    //Sección que capta los eventos del usuario
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // you may need the x/y location
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                posX=(int)event.getX();
                radio=50;
                this.invalidate();
        }
        return true;
    }
    public Juego(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Tras dibujarse el lienzo, se relacionan las variables definidas con recursos
        //Las imágenes de los elementos se asocian con imágenes de tipo png a partir de BitmapFactory
        imageStrawberry = BitmapFactory.decodeResource(getResources(), R.drawable.m_strawberry);
        imageDonut = BitmapFactory.decodeResource(getResources(), R.drawable.m_donut2);
        imageBanana = BitmapFactory.decodeResource(getResources(), R.drawable.banana);
        imageCandy = BitmapFactory.decodeResource(getResources(), R.drawable.candycane);
        imageCherries = BitmapFactory.decodeResource(getResources(), R.drawable.m_cherries);
        imageFondo = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        imageBasket = BitmapFactory.decodeResource(getResources(), R.drawable.m_m_basket);
        //Se asociado los MediaPlayer con los archivos mp3 correspondientes (de la carpeta raw)
        sonidoColision = MediaPlayer.create(context,R.raw.nope);
        sonidoSuccess = MediaPlayer.create(context,R.raw.yoshi);

        //Definimos los objetos a pintar (fondo y puntos)
        Paint fondo = new Paint();
        Paint puntos = new Paint();

        //Se definen los colores de los objetos a pintar
        fondo.setColor(Color.BLACK);
        fondo.setStyle(Paint.Style.FILL_AND_STROKE);

        //Se establecen las características para los puntos que se van a dibujar en la pantalla
        puntos.setTextAlign(Paint.Align.RIGHT);
        puntos.setTextSize(100);
        puntos.setColor(Color.WHITE);

         //Se pinta un rectángulo con un ancho y alto de 1000 o de menos si la pantalla es menor.
        canvas.drawRect(new Rect(0,0,(ancho),(alto)),fondo);

        Rect rectBackground = new Rect(0,0,(ancho),(alto) );
        canvas.drawBitmap(imageFondo, null, rectBackground, null);

        // Pintamos la cesta.
        // La Y la implementa el timer y la X la pongo aleatorieamente en cuanto llega al final
        rectCesta= new RectF((posX-radio),(radio),(posX+radio),(200));
        //canvas.drawOval(rectCesta,cesta);
        canvas.drawBitmap(imageBasket, null, rectCesta, null);

        //DEFINIMOS LOS RECT
        //Con el método RectF(REAL, REAL, REAL, REAL) se crea un objeto RectF usando 4 integers para inicializar la X, la Y, el ancho y el alto
        rectStrawberry = new RectF((posStrawberryX -radio),(posStrawberryY -radio),(posStrawberryX +radio),(posStrawberryY +radio));

        rectBanana = new RectF((posBananaX-radio),(posBananaY-radio),(posBananaX+radio),(posBananaY+radio));

        rectCherry = new RectF((posCherryX-radio),(posCherryY-radio),(posCherryX+radio),(posCherryY+radio));

        rectCandy = new RectF((posCandyX-radio),(posCandyY-radio),(posCandyX+radio),(posCandyY+radio));

        rectDonut = new RectF((posDonutX -radio),(posDonutY -radio),(posDonutX +radio),(posDonutY +radio));


        //Se pinta el elemento FRESA
        if (posStrawberryY <0) {
            //Si su posición en el Y pasa más alla del 0 en términos negativos (es decir, si se sale del límite superior)
            //lo posicionamos de nuevo en la parte baja de la pantalla (eje Y)
            posStrawberryY =alto;
            //y hacemos que salga en un punto aleatorio del eje X dentro del ancho de la pantalla
            posStrawberryX = random.nextInt(ancho);
        }
        //Se dibuja la imagen de la fresa en el canvas
        canvas.drawBitmap(imageStrawberry, null, rectStrawberry, null);

        //Se pinta el elemento BANANA
        if (posBananaY<0) {
            //Si su posición en el Y pasa más alla del 0 en términos negativos (es decir, si se sale del límite superior)
            //lo posicionamos de nuevo en la parte baja de la pantalla (eje Y)
            posBananaY=alto;
            //y hacemos que salga en un punto aleatorio del eje X dentro del ancho de la pantalla
            posBananaX= random.nextInt(ancho);
        }
        //Se dibuja la imagen del plátano en el canvas
        canvas.drawBitmap(imageBanana, null, rectBanana, null);

        //Se pinta el elemento CHERRY
        if (posCherryY<0) {
            //Si su posición en el Y pasa más alla del 0 en términos negativos (es decir, si se sale del límite superior)
            //lo posicionamos de nuevo en la parte baja de la pantalla (eje Y)
            posCherryY=alto;
            //y hacemos que salga en un punto aleatorio del eje X dentro del ancho de la pantalla
            posCherryX= random.nextInt(ancho);
        }
        //Se dibuja la imagen de las cerezas en el canvas
        canvas.drawBitmap(imageCherries, null, rectCherry, null);

        //Se pinta el elemento CANDY
        if (posCandyY<0) {
            //Si su posición en el Y pasa más alla del 0 en términos negativos (es decir, si se sale del límite superior)
            //lo posicionamos de nuevo en la parte baja de la pantalla (eje Y)
            posCandyY=alto;
            //y hacemos que salga en un punto aleatorio del eje X dentro del ancho de la pantalla
            posCandyX= random.nextInt(ancho);
        }
        //Se dibuja la imagen del caramelo en el canvas
        canvas.drawBitmap(imageCandy, null, rectCandy, null);

        //Se pinta el elemento DONUT
        if (posDonutY <0) {
            //Si su posición en el Y pasa más alla del 0 en términos negativos (es decir, si se sale del límite superior)
            //lo posicionamos de nuevo en la parte baja de la pantalla (eje Y)
            posDonutY =alto;
            //y hacemos que salga en un punto aleatorio del eje X dentro del ancho de la pantalla
            posDonutX = random.nextInt(ancho);
        }
        //Se dibuja la imagen del dónut en el canvas
        canvas.drawBitmap(imageDonut, null, rectDonut, null);

        // Cálculo de las intersecciones entre el elemento CESTA y el resto de ELEMENTOS (Frutas y Golosinas)
        if (RectF.intersects(rectCesta, rectStrawberry)) {
            //Si chocan la cesta y la fresa:
            //Se suman 3 puntos a la puntuación total
            puntuacion += 3;
            //Se sitúa la fresa de nuevo en lo más bajo de la pantalla (eje Y)
            posStrawberryY =alto;
            //La fresa aparece en un punto aleatorio del eje X dentro de los límites del ancho de la pantalla
            posStrawberryX = random.nextInt(ancho);
            //Se reproduce la música de éxito
            sonidoSuccess.start();
        }

        if(RectF.intersects(rectCesta, rectBanana)){
            //Si chocan la cesta y el plátano:
            //Se suman 5 puntos a la puntuación total
            puntuacion += 5;
            //Se sitúa el plátano de nuevo en lo más bajo de la pantalla (eje Y)
            posBananaY=alto;
            //El plátano aparece en un punto aleatorio del eje X dentro de los límites del ancho de la pantalla
            posBananaX = random.nextInt(ancho);
            //Se reproduce la música de éxito
            sonidoSuccess.start();
        }
        if(RectF.intersects(rectCesta, rectCherry)){
            //Si chocan la cesta y las cerezas:
            //Se suman 4 puntos a la puntuación total
            puntuacion += 4;
            //Se sitúan las cerezas de nuevo en lo más bajo de la pantalla (eje Y)
            posCherryY=alto;
            //Las cerezas aparecen en un punto aleatorio del eje X dentro de los límites del ancho de la pantalla
            posCherryX = random.nextInt(ancho);
            //Se reproduce la música de éxito
            sonidoSuccess.start();
        }
        if(RectF.intersects(rectCesta, rectDonut)){
            //Si chocan la cesta y el dónut:
            //Se restan 5 puntos a la puntuación total
            puntuacion -= 5;
            //Se sitúa el dónut de nuevo en lo más bajo de la pantalla (eje Y)
            posDonutY =alto;
            //El dónut aparece en un punto aleatorio del eje X dentro de los límites del ancho de la pantalla
            posDonutX = random.nextInt(ancho);
            //Se reproduce la música de fracaso
            sonidoColision.start();
        }
        if(RectF.intersects(rectCesta, rectCandy)){
            //Si chocan la cesta y el bastón de caramelo:
            //Se restan 3 puntos a la puntuación total
            puntuacion -= 3;
            //Se sitúa el bastón de caramelo de nuevo en lo más bajo de la pantalla (eje Y)
            posCandyY=alto;
            //El bastón de caramelo aparece en un punto aleatorio del eje X dentro de los límites del ancho de la pantalla
            posCandyX = random.nextInt(ancho);
            //Se reproduce la música de fracaso
            sonidoColision.start();
        }

        //Se dibuja en el canvas/lienzo la puntuación arriba a la izquierda con el valor correspondiente
        canvas.drawText(puntuacion.toString(), 150,150,puntos);
    }

}
