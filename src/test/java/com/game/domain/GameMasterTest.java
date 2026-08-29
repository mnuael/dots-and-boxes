package com.game.domain;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class GameMasterTest {
    final GameMaster testedClass = GameMaster.getInstance();

    @Test
    public void test() {
        var result = testedClass.generateBoxList(3,3);

        Assertions.assertNotNull(result);
    }
}
