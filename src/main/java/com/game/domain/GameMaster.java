package com.game.domain;

import com.game.ui.*;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Check grid to determine if player owns a home and fill it
     * @param player player who made the move
     * @param grid grid containing cells
     */
    public void updateGridAfterPlayerMove(Player player, Grid grid, int playerMoveX, int playerMoveY) {
        final Cell playerMoveCell = grid.getCell(playerMoveX, playerMoveY);
        // ensure that the cell is a line cell
        if(!(playerMoveCell instanceof Ownable) || (playerMoveCell instanceof HomeCell)) {
         throw new InvalidMoveException("The move is not valid.");
        }

        // find home cells to consider from the x, y
        List<HomeCell> affectedHomeCells = new ArrayList<>();
        if(playerMoveX==0) {
            // homeCell is to the right
            affectedHomeCells.add(getAdjacentHomeCell(playerMoveCell, Direction.RIGHT, grid));
        } else if(playerMoveX==grid.getLength()-1) {
            // homeCell is to the left
            affectedHomeCells.add(getAdjacentHomeCell(playerMoveCell, Direction.LEFT, grid));
        } else if(playerMoveY==0) {
            // homeCell is on bottom
            affectedHomeCells.add(getAdjacentHomeCell(playerMoveCell, Direction.DOWN, grid));
        } else if(playerMoveY==grid.getHeight()-1) {
            // homeCell is on top
            affectedHomeCells.add(getAdjacentHomeCell(playerMoveCell, Direction.UP, grid));
        } else {
            if(playerMoveCell instanceof HorizontalLineCell horizontal) {
                affectedHomeCells.add(getAdjacentHomeCell(playerMoveCell, Direction.UP, grid));
                affectedHomeCells.add(getAdjacentHomeCell(playerMoveCell, Direction.DOWN, grid));
            }
            if(playerMoveCell instanceof VerticalLineCell vertical) {
                affectedHomeCells.add(getAdjacentHomeCell(playerMoveCell, Direction.LEFT, grid));
                affectedHomeCells.add(getAdjacentHomeCell(playerMoveCell, Direction.RIGHT, grid));
            }
        }
        checkAndUpdateGrid(affectedHomeCells, grid, player);
    }

    /**
     * Iterate through affected home cells and update grid if any affected home cell is won by the player
     * @param affectedHomeCells list of home cells to check
     * @param grid grid containing cells
     * @param player player
     */
    private void checkAndUpdateGrid(List<HomeCell> affectedHomeCells, Grid grid, Player player) {
        for(HomeCell homeCell : affectedHomeCells) {
            int x = homeCell.getX();
            int y = homeCell.getY();
            // when home cell is not already owned, check if the player won it
            if(homeCell.getOwner()==null) {
                int leftX = x-1;
                int leftY = y;
                int rightX = x+1;
                int rightY = y;
                int topY = y-1;
                int topX = x;
                int bottomY = y+1;
                int bottomX = x;
                final int[][] neighbourCoordinates = new int[4][2];
                neighbourCoordinates[0] = new int[]{leftX, leftY};
                neighbourCoordinates[1] = new int[]{rightX, rightY};
                neighbourCoordinates[2] = new int[]{topX, topY};
                neighbourCoordinates[3] = new int[]{bottomX, bottomY};

                int neighborsOwned = 0;
                for(int i=0;i<4;++i) {
                    Cell c = grid.getCell(neighbourCoordinates[i][0], neighbourCoordinates[i][1]);
                    if(c instanceof Ownable o) {
                        if(o.getOwner()!=null) {
                            ++neighborsOwned;
                        }
                    }
                }

                // if all fours sides of home cell is filled, player won it
                if(neighborsOwned==4) {
                    grid.fill(x, y, player);
                }
            }
        }
    }

    /**
     * Get home cell adjacent to the provided cell
     * @param cell whose adjacent home cell have to be found
     * @param direction where to look for the RIGHT, LEFT, UP or DOWN
     * @param grid grid containing cells to search
     * @return home cell adjacent to the provided cell, null if not found
     */
    private HomeCell getAdjacentHomeCell(Cell cell, Direction direction, Grid grid) {
        int x = switch (direction) {
            case RIGHT -> cell.getX() + 1;
            case LEFT -> cell.getX() - 1;
            case UP,DOWN -> cell.getX();
        };

        int y = switch (direction) {
            case UP -> cell.getY() - 1;
            case DOWN -> cell.getY() + 1;
            case LEFT,RIGHT -> cell.getY();
        };
        final var result = grid.getCell(x,y);
        if(result instanceof HomeCell homeCell) {
            return homeCell;
        }
        return null;
    }
}

enum Direction {
    UP, DOWN, LEFT, RIGHT;
}
