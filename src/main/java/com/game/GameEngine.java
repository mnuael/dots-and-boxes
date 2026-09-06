package com.game;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.PropertyTheme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

class GameEngine {
    final static String PLAYER_1_COLOR = "red";
    final static String PLAYER_2_COLOR = "blue";
    final static String SELECTED_BG_COLOR = "green";
    final static String NEUTRAL_COLOR = "white";
    final static TextColor PLAYER_1_ANSI = TextColor.ANSI.RED;
    final static TextColor PLAYER_2_ANSI = TextColor.ANSI.BLUE;

    Panel mainPanel;
    Panel gridPanel;
    Panel scorePanel;
    Screen screen;

    // A ticker to keep track of whose turn it is
    // odd is player 1 red
    // even is plater 2 blue
    int playerTicker = 0;

    final Grid gridState;

    Panel getMainPanel() { return mainPanel; }

    public GameEngine(Grid gridState, Screen screen) {
        this.gridState = gridState;
        this.screen = screen;
    }

    void initPanel() {
        Panel panel = new Panel();
        initGridPanel();
        updateScorePanel();
        panel.addComponent(gridPanel);
        panel.addComponent(scorePanel.withBorder(Borders.singleLine("SCORE PANEL")));
        mainPanel = panel;
    }

    /**
     * Initialises and sets a grid panel.
     */
    void initGridPanel() {
        GridLayout gridLayout = new GridLayout(gridState.getLength());
        Panel panel = new Panel();
        panel.setLayoutManager(gridLayout);
        for(int y = 0; y < gridState.getHeight(); ++y) {
            for (int x = 0; x < gridState.getLength(); ++x) {
                Cell cell = gridState.getCell(x, y);

                if(cell instanceof DotCell dot) {
                    panel.addComponent(new Label(dot.toString()));
                } else if(cell instanceof HomeCell home) {
                    final Player owner = home.getOwner();
                    panel.addComponent(new Label(owner == null
                            ? " "
                            : owner.toString()));
                } else {
                    panel.addComponent(getButton(cell.toString(), x, y));
                }
            }
        }
        gridPanel = panel;
    }

    Button getButton(String toRenderSymbol, int x, int y) {
        Button button = new Button(" ", () -> {});

        button.setRenderer(new Button.FlatButtonRenderer());
        button.setTheme(getNormalButtonTheme());
        button.addListener((b) -> {
            b.setEnabled(false);
            b.setLabel(toRenderSymbol);
            b.setTheme(getPlayerBasedButtonTheme());
            Player player = getActivePlayer();
            this.gridState.fill(x, y, player);
            updateGridAfterPlayerMove(player, gridState, x, y);
            playerTicker++;
            updateScorePanel();

            System.out.println(gridState);
            try {
                screen.refresh();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return button;
    }

    private void updateScorePanel() {
        if(scorePanel == null) {
            scorePanel = new Panel();
        }
        scorePanel.removeAllComponents();
        final Label playerOneLabel = new Label("Player 1: "+calculateScore(Player.P_1));
        final Label playerTwoLabel = new Label("Player 2: "+calculateScore(Player.P_2));
        playerOneLabel.addTo(scorePanel);
        playerTwoLabel.addTo(scorePanel);
        if(getActivePlayer()==Player.P_1) {
            playerOneLabel.setBackgroundColor(getActivePlayerColorAnsi());
        }
        if(getActivePlayer()==Player.P_2) {
            playerTwoLabel.setBackgroundColor(getActivePlayerColorAnsi());
        }
    }

    private String calculateScore(Player player) {
        final int score = getScore(player, gridState);
        return String.valueOf(score);
    }

    PropertyTheme getNormalButtonTheme() {
        var p = new Properties();
        p.put("background[SELECTED]", SELECTED_BG_COLOR);
        p.put("background", NEUTRAL_COLOR);
        return new PropertyTheme(p);
    }

    PropertyTheme getPlayerBasedButtonTheme() {
        var p = new Properties();
        var color = getActivePlayerColor();
        p.put("foreground", NEUTRAL_COLOR);
        p.put("background", color);
        return new PropertyTheme(p);
    }

    private String getActivePlayerColor() {
        return getActivePlayer() == Player.P_1
                ? PLAYER_1_COLOR
                : PLAYER_2_COLOR;
    }

    private TextColor getActivePlayerColorAnsi() {
        return getActivePlayer() == Player.P_1
                ? PLAYER_1_ANSI
                : PLAYER_2_ANSI;
    }

    Player getActivePlayer() {
        return playerTicker%2==0
                ? Player.P_1
                : Player.P_2;
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
            if(playerMoveCell instanceof HorizontalLineCell) {
                affectedHomeCells.add(getAdjacentHomeCell(playerMoveCell, Direction.UP, grid));
                affectedHomeCells.add(getAdjacentHomeCell(playerMoveCell, Direction.DOWN, grid));
            }
            if(playerMoveCell instanceof VerticalLineCell) {
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
