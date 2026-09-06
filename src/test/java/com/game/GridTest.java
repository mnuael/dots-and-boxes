package com.game;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class GridTest {
    @Test
    @DisplayName("Grid is created with vertical and horizontal line cells")
    public void gridHasRightVerticalHorizontalCells() {
        Grid grid = new Grid(2);
        Assertions.assertAll(
                // row 1
                () -> Assertions.assertEquals(DotCell.class, grid.getCell(0,0).getClass()),
                () -> Assertions.assertEquals(HorizontalLineCell.class, grid.getCell(1,0).getClass()),
                () -> Assertions.assertEquals(DotCell.class, grid.getCell(2,0).getClass()),
                // row 2
                () -> Assertions.assertEquals(VerticalLineCell.class, grid.getCell(0,1).getClass()),
                () -> Assertions.assertEquals(HomeCell.class, grid.getCell(1,1).getClass()),
                () -> Assertions.assertEquals(VerticalLineCell.class, grid.getCell(2,1).getClass()),
                // row 3
                () -> Assertions.assertEquals(DotCell.class, grid.getCell(0,2).getClass()),
                () -> Assertions.assertEquals(HorizontalLineCell.class, grid.getCell(1,2).getClass()),
                () -> Assertions.assertEquals(DotCell.class, grid.getCell(2,2).getClass())

        );
    }

    @Test
    @DisplayName("fill() sets owner on home cell")
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
    @DisplayName("fill() sets owner on line cell")
    public void fillLine() {
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
    public void fillOwnerThrowsWhenOutOfBound() {
        final Grid grid = new Grid(2);

        Assertions.assertThrows(InvalidMoveException.class,
                () -> grid.fill(3, 3, Player.P_1));
    }
}
