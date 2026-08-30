package com.game.ui;

import com.game.domain.Player;

/**
 * Cell makes the "   " (3 empty space) in the grid.
 */
class VacantCell extends Cell {
    Player owner;

    public Player getOwner() { return owner; }

    public void setOwner(Player player) {
        if(owner != null && player != owner) {
            throw new InvalidMoveException("This cell belongs to " + player.toString());
        }

        owner = player;
    }

    @Override
    public String toString() {
        return owner != null ? owner.toString() : "   ";
    }
}