package com.game;

/**
 * Cell makes the "o" in the grid.
 */
class DotCell extends Cell {
    DotCell(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "o";
    }
}