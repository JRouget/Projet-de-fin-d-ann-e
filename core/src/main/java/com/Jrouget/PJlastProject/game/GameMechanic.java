package com.Jrouget.PJlastProject.game;

import java.util.Random;

public class GameMechanic {

    private int playerSold = 0;
    private final int roundCost = 1;

    private int currentRound = 1;
    private int roundQuota = 20;
    private int coef;
    private int score;

    private int reward = 100;
    private int ticketsBonus = 0;
    private int tickets = 5;

    private int probaApple = 60;
    private int probaOrange = 30;
    private int probaSeven = 10;

    private int proba;

    private int case1;
    private int case2;
    private int case3;

    private Random random = new Random();

    public boolean canPlay(){

        return tickets >= roundCost;
    }

    public int[] tirage(){

        tickets -= roundCost;
        System.out.println("Player's balance : " + playerSold);

        case1 = generateSymbol();
        case2 = generateSymbol();
        case3 = generateSymbol();

        coef = multiplier();

        score += (case1 + case2 + case3) * coef;
        System.out.println("score =" + score);
        System.out.println("coef =" + coef);
        System.out.println("tirages =" + tickets);

        win();
        return new int[]{case1, case2, case3};
    }

    public int generateSymbol() {
        proba = random.nextInt(100);
        if (proba < probaApple) {
            return 1;
        } else if (proba > probaApple && proba < probaOrange + probaApple) {
            return 2;
        } else {
            return 3;
        }
    }

    public void win() {
        if (score >= roundQuota){
            roundQuota += roundQuota;
            currentRound += 1;
            playerSold += reward;
            reward += 50;
            score = 0;
        }
    }

    public void buying(int price) {
        this.playerSold -= price;
    }

    public int getPlayerSold() {
        return playerSold;
    }

    public int getScore() {
        return score;
    }

    public int getQuota() {
        return roundQuota;
    }

    public int getRound() {
        return currentRound;
    }

    public int getTickets() {
        return tickets;
    }

    public int multiplier() {
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

    public void appleBoost(int bonus) {
        this.probaApple += bonus;
    }

    public void orangeBoost(int bonus) {
        this.probaOrange += bonus;
    }

    public void sevenBoost(int bonus) {
        this.probaSeven += bonus;
    }

    public void getTicketsBonus(int bonus) {
        this.ticketsBonus += bonus;
    }

    public void prepareNewRound() {
        tickets = 10 + ticketsBonus;
    }
}
