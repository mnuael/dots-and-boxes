package com.game;

import com.game.ui.Grid;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

class Game {
    public static void main(String[] args) throws IOException {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();

        Terminal terminal;
        try {
            terminal = defaultTerminalFactory.createTerminal();

            terminal.putString("Welcome to dots and boxes.");
            terminal.putCharacter('\n');

            var screen = new TerminalScreen(terminal);
            screen.startScreen();

            final var grid = new Grid(8);
            final var engine = new GameEngine(grid);
            Panel panel = engine.createPanel();

            var window = new BasicWindow();
            window.setComponent(panel);

            var gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
            gui.addWindowAndWait(window);

            var gridStr = grid.toString();
            String[] split = gridStr.split("\n");
            for (String s : split) {
                terminal.putString(s);
                terminal.putCharacter('\n');
            }

            terminal.flush();
        } catch (Exception e) {
            throw e;
        }
    }
}