package com.game;
/**
 * Cells make up a grid.
 * For example a 4x4 grid has 16 cells.
 */
abstract class Cell {
    int x;
    int y;

    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}