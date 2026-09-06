package com.game;

import com.googlecode.lanterna.screen.Screen;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;

public class GameEngineTest {
    @Mock
    Screen screen;

    @Test
    public void generateGrid() {
        var engine = new GameEngine(new Grid(2), screen);
        var result = engine.gridState;
        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.getCellTable().length);
        Assertions.assertEquals(3, result.getCellTable()[0].length);
    }

    @Test
    @DisplayName("Returns 2 when player owns 2 home cells")
    public void getScorePlayer() {
        var engine = new GameEngine(new Grid(3), screen);
        var grid = engine.gridState;
        /*
         * O O O
         * O O O
         * O O O
         */
        grid.fill(1,1, Player.P_1);
        grid.fill(1,3,Player.P_1);

        final int result = engine.getScore(Player.P_1, grid);

        Assertions.assertEquals(2, result);
    }

    @Test
    @DisplayName("Player wins 2 cells when the line between two cell is filled in and all other neighbouring lines are filled")
    public void playerWinsTwoCells() {
        var engine = new GameEngine(new Grid(3), screen);
        var grid = engine.gridState;
        /*
         * O O O
         * O O O
         * O O O
         */
        grid.fill(1,0, Player.P_1);
        grid.fill(1,2,Player.P_1);
        grid.fill(3,0,Player.P_1);
        grid.fill(3,2,Player.P_1);
        grid.fill(0,1,Player.P_1);
        grid.fill(4,1,Player.P_1);

        grid.fill(2,1,Player.P_2);
        engine.updateGridAfterPlayerMove(Player.P_2, grid,2,1);
        var result = engine.getScore(Player.P_2, grid);

        Assertions.assertEquals(2, result);
    }

    @Test
    @DisplayName("Player does not win when the line between two cell is filled but other neighbouring lines are not filled")
    public void playerDoesNotCells() {
        var engine = new GameEngine(new Grid(3), screen);
        var grid = engine.gridState;
        /*
         * O O O
         * O O O
         * O O O
         */
        grid.fill(1,0, Player.P_1);
        grid.fill(1,2,Player.P_1);
        grid.fill(3,0,Player.P_1);
        grid.fill(3,2,Player.P_1);

        grid.fill(2,1,Player.P_2);
        engine.updateGridAfterPlayerMove(Player.P_2, grid,2,1);
        var result = engine.getScore(Player.P_2, grid);

        Assertions.assertEquals(0, result);
    }
}

