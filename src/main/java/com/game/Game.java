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
            screen.refresh();
            final var grid = new Grid(20);
            final var engine = new GameEngine(grid, screen);
            engine.initPanel();

            var window = new BasicWindow();
            window.setComponent(engine.getMainPanel());

            var gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
            gui.addWindowAndWait(window);
            terminal.flush();
        } catch (Exception e) {
            throw e;
        }
    }
}