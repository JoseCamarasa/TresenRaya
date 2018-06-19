package com.josecamarasa.tresenraya;

import java.util.Random;

public class Partida {

    public Partida(int dificultad){

        this.dificultad=dificultad;

        jugador=1;

        casillas=new int[9];
        for(int i=0;i<9;i++){              //Creamos bucle para poner a 0 para no sobreescribir la imagen al jugar
            casillas[i]=0;
        }
    }

    public boolean comprueba_casilla(int casilla){

        if(casillas[casilla]!=0){           //Comprobamos si la casilla ya se ha utilizado

            return false;
        }else{
            casillas[casilla]=jugador;
        }
        return true;
    }

    public int turno(){

        boolean empate=true;                //vemos si hay empate

        boolean ult_movimiento=true;

        for (int i=0;i<COMBINACIONES.length;i++){  //recorremos el array combinaciones de inicio a fin
            for(int pos:COMBINACIONES[i]){

                System.out.println("Valor en posicion " + pos + " " + casillas[pos]);


                if(casillas[pos]!=jugador)ult_movimiento=false; // si alguien ha hecho 3 en raya

                if(casillas[pos]==0){
                    empate=false;
                }

            }
            System.out.println("--------------------------------------------------");
            if(ult_movimiento)return jugador;    //Si alguien ha hecho 3 en raya
            ult_movimiento=true;
        }

        if(empate){
            return 3;
        }

        jugador++;      //Sumamos 1 para cambiar a jugador 2

        if(jugador>2){    //Para que no siga sumando, volvemos a jugador 1
            jugador=1;
        }

        return 0;
    }

    public int dosEnRaya(int jugador_en_turno){
        int casilla=-1;                            //la iniciamos en una casilla que no existe
        int cuantas_lleva=0;                         //si en algun momento es 2, seria 2 en raya

        for (int i=0;i<COMBINACIONES.length;i++) {  //recorremos el array combinaciones de inicio a fin
            for (int pos : COMBINACIONES[i]) {
               if(casillas[pos]==jugador_en_turno) cuantas_lleva++;         //Evaluamos si la casilla esta marcada o no por el jugador en turno
                if(casillas[pos]==0) casilla=pos;                     //si la casilla que queda es 0 se marca
            }
            if(cuantas_lleva==2 && casilla!=-1) return casilla;    //Conseguimos saber la casilla clave

            casilla=-1;
            cuantas_lleva=0;
        }
        return -1;              //se devuelve la casilla -1 si no hay casilla clave
    }

    public int ia(){

        int casilla;
        casilla=dosEnRaya(2);

        if(casilla!=-1) return casilla;  // Si no hay poisbilidad de 3 en raya, devuelve -1, si la hay, devuelve la casilla clave

        if(dificultad>0){
            casilla=dosEnRaya(1); // La maquina mira si el jugador tiene posibilidad de 3 en raya
            if(casilla!=-1) return casilla;
        }

        if(dificultad==2) {

            if (casillas[4] == 0) return 4;         //la casilla central es la de mayor probabilidad de todas
            if (casillas[0] == 0) return 0;        //las esquinas tienen mayor posibilidad para ganar
            if (casillas[2] == 0) return 2;
            if (casillas[6] == 0) return 6;
            if (casillas[8] == 0) return 8;

        }

        Random casilla_azar=new Random();
        casilla=casilla_azar.nextInt(9);
        return casilla;
    }



    public final int dificultad;

    public int jugador;

    private int [] casillas;

    private final int[][] COMBINACIONES={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

}
