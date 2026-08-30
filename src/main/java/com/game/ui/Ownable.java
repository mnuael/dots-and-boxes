package com.game.ui;

import com.game.domain.Player;

public interface Ownable {
    void setOwner(Player owner);

    Player getOwner();
}
