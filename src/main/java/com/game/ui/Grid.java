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

        for(int i=0; i<length; ++i) {
            for(int j = 0; j< height; ++j) {
                if(i%2==0) { // 0, 1, 3 ..

                    if(j%2==0) {
                        cellTable[i][j] = new DotCell();
                    } else {
                        cellTable[i][j] = new HomeCell();
                    }
                } else { // even lines
                    cellTable[i][j] = new HomeCell();
                }
            }
        }
    }

    public void fill(int xCellPos, int yCellPos, Player player) throws Exception {
        if(xCellPos < 0 || yCellPos < 0) {
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
        for(int i=0; i<length; ++i) {
            for(int j=0; j<height; ++j) {
                sb.append(cellTable[i][j]);
            }
            sb.append(System.lineSeparator());
        }
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