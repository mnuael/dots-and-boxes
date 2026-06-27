package com.game;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.PropertyTheme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Properties;

class Game {
    public static void main(String[] args) throws IOException {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();

        Terminal terminal = null;
        try {
            terminal = defaultTerminalFactory.createTerminal();

            terminal.putString("Welcome to dots and boxes.");
            terminal.putCharacter('\n');

            var screen = new TerminalScreen(terminal);
            screen.startScreen();

            final var grid = new Grid(8);
            Panel panel = createPanel(grid);

            var window = new BasicWindow();
            window.setComponent(panel);

            var gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
            gui.addWindowAndWait(window);

            var gridStr = grid.toString();
            String[] split = gridStr.split("\n");
            for (int i = 0; i < split.length; ++i) {
                terminal.putString(split[i]);
                terminal.putCharacter('\n');
            }

            terminal.flush();
        } catch (Exception e) {
            throw e;
        }
    }

    private static Panel createPanel(Grid grid) {
        GridLayout gridLayout = new GridLayout(grid.getCellCountPerLine());
        Panel panel = new Panel();
        panel.setLayoutManager(gridLayout);

        for(int y = 0; y < grid.getCellCountPerLine(); ++y) {
            for (int x = 0; x < grid.getCellCountPerLine(); ++x) {
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

    static Button getButton(String toRenderSymbol) {
        Button button = new Button(" ", () -> {});

        button.setRenderer(new Button.FlatButtonRenderer());
        button.setTheme(getNormalButtonTheme());

        button.addListener( (b) -> {
            b.setEnabled(false);
            b.setLabel(toRenderSymbol);
            b.setTheme(getDisabledButtonTheme());
        });
        return button;
    }

    static PropertyTheme getNormalButtonTheme() {
        var p = new Properties();
        p.put("background[SELECTED]", "red");
        p.put("background", "white");
        return new PropertyTheme(p);
    }

    static PropertyTheme getDisabledButtonTheme() {
        var p = new Properties();
        p.put("foreground", "black");
        p.put("background", "white");
        return new PropertyTheme(p);
    }
}