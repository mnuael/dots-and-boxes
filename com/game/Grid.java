package com.game;

import com.game.Cell;
import com.game.DotCell;
import com.game.HorizontalLineCell;
import com.game.VerticalLineCell;
import com.game.VacantCell;

class Grid {
    final int
            length, // length of the grid
            width, // width of the grid
            xDotCount, // number of dots to display along x axis
            yDotCount; // number of dots to display along y axis
    final Cell[][] cellTable;

    Grid(int xDotCount, int yDotCount) {
        this.xDotCount = xDotCount;
        this.yDotCount = yDotCount;
        this.length = xDotCount + (xDotCount - 1);
        this.width = yDotCount + (yDotCount - 1);
        this.cellTable = new Cell[length][width];

        for(int i=0; i<length; ++i) {
            for(int j=0; j<width; ++j) {
                if(i%2==0) { // 0, 1, 3 ..

                    if(j%   2==0) {
                        cellTable[i][j] = new DotCell();
                    } else {
                        cellTable[i][j] = new VacantCell();
                    }
                } else { // even lines
                    cellTable[i][j] = new VacantCell();
                }
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for(int i=0; i<length; ++i) {
            for(int j=0; j<width; ++j) {
                sb.append(cellTable[i][j].display);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
 }