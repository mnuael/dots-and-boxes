package com.game.domain;

public class Line {
    final Dot start;
    final Dot end;
    final Player owner;

    public Line(Dot start, Dot end, Player owner) {
        this.start = start;
        this.end = end;
        this.owner = owner;
    }
}
