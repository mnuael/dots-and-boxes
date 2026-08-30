package com.game;

import com.game.domain.Player;
import com.game.ui.*;
import com.googlecode.lanterna.graphics.PropertyTheme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.Properties;

public class GameEngine {
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
            Player player = playerTicker%2==0
                    ? Player.P_2
                    : Player.P_1;
            this.gridState.fill(x, y, player);
            System.out.println(gridState);
            playerTicker++;
            updateScorePanel();
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
        new Label("Player 1: "+calculateScore(Player.P_1)).addTo(scorePanel);
        new Label("Player 2: "+calculateScore(Player.P_2)).addTo(scorePanel);
        new Label("Active: "+getActivePlayer()).addTo(scorePanel);
    }

    private String calculateScore(Player player) {
        // TODO: calculate score
        return "0";
    }

    PropertyTheme getNormalButtonTheme() {
        var p = new Properties();
        p.put("background[SELECTED]", "green");
        p.put("background", "white");
        return new PropertyTheme(p);
    }

    PropertyTheme getPlayerBasedButtonTheme() {
        var p = new Properties();
        var color = getActivePlayer() == Player.P_1
                ? "red"
                : "blue";
        p.put("foreground", "white");
        p.put("background", color);
        return new PropertyTheme(p);
    }

    Player getActivePlayer() {
        return playerTicker%2==0
                ? Player.P_1
                : Player.P_2;
    }
}
