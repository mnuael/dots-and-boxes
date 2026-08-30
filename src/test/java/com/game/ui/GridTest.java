package com.game.ui;

import com.game.domain.Player;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class GridTest {
    @Test
    @DisplayName("fill() sets owner on vacant cell")
    public void fillOwner() throws Exception {
         final Grid grid = new Grid(2);

        Assertions.assertEquals(3,grid.getCellTable().length);
        Assertions.assertEquals(3,grid.getCellTable()[0].length);

        HomeCell result = (HomeCell) grid.getCell(1,1);
        Assertions.assertNull(result.getOwner());

        grid.fill(1, 1, Player.P_1);

        Assertions.assertEquals(Player.P_1, result.getOwner());
    }

    @Test
    @DisplayName("fill() does not set owner when cell is owned")
    public void fillThrowsIfHomeNotVacant() {
        final Grid grid = new Grid(2);

        Assertions.assertEquals(3,grid.getCellTable().length);
        Assertions.assertEquals(3,grid.getCellTable()[0].length);

        HomeCell result = (HomeCell) grid.getCell(1,1);
        result.setOwner(Player.P_1);
        Assertions.assertNotNull(result.getOwner());
        Assertions.assertThrows(InvalidMoveException.class, () -> grid.fill(1, 1, Player.P_2));
        Assertions.assertEquals(Player.P_1, result.getOwner());
    }

    @Test
    @DisplayName("fill() throws exception when co-ordinate is outside grid")
    public void fillInLineThrows() throws Exception {
        final Grid grid = new Grid(2);

        Assertions.assertThrows(InvalidMoveException.class,
                () -> grid.fill(3, 3, Player.P_1));
    }
}
