package com.game;

import com.game.ui.Grid;
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

    Panel createPanel() {
        GridLayout gridLayout = new GridLayout(gridState.getCellCountPerLine());
        Panel panel = new Panel();
        panel.setLayoutManager(gridLayout);


        for(int y = 0; y < gridState.getCellCountPerLine(); ++y) {
            for (int x = 0; x < gridState.getCellCountPerLine(); ++x) {
                if(y%2==0) {
                    if(x%2==0) {
                        panel.addComponent(new Label("o"));
                    } else {
                        panel.addComponent(getButton("-"));
                    }
                } else {
                    if(x%2==0) {
                        panel.addComponent(getButton("|"));
                    } else {
                        panel.addComponent(new EmptySpace());
                    }
                }
            }
        }
        return panel;
    }

    Button getButton(String toRenderSymbol) {
        Button button = new Button(" ", () -> {});

        button.setRenderer(new Button.FlatButtonRenderer());
        button.setTheme(getNormalButtonTheme());

        button.addListener((b) -> {
            b.setEnabled(false);
            b.setLabel(toRenderSymbol);
            b.setTheme(getDisabledButtonTheme());
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
