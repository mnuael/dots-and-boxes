package com.game;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

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


            var button = new Button("-", new Runnable() {
                @Override
                public void run() {}
            });
            var button2 = new Button("|", new Runnable() {
                @Override
                public void run() {}
            });
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
        Button button = new Button(" ", () -> {
        }).setRenderer(new Button.FlatButtonRenderer());
        button.addListener( (b) -> {
            b.setEnabled(false);
            Button.Listener listener = btn -> btn.setEnabled(false);
            b.setLabel(toRenderSymbol);
        });
        return button;
    }
}