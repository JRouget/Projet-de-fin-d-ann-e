package com.Jrouget.PJlastProject;

import java.util.Random;

public class GameMechanic {
    private Integer resultat;
    private int soldeJoueur = 0;
    private final int coutTirage = 1;

    private int mancheActuelle = 1;
    private int quotaManche = 1;
    private int coef;
    private int score;

    private int recompense = 100;
    private int tirage = 10;

    private int probaApple = 60;
    private int probaOrange = 30;
    private int probaSeven = 10;

    private int proba;

    private int case1;
    private int case2;
    private int case3;

    private ShopScreen shopScreen;

    private Random random = new Random();

    public boolean peutJouer(){

        return tirage >= coutTirage;
    }

    public int[] tirage(){

        tirage -= coutTirage;
        System.out.println("Player's balance : " + soldeJoueur);

        case1 = generateSymbol();
        case2 = generateSymbol();
        case3 = generateSymbol();

        coef = multiplicateur();

        score += (case1 + case2 + case3) * coef;
        System.out.println("score =" + score);
        System.out.println("coef =" + coef);
        System.out.println("tirages =" + tirage);

        win();
        return new int[]{case1, case2, case3};
    }

    public int generateSymbol() {
        proba = random.nextInt(100);
        if (proba < probaApple) {
            return 1;
        } else if (proba < probaOrange + probaApple) {
            return 2;
        } else {
            return 3;
        }
    }

    public void win() {
        if (score >= quotaManche){
            quotaManche += quotaManche;
            mancheActuelle += 1;
            soldeJoueur += recompense;
            recompense += 50;
            tirage = 10;
            score = 0;
        }
    }

    public void buying(int price) {
        this.soldeJoueur -= price;
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

    public void boosterApple(int bonus) {
        this.probaApple += bonus;
    }

    public void boosterOrange(int bonus) {
        this.probaOrange += bonus;
    }

    public void boosterSeven(int bonus) {
        this.probaSeven += bonus;
    }
}
