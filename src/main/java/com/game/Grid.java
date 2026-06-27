package com.game;

class Grid {
    final int
            length, // length of the grid
            width, // width of the grid
            xDotCount, // number of dots to display along x axis
            yDotCount; // number of dots to display along y axis
    final Cell[][] cellTable;

    int getCellCountPerLine() {
        return length;
    }

    Grid(int dotCountPerLine) {
        this.xDotCount = dotCountPerLine;
        this.yDotCount = dotCountPerLine;
        this.length = dotCountPerLine + (dotCountPerLine - 1);
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
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
 }