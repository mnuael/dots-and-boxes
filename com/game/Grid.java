package com.game;

import com.game.Cell;

class Grid {
    final int length, width;
    final Cell[][] cellTable;

    Grid(int length, int width) {
        this.length = length;
        this.width = width;
        this.cellTable = new Cell[length][width];
    }
 }