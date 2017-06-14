package ru.mda.home.myapplication;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Zver on 25.05.2017.
 */

public class EnemyCircle extends SimpleCircle {

    public static final int MIN_RADIUS = 10;
    public static final int MAX_RADIUS_DIVIDER = 6;
    public static final int ENEMY_COLOR = Color.RED;
    public static final int FOOD_COLOR = Color.GREEN;
    public static final int RANDOM_SPEED = 20;
    private static int maxRadius;
    private int dx;
    private int dy;

    public EnemyCircle(int x, int y, int radius, int dx, int dy) {
        super(x, y, radius);
        this.dx = dx;
        this.dy = dy;
    }

    public static EnemyCircle getRandomCircle() {
        Random rnd = new Random();
        int x = rnd.nextInt(GameManager.getWidth());
        int y = rnd.nextInt(GameManager.getHeight());
        int dx = 1 + rnd.nextInt(RANDOM_SPEED)-RANDOM_SPEED/2;
        int dy = 1 + rnd.nextInt(RANDOM_SPEED)-RANDOM_SPEED/2;
        maxRadius = (Math.min(GameManager.getHeight(), GameManager.getWidth()) / MAX_RADIUS_DIVIDER);
        int radius = MIN_RADIUS + rnd.nextInt(maxRadius - MIN_RADIUS);
        return new EnemyCircle(x, y, radius, dx, dy);
    }

    public void setEnemyColor(MainCircle mainCircle) {
        setColor(isSmallerThan(mainCircle) ? FOOD_COLOR : ENEMY_COLOR);
    }

    public boolean isSmallerThan(SimpleCircle circle) {
        return radius < circle.radius;
    }

    public void moveOneStep() {
        x += dx;
        y += dy;
        checkBounds();
    }

    private void checkBounds() {
        if (x > GameManager.getWidth()|| x < 0){
            dx = -dx;
        }
        if (y > GameManager.getWidth()|| y < 0){
            dy = -dy;
        }
    }
}
