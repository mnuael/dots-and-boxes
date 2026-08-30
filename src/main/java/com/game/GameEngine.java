package com.game;

import com.game.domain.Player;
import com.game.ui.*;
import com.googlecode.lanterna.graphics.PropertyTheme;
import com.googlecode.lanterna.gui2.*;

import java.util.Properties;

public class GameEngine {
    // A ticker to keep track of whose turn it is
    // odd is player 1 red
    // even is plater 2 blue
    int playerTicker = 0;

    final Grid gridState;

    public GameEngine(Grid gridState) {
        this.gridState = gridState;
    }

    /**
     * Creates panel based on state of grid
     * @return panel representing initial state of grid
     */
    Panel createPanel() {
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
        return panel;
    }

    Button getButton(String toRenderSymbol, int x, int y) {
        Button button = new Button(" ", () -> {});

        button.setRenderer(new Button.FlatButtonRenderer());
        button.setTheme(getNormalButtonTheme());

        button.addListener((b) -> {
            b.setEnabled(false);
            b.setLabel(toRenderSymbol);
            b.setTheme(getDisabledButtonTheme());
            Player player = playerTicker%2==0
                    ? Player.P_2
                    : Player.P_1;
            this.gridState.fill(x, y, player);
            System.out.println(gridState);
            this.playerTicker++;
        });
        return button;
    }

    PropertyTheme getNormalButtonTheme() {
        var p = new Properties();
        p.put("background[SELECTED]", "red");
        p.put("background", "white");
        return new PropertyTheme(p);
    }

    PropertyTheme getDisabledButtonTheme() {
        var p = new Properties();
        var color = isPlayerRed()
                ? "red"
                : "blue";
        p.put("foreground", color);
        p.put("background", "white");
        return new PropertyTheme(p);
    }

    private boolean isPlayerRed() {
        return playerTicker % 2 == 0;
    }
}
