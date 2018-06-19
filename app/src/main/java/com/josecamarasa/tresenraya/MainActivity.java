package com.josecamarasa.tresenraya;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.R.*;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Iniciamos el array casillas que identifica cada casilla y la almacena en el array

        CASILLAS= new int[9];

        CASILLAS[0]=R.id.a1;
        CASILLAS[1]=R.id.a2;
        CASILLAS[2]=R.id.a3;
        CASILLAS[3]=R.id.b1;
        CASILLAS[4]=R.id.b2;
        CASILLAS[5]=R.id.b3;
        CASILLAS[6]=R.id.c1;
        CASILLAS[7]=R.id.c2;
        CASILLAS[8]=R.id.c3;
    }

    public void aJugar(View vista){

        ImageView imagen;
            for(int cadaCasilla:CASILLAS){
                imagen=(ImageView)findViewById((cadaCasilla));

                imagen.setImageResource(R.drawable.casilla);
            }

            jugadores=1;

            if(vista.getId()==R.id.dosjug){

                jugadores=2;
            }

            RadioGroup configDificultad=(RadioGroup)findViewById(R.id.configD);

            int id=configDificultad.getCheckedRadioButtonId();

            int dificultad=0;
            if(id==R.id.normal){
                dificultad=1;
            }else if(id==R.id.imposible){
                dificultad=2;
            }

            partida=new Partida(dificultad);

            ((Button)findViewById(R.id.unjug)).setEnabled(false);

            ((RadioGroup)findViewById(R.id.configD)).setAlpha(0);

            ((Button)findViewById(R.id.dosjug)).setEnabled(false);

    }



    public void toque(View mivista){

        if(partida==null){
            return;
        }

        int casilla=0;

        for(int i=0;i<9;i++){
            if(CASILLAS[i]==mivista.getId()){
                casilla=i;
                break;
            }
        }



       if(partida.comprueba_casilla(casilla)==false){  //si la casilla ya esta elegida nos salimos
           return;
       }

       marca(casilla);   //El usuario marca su casilla


        int resultado=partida.turno();   //0 si no ha ganado nadie, 1 si gana jugador 1, 2 si gana jugador 2, 3 si empate

        if(resultado>0){   // Si es diferente a 0, tenemos algun resultado final
            termina(resultado);

            return;
        }

        if(jugadores==1) {
            casilla = partida.ia();   //la maquina elige su casilla

            while (partida.comprueba_casilla(casilla) != true) {
                casilla = partida.ia();
            }


            marca(casilla);         //dibuja su casilla
            resultado = partida.turno();

            if (resultado > 0) {
                termina(resultado);
            }
        }
    }

    private void termina(int resultado){
        String mensaje;
        if(resultado==1) mensaje="Ganan los circulos";
        else if(resultado==2) mensaje="Ganan las aspas";
        else mensaje="Empate";

        Toast toast=Toast.makeText(this, mensaje, Toast.LENGTH_LONG); //Ventanas emergentes con Toast

        toast.setGravity(Gravity.CENTER,0,0);

        toast.show();

        partida=null;

        ((Button)findViewById(R.id.unjug)).setEnabled(true);
        ((RadioGroup)findViewById(R.id.configD)).setAlpha(1);
        ((Button)findViewById(R.id.dosjug)).setEnabled(true);
    }

    private void marca(int casilla){

        ImageView imagen;
        imagen=(ImageView)findViewById(CASILLAS[casilla]);

        if(partida.jugador==1){
            imagen.setImageResource(R.drawable.circulo);  //Elegimos imagen para jugador 1
        }else{
            imagen.setImageResource(R.drawable.aspa);     //Elegimos imagen para jugador 2
        }
    }

    private int jugadores;

    private int[] CASILLAS;

    private Partida partida;
}
