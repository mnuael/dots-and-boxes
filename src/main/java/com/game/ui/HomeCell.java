package com.game.ui;

import com.game.domain.Player;

/**
 * Cell that represents the boxes that can be owned by the player.
 */
public class HomeCell extends Cell implements Ownable {
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