package ru.mda.home.myapplication;

/**
 * Created by Zver on 21.05.2017.
 */

public interface ICanvasView {
    void drawCircle(SimpleCircle circle);

    void redraw();

    void showMessage(String s);
}
