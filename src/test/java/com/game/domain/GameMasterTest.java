package com.game.domain;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class GameMasterTest {
    final GameMaster testedClass = GameMaster.getInstance();

    @Test
    public void generateGrid() {
        var result = testedClass.generateGrid(2);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.getCellTable().length);
        Assertions.assertEquals(3, result.getCellTable()[0].length);

        System.out.println(result.toString());
    }
}
