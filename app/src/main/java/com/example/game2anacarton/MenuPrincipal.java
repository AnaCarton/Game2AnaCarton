package com.example.game2anacarton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//Actividad del Menú Principal del juego
public class MenuPrincipal extends AppCompatActivity {

    //Se definen las variables necesarias
    //Un editText para que el usuario introduzca su nombre
    EditText editTextNombre;
    //Un spinner para que el usuario seleccione el nivel de dificultad
    Spinner spinnerDificultad;
    //Un botón para empezar el juego
    Button buttonPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Se dibuja el layout
        setContentView(R.layout.menu_principal);

        //Se relacionan las variables con los elementos del layout
        editTextNombre = findViewById(R.id.editTextNombre);
        spinnerDificultad = findViewById(R.id.spinner);
        buttonPlay = findViewById(R.id.button);

        //Definimos el array con las opciones que va a mostrar el spinner
        String[] options = {"Fácil", "Medio", "Difícil"};

        //Se declara el adaptador para el spinner y se le setea
        ArrayAdapter<String> optionsAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, options);
        spinnerDificultad.setAdapter(optionsAdapter);

        //Se establece un listener para el botón de jugar
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Se crea un Intent para pasar a la Activity siguiente (MainActivity)
                Intent intent = new Intent(MenuPrincipal.this, MainActivity.class);
                //Se añade un extra al intent con la dificultad seleccionada por el usuario
                intent.putExtra("dificultad", String.valueOf(spinnerDificultad.getSelectedItemPosition()));

                //Se comprueba si el usuario ha introducido algún nombre en el editText

                if(editTextNombre.getText().toString().isEmpty()){
                    //Si no, se le pone como nombre "PLAYER 1"
                    intent.putExtra("nombre", "PLAYER 1" );
                }
                else{
                    //Si sí ha metido un nombre, se pasa su valor en el intent dentro del extra "nombre"
                    intent.putExtra("nombre", editTextNombre.getText().toString());
                }
                //Se comienza la siguiente actividad (MainActivity)
                startActivity(intent);
            }
        });

    }
}
