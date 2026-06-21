package com.Jrouget.PJlastProject;

import com.Jrouget.PJlastProject.game.GameMechanic;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameMechanicTest {
    @Test
    public void testCanPlayInitialState() {
        GameMechanic mechanic = new GameMechanic();
        assertTrue(mechanic.canPlay());
    }
    @Test
    public void testBuyingReducesSold() {
        GameMechanic mechanic = new GameMechanic();

        mechanic.win();
        int initialSold = mechanic.getPlayerSold();

        mechanic.buying(10);

        assertEquals(initialSold - 10, mechanic.getPlayerSold());
    }
}
