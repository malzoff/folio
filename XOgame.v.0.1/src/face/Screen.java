package face;

import bin.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Created on 04.03.2017.
 * утилитарные методы, вынесенные в отдельный класс во избежание загромождения
 * основных классов
 */

public class Screen {


    /**
     * centers @param frame on the screen
     *
     * @param frame - JFrame object
     */
    public static void center(JFrame frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    /**
     * @param weightX    >= 0
     * @param weightY    >= 0
     * @param gridWidth  >= 0
     * @param gridHeight >= 0
     * @param fill       [0, 1, 2]
     */
    public static GridBagConstraints gridBagSetup(double weightX, double weightY, int gridWidth, int gridHeight, int fill) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = weightX;
        constraints.weighty = weightY;
        constraints.gridwidth = gridWidth;
        constraints.gridheight = gridHeight;
        constraints.fill = fill;
        return constraints;
    }

    //public enum PlayerSide {X_WON, O_WON, DRAW, NO_ONE_YET}

    public static String winnerMessage(JFrame frame, Game.PlayerSide winner) {
        String w = "";
        switch (winner) {
            case NO_ONE_YET:
                return null;
            case X:
                w = winner == Game.getPlayerSide() ? "You won!" : "You lose!";
                break;
            case O:
                w = winner == Game.getPlayerSide() ? "You won!" : "You lose!";
                break;
            case DRAW:
                w = "Draw round!";
                break;
        }
        String dialogText = w + " Play again?";
        int i;
        i = JOptionPane.showConfirmDialog(frame, dialogText, UIManager.getString("OptionPane.titleText"),
                JOptionPane.YES_NO_OPTION);

        if (i == 0) {
            // String[] args = new String[1];
            System.out.println("ng"); //debug
            GameScreen.close();
            MainApp.newGame();
        } else {
            System.out.println("Exit");//debug
            System.exit(0);
        }
        return null;
    }

}
