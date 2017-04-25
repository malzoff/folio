package face;

import bin.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Created on 04.03.2017.
 */

public class MainApp {
    private static final int width = 400;
    private static final int height = 450;
    private static String appTitle = "XO";
    private static JFrame frame;


    public static void main(String[] args) {
        frame = new JFrame(appTitle);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        Screen.center(frame);
        choiceScreen();
        frame.setVisible(true);
    }

    public enum PlayerSide {X, O}

    private static JButton buttonO = new JButton(MainApp.PlayerSide.O.toString());
    private static JButton buttonX = new JButton(MainApp.PlayerSide.X.toString());
    private static JTextArea hello = new JTextArea("Choose your side!");

    private static void close() {
        frame.setVisible(false);
        frame.remove(buttonX);
        frame.remove(buttonO);
        frame.remove(hello);
    }

    public static void newGame(){
            Game.reset();
            Game.parseField();
            MainApp.choiceScreen();
    }

    public static void choiceScreen() {



        buttonX.addActionListener(e -> {
            Game.setPlayerSide(Game.PlayerSide.X);
            MainApp.close();
            GameScreen.gameScreen(GameScreen.PlayerSide.X);
        });

        buttonO.addActionListener(e -> {
            Game.setPlayerSide(Game.PlayerSide.O);
            MainApp.close();
            GameScreen.gameScreen(GameScreen.PlayerSide.O);
        });

        GridBagLayout choiceLayout = new GridBagLayout();
        frame.setLayout(choiceLayout);
        hello.setEditable(false);
        frame.add(hello, Screen.gridBagSetup(2, 1, GridBagConstraints.REMAINDER, 1, GridBagConstraints.BOTH));
        frame.add(buttonX, Screen.gridBagSetup(1, 2, 1, 1, GridBagConstraints.BOTH));
        frame.add(buttonO, Screen.gridBagSetup(1, 2, 1, 1, GridBagConstraints.BOTH));

        frame.setVisible(true);

    }
}

