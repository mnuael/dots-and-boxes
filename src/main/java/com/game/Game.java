package com.game;

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
}