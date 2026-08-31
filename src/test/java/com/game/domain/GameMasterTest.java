package com.game.domain;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class GameMasterTest {
    final GameMaster testedClass = GameMaster.getInstance();

    @Test
    public void generateGrid() {
        var result = testedClass.generateGrid(2);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.getCellTable().length);
        Assertions.assertEquals(3, result.getCellTable()[0].length);
    }

    @Test
    @DisplayName("Returns 2 when player owns 2 home cells")
    public void getScorePlayer() {
        var grid = testedClass.generateGrid(3);
        /*
         * O O O
         *
         * O O O
         * O O O
         */
        grid.fill(1,1,Player.P_1);
        grid.fill(1,3,Player.P_1);

        final int result = testedClass.getScore(Player.P_1, grid);

        Assertions.assertEquals(2, result);
    }

}
