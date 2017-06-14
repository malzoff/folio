package ru.mda.home.myapplication;

import static ru.mda.home.myapplication.MainCircle.INIT_RADIUS;

/**
 * Created by Zver on 25.05.2017.
 */

public class SimpleCircle {
    //public static final int INIT_RADIUS = 50;
    protected int x;
    protected int y;
    protected int radius;
    protected int color;


    public SimpleCircle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public SimpleCircle getCircleArea(int multiplier) {
        return new SimpleCircle(x,y,radius*multiplier);
    }

    public boolean isIntersects(SimpleCircle circle) {
        return radius+circle.radius >= Math.hypot(x-circle.x, y-circle.y);
    }
}
