package com.Jrouget.PJlastProject;

import java.util.Random;

public class GameMechanic {
    private Integer resultat;
    private int soldeJoueur = 100;
    private final int coutTirage = 1;

    private int mancheActuelle = 1;
    private int quotaManche = 50;
    private int coef;
    private int score;

    private int recompense = 100;
    private int tirage = 10;

    private int case1;
    private int case2;
    private int case3;

    private Random random = new Random();

    public boolean peutJouer(){
        return tirage > coutTirage;
    }

    public int[] tirage(){

        tirage -= coutTirage;
        System.out.println("Player's balance : " + soldeJoueur);

        case1 = random.nextInt(3) + 1;
        case2 = random.nextInt(3) + 1;
        case3 = random.nextInt(3) + 1;

        coef = multiplicateur();

        score += (case1 + case2 + case3) * coef;
        System.out.println("score =" + score);
        System.out.println("coef =" + coef);
        System.out.println("tirages =" + tirage);

        win();
        return new int[]{case1, case2, case3};
    }

    public void win() {
        if (score >= quotaManche){
            quotaManche += quotaManche;
            mancheActuelle += 1;
            soldeJoueur += recompense;
            recompense += 50;
            tirage += tirage + 5;
            score = 0;
        }
    }

    public int getsoldeJoueur() {
        return soldeJoueur;
    }

    public int getScore() {
        return score;
    }

    public int getQuota() {
        return quotaManche;
    }

    public int getManche() {
        return mancheActuelle;
    }

    public int getTirage() {
        return tirage;
    }

    public int multiplicateur() {
        if (case1 == case2 && case2 == case3) {
            if (case1 == 1) {
                return 2;
            } else if (case1 == 2) {
                return 5;
            } else if (case1 == 3) {
                return 10;
            }
        }
        return 1;
    }
}
