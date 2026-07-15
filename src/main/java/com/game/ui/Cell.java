package com.game.ui;
/**
 * Cells make up a grid.
 *
 * For example a 4x4 grid has 16 cells.
 */
abstract class Cell {
    final String display;

    Cell(String display) {
        this.display = display;
    }
}