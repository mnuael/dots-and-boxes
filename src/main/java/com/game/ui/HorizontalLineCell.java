package com.game.ui;

import com.game.domain.Player;

/**
 * Cell that makes the "---" in the grid.
 */
class HorizontalLineCell extends Cell implements Ownable {
    Player owner;

    @Override
    public String toString() {
        return "-";
    }

    @Override
    public void setOwner(Player newOwner) {
        if(owner != null && owner != newOwner) {
            throw new InvalidMoveException("This line was already filled by to " + owner);
        }

        owner = newOwner;
    }

    @Override
    public Player getOwner() { return owner; }
}