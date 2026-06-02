package com.Jrouget.PJlastProject;

import java.util.Random;

public class GameMechanic {
    private Integer resultat;
    private Integer case1;
    private Integer case2;
    private Integer case3;
    private Random random;

    public void tirage(){

        random = new Random();

        int case1 = random.nextInt(3) + 1;
        int case2 = random.nextInt(3) + 1;
        int case3 = random.nextInt(3) + 1;

        System.out.println(case1 + " " + case2 + " " + case3 );
    }
}
