package com.game;

import com.game.Grid;

class Game {
    public static void main(String[] args) {

        final var grid = new Grid(2, 2);
        System.out.println("Welcome to dots and boxes.");
        System.out.println(grid);
    }
}