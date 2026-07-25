package com.game.domain;

/**
 * Box is created when four lines are joined. It will have 4 dots and 4 lines.
 */
public class Box {
    final Dot leftTop;
    final Dot leftBottom;
    final Dot rightTop;
    final Dot rightBottom;

    public Box(Dot leftTop, Dot leftBottom, Dot rightTop, Dot rightBottom) {
        this.leftTop = leftTop;
        this.leftBottom = leftBottom;
        this.rightTop = rightTop;
        this.rightBottom = rightBottom;
    }
}
