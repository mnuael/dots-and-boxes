package com.game;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.PropertyTheme;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.Theme;
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

            final var grid = new Grid(4, 5);
            terminal.putString("Welcome to dots and boxes.");
            terminal.putCharacter('\n');

            var screen = new TerminalScreen(terminal);
            screen.startScreen();

            var panel = new Panel();
            GridLayout gridLayout = new GridLayout(3);
            panel.setLayoutManager(gridLayout);

            panel.addComponent(new Label("o"));
            panel.addComponent(getButton("-"));
            panel.addComponent(new Label("o"));
            panel.addComponent(getButton("|"));
            panel.addComponent(new EmptySpace());
            panel.addComponent(getButton("|"));
            panel.addComponent(new Label("o"));
            panel.addComponent(getButton("-"));
            panel.addComponent(new Label("o"));

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