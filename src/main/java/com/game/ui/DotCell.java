package com.game.ui;

/**
 * Cell makes the "o" in the grid.
 */
public class DotCell extends Cell {
    DotCell(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "o";
    }
}