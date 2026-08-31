package com.game.domain;

import com.game.ui.Cell;
import com.game.ui.Grid;
import com.game.ui.HomeCell;

import java.util.ArrayList;

/**
 * Class that determines who wins the game
 */
public class GameMaster {
    private static GameMaster instance;

    private GameMaster() {}
    public  static GameMaster getInstance() {
        if(instance == null) {
            instance = new GameMaster();
        }
        return instance;
    }

    /**
     * Generate a grid with the given number of dots per line
     * @param dotCountPerLine number of dots per line
     * @return grid representing board with given dots per line
     */
    public Grid generateGrid(int dotCountPerLine) {
        return new Grid(dotCountPerLine);
    }

    /**
     * Get score of player by counting number of home cells owned by player
     * @param player player to check score
     * @param grid grid containing cells
     * @return number of home cells owned by given player
     */
    public int getScore(Player player, Grid grid) {
        int score = 0;
        for(int x=0;x<grid.getLength();++x) {
            for(int y=0;y<grid.getHeight();++y) {
                Cell cell = grid.getCell(x,y);
                if(cell instanceof HomeCell homeCell) {
                    if(homeCell.getOwner()==player) {
                        score++;
                    }
                }
            }
        }
        return score;
    }
}
