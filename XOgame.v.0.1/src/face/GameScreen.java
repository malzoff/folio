package face;

import bin.Game;
import bin.GameField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created on 04.03.2017.
 */

public class GameScreen {

    public enum PlayerSide {X, O}

    private static final int width = 400;
    private static final int height = 450;
    private static final int gameFieldSize = 3;
    private static String playerSide;
    private static String aiSide;
    private static String appTitle = "XO: PlayerSide: ";
    private static JFrame frame= new JFrame();
    private static int turn = 0;
    private static JButton[][] field = new JButton[3][3];

    public static JButton getButtonFromField(int i, int j) {
        return field[i][j];
    }

    public static void addButtonToField(int i, int j, JButton button) {
        GameScreen.field[i][j] = button;
    }

    static void gameScreen(GameScreen.PlayerSide playerSide) {
        //frame = new JFrame();
        GameScreen.playerSide = playerSide.toString();
        GameScreen.aiSide = playerSide == PlayerSide.X ? PlayerSide.O.toString() : PlayerSide.X.toString();
        frame.setTitle(appTitle + GameScreen.playerSide);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        Screen.center(frame);
        GridLayout layout = new GridLayout(gameFieldSize, gameFieldSize);
        frame.setLayout(layout);

        //игровое поле
        //Заполнение сцены кнопками-клетками
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton button = new JButton();
                addButtonToField(i, j, button);
                frame.add(button);
                CellListener cellListener = new CellListener(i, j, button);
                button.addActionListener(cellListener);

            }
        }

        if (playerSide == PlayerSide.O){
            Game.setFirstGameTurn();
            Game.aiTurn();
            field[1][1].setText(PlayerSide.X.toString());
        }

        refreshTitle();

        frame.setVisible(true);
        frame.repaint();
        //Game.startGame();
    }

    public static int getTitleTurn() {
        return turn = Game.getGameTurn();
    }

    private static void refreshTitle() {
        frame.setTitle(appTitle + playerSide + " turn: " + getTitleTurn());
    }

    public static void close() {
        frame.setVisible(false);
        //frame.dispose();
        frame.removeAll();
    }


    private static class CellListener implements ActionListener {
        private int i;
        private int j;
        private JButton button;

        public CellListener(int i, int j, JButton button) {
            this.i = i;
            this.j = j;
            this.button = button;
        }

        public void actionPerformed(ActionEvent e) {
            if (Game.playerTurn(i, j)) {
                button.setText(GameScreen.playerSide);
                refreshTitle();

                System.out.println();//debug
                System.out.print("[" + i + "," + j + "] = "); // debug
                System.out.println(Game.getPlayerSide().toString()); //debug
                Game.aiTurn();
                int[] cors = Game.getAiTurn();
                getButtonFromField(cors[0], cors[1]).setText(GameScreen.aiSide);
                Game.parseField();

                Screen.winnerMessage(frame, Game.checkForWinner());
            }
            Screen.winnerMessage(frame, Game.checkForWinner());
        }
    }
}
