package ru.mda.home.myapplication;

import android.graphics.Color;

/**
 * Created by Zver on 21.05.2017.
 */

public class MainCircle extends SimpleCircle {
    public static final int INIT_RADIUS = 50;
    public static final int COLOR = Color.BLUE;

    public MainCircle(int x, int y) {
        super(x, y, INIT_RADIUS);
        setColor(COLOR);
    }

    public void moveMainCircleWhenTouchAt(int x, int y, int speed) {
        int dx = (-this.x + x) * speed / GameManager.getWidth();
        int dy = (-this.y + y) * speed / GameManager.getHeight();
        this.x += dx;
        this.y += dy;
    }

    public void initRadius() {
        radius = INIT_RADIUS;
    }

    public void growRadius(SimpleCircle circle) {
        radius = (int) Math.hypot(this.radius, circle.radius);
    }
}
