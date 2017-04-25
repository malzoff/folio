package chat.client;

import chat.server.Server;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

/**
 * @author colwin
 */
public class UIClient {
    private static final String HOST = "127.0.0.1";
    private final Socket         socket;
    private final BufferedReader serverReader;
    private final PrintWriter    serverWriter;
    private final BufferedReader reader;
    private boolean isNamed  = false;
    private String  ClientID = "NoNameClient";

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String clientID) {
        ClientID = clientID;
    }

    public boolean isNamed() {
        return isNamed;
    }

    public void setNamed(boolean named) {
        isNamed = named;
    }

    private UIClient(String host, int port) throws IOException, InterruptedException {
        socket = new Socket(host, port);
        serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), Server.ENCODING));
        serverWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), Server.ENCODING));
        reader = new BufferedReader(new InputStreamReader(System.in, Server.ENCODING));
    }

    public void sendMessage(String s) {
        serverWriter.println(s);
        serverWriter.flush();
    }

    public String readMessage() throws IOException {
        return serverReader.readLine();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        UIClient uiClient = new UIClient(HOST, Server.PORT);

        JFrame frame = new JFrame("Чат");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        center(frame);

        JTextArea serverTextField = new JTextArea();
        serverTextField.setEditable(false);
        serverTextField.setLineWrap(true);
        serverTextField.setAutoscrolls(true);
        serverTextField.setBackground(Color.lightGray);

        JTextField ourTextField = new JTextField();
        ourTextField.setFocusable(true);

        JButton       sendButton = new JButton("Отправить");
        GridBagLayout layout     = new GridBagLayout();
        frame.setLayout(layout);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = 1;
        constraints.gridheight = 10;
        constraints.gridx = 0;
        constraints.gridy = 0;
        frame.add(serverTextField, constraints);

        constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 0;
        constraints.gridy = 10;
        frame.add(ourTextField, constraints);

        constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 0;
        constraints.gridy = 11;
        frame.add(sendButton, constraints);

        frame.setVisible(true);

        sendButton.addActionListener((e) -> {

            //Берем из текстового поля и посылаем на сервер через uiClient

            String s = ourTextField.getText();
            if (!s.equals("")) {
                uiClient.sendMessage(s);
                ourTextField.setText(null);

                if (uiClient.isNamed()) {
                    serverTextField.append(s);
                    serverTextField.append("\n");
                }
                uiClient.setNamed(true);
            }

        });

       /* ourTextField.addActionListener((e) -> {

        });
         ourTe
        */

        new Thread(() -> {
            while (true) {
                String line = null;
                try {
                    line = uiClient.readMessage();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                //Читаем с сервера через uiClient и добавляем в текстовое поле:

                serverTextField.append(line);
                serverTextField.append("\n");
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void center(JFrame frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int       x         = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int       y         = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}
