package com.game.ui;

import com.game.domain.Player;

public class Grid {
    final int
            length, // length of the grid
            height, // width of the grid
            xDotCount, // number of dots to display along x axis
            yDotCount; // number of dots to display along y axis
    final Cell[][] cellTable;

    public int getCellCountPerLine() {
        return length;
    }
    public Cell[][] getCellTable() { return cellTable; }

    public Grid(int dotCountPerLine) {
        this.xDotCount = dotCountPerLine;
        this.yDotCount = dotCountPerLine;
        this.length = dotCountPerLine + (dotCountPerLine - 1);
        this.height = yDotCount + (yDotCount - 1);
        this.cellTable = new Cell[length][height];

        /*
         * O   -   O start iterating from top to bottom, left to right,
         * |       |
         * O   -   O
         *
         * 2 x 2 grid with 2 dots per line
         */

        for(int y=0;y<height;++y) {
            for(int x=0;x<length;++x) {
                if(y%2==0) {
                    if(x%2==0) {
                        cellTable[x][y] = new DotCell();
                    } else {
                        cellTable[x][y] = new HorizontalLineCell();
                    }
                } else {
                    cellTable[x][y] = x%2==0
                            ? new VerticalLineCell()
                            : new HomeCell();
                }
            }
        }
    }

    public void fill(int xCellPos, int yCellPos, Player player) throws Exception {
        if(xCellPos<0 || yCellPos<0) {
            throw new InvalidMoveException("Co ordinates cannot be negative.");
        }

        if(xCellPos >= length || yCellPos >= height) {
            throw new InvalidMoveException("Co ordinates out of bounds.");
        }

        final Cell cell = cellTable[xCellPos][yCellPos];
        if(cell instanceof HomeCell v) {
            v.setOwner(player);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for(int y=0;y<height;++y) {
            for(int x=0;x<length;++x) {
                sb.append(cellTable[x][y]);
//                sb.append(" " + cellTable[x][y] +"["+x+","+y+"]" );
            }
            sb.append(System.lineSeparator());
        }
        sb.append("Grid size: "+xDotCount+"x"+yDotCount);
        return sb.toString();
    }

    /**
     * Get cell at the given co-ordinate
     * @param xPos x co-ordinate
     * @param yPos y co-ordinate
     * @return Cell at the given co-ordinate, null if not found
     */
    public Cell getCell(int xPos, int yPos) {
        Cell result;
        try {
            result = cellTable[xPos][yPos];
        } catch (ArrayIndexOutOfBoundsException e) {
            result = null;
        }

        return result;
    }
 }