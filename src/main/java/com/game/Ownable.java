package com.game;

/**
 * Ownable by the player. Player can own:
 * LineCell when a line is drawn by the player
 * HomeCell when it is won by the player.
 */
interface Ownable {
    void setOwner(Player owner);

    Player getOwner();

    String getDisplay();
}
