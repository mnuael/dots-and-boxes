package com.game.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that determines who wins the game
 */
public class GameMaster {
    private static GameMaster instance;

    private GameMaster() {}
    public  static GameMaster getInstance() {
        if(instance == null) {
            instance = new GameMaster();
        }
        return instance;
    }

    public ArrayList<Box> generateBoxList(int xDotCount, int yDotCount) {
        if(xDotCount < 2 || yDotCount < 2) {
            throw new IllegalArgumentException("Number of dots must be at least 2 along x and y");
        }

        //determine number of boxes than can exist and create an array of that length
        final int boxCount = (xDotCount-1)*(yDotCount-1);
        final var boxList = new ArrayList<Box>(boxCount+1);

        //iterate through each box and find a way to determine the dot positions of each
         for(int i = 0; i < boxCount; i++) {
            final Dot leftTop = new Dot(i,i);
            final Dot rightTop = new Dot(i+1,i);
            final Dot leftBottom = new Dot(i,i+1);
            final Dot rightBottom = new Dot(i+1,i+1);

            final Box box = new Box(leftTop,leftBottom,rightTop,rightBottom);
            boxList.add(i, box);
         }
        return boxList;
    }
}
