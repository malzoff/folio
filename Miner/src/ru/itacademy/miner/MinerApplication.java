package ru.itacademy.miner;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 */
class MinerApplication {
  private static int FIELD_HEIGHT_BUTTONS = 16;
  private static int FIELD_WIDTH_BUTTONS = 16;

  private static int BUTTON_HEIGHT_PX = 20;
  private static int BUTTON_WIDTH_PX = 20;

  public static void main(String[] args) {
    final GameField field = new GameField(FIELD_WIDTH_BUTTONS, FIELD_HEIGHT_BUTTONS);

    JFrame frame = new JFrame("Сапёр");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(FIELD_WIDTH_BUTTONS * BUTTON_WIDTH_PX + 16, FIELD_HEIGHT_BUTTONS * BUTTON_HEIGHT_PX + 38);
    frame.setLayout(new GridLayout(FIELD_HEIGHT_BUTTONS, FIELD_WIDTH_BUTTONS));
    center(frame);


    for (int y = 0; y < FIELD_HEIGHT_BUTTONS; y++) {
      for (int x = 0; x < FIELD_WIDTH_BUTTONS; x++) {
        final JButton button = new JButton();
        frame.add(button);
        field.bind(x,y,button);
        final int buttonX = x;
        final int buttonY = y;
        button.addActionListener(e ->
                field.onClick(buttonX, buttonY, button));
      }
    }

    frame.setVisible(true);
    // frame.doLayout();
  }

  private static void center(JFrame frame) {
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
    frame.setLocation(x, y);
  }
}
