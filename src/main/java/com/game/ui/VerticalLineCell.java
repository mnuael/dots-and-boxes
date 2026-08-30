package com.game.ui;

import com.game.domain.Player;

/**
 * Cell that makes the "|" in the grid.
 */
class VerticalLineCell extends Cell implements Ownable{
    Player owner;

    @Override
    public String toString() {
        return "|";
    }

    @Override
    public void setOwner(Player newOwner) {
        if(owner != null && owner != newOwner) {
            throw new InvalidMoveException("Line was already filled by " + owner);
        }

        owner = newOwner;
    }

    @Override
    public Player getOwner() { return owner; }
}