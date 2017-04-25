package ru.itacademy.miner;

import javax.swing.*;
import java.util.Random;

class GameField {

    private Cell[][] cells;

    /**
     * @param GameFieldWidth
     * @param GameFieldHeight
     */
    public GameField(int GameFieldWidth, int GameFieldHeight) {
        cells = new Cell[GameFieldWidth][GameFieldHeight];
        Random rnd = new Random();
        // TODO:
    }

    /**
     * @param x
     * @param y
     * @param button
     */
    public void bind(int x, int y, JButton button){
        cells[y][x] = new Cell();
        cells[y][x].setButton(button);
        cells[y][x].setX(x);
        cells[y][x].setY(y);
        cells[y][x].setClicked(false);
        cells[y][x].setFlag(false);
        cells[y][x].setMine(false);
        //// TODO: 21.07.2016
    }

    /**
     *
     */
    public void initMines(){
        Random rnd = new Random();

    }

    /**
     * @param buttonX
     * @param buttonY
     * @param button
     */
    public void onClick(int buttonX, int buttonY, JButton button) {
        // TODO:
        JOptionPane.showMessageDialog(null, "Это кнопка (x = " + buttonX + ", y = " + buttonY + ")");
    }
}
