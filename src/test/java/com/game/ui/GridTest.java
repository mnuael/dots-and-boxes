package com.game.ui;

import com.game.domain.Player;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class GridTest {
    final Player player = Player.P_1;

    @Test
    @DisplayName("fill() sets owner on vacant cell")
    public void fillInLine() throws Exception {
         final Grid grid = new Grid(2);

        Assertions.assertEquals(3,grid.getCellTable().length);
        Assertions.assertEquals(3,grid.getCellTable()[0].length);

        VacantCell result = (VacantCell) grid.getCell(1,1);
        Assertions.assertNull(result.getOwner());

        grid.fill(1, 1, player);

        Assertions.assertEquals(player, result.getOwner());
    }

    @Test
    @DisplayName("fill() throws exception when co-ordinate is outside grid")
    public void fillInLineThrows() throws Exception {
        final Grid grid = new Grid(2);

        Assertions.assertThrows(InvalidMoveException.class,
                () -> grid.fill(3, 3, player));
    }
}
