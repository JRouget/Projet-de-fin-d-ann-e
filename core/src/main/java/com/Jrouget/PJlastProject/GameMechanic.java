package com.Jrouget.PJlastProject;

import java.util.Random;

public class GameMechanic {
    private Integer resultat;
    private Random random;
    private int soldeJoueur = 100;
    private final int coutTirage = 10;

    public boolean peutJouer(){
        return soldeJoueur >= coutTirage;
    }

    public int[] tirage(){

        soldeJoueur -= coutTirage;
        System.out.println("Player's balance : " + soldeJoueur);

        random = new Random();

        int case1 = random.nextInt(3) + 1;
        int case2 = random.nextInt(3) + 1;
        int case3 = random.nextInt(3) + 1;

        return new int[]{case1, case2, case3};
    }
}
