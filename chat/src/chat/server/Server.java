package chat.server;

import chat.ClientInfo;
import chat.client.UIClient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/**
 * 13.08.2016.
 */
public class Server {
    public static final String PONG = "pong from server";
    public static final String ACCEPTED = "Name accepted";
    public static final String ENCODING = "UTF-8";
    public static final int    PORT     = 8888;
    private final ServerSocket     server;
    private final List<ClientInfo> clients;

    public Server() throws IOException {
        server = new ServerSocket(PORT);
        clients = new ArrayList<>();
    }

    public boolean isUnicName(ClientInfo client) {
        boolean isUnic        = true;
        String currentname = client.getClientName();
        Socket currentsocket = client.getClientSocket();

        if (clients.size() > 1) {
            for (ClientInfo anotherClient : clients) {
                String acName = anotherClient.getClientName();
                Socket acSocket = anotherClient.getClientSocket();
                if (!acSocket.equals(currentsocket) && (acName.equals(currentname) && !acName.equals(ClientInfo
                        .NONAME))) {
                    isUnic = false;
                }
            }
        }
        return isUnic;
    }

    public void checkClientName(ClientInfo client, BufferedReader reader, PrintWriter writer) throws IOException {
        try {
            while (!isUnicName(client)) {
                writer.println("Server message: Пользователь с таким именем уже существует.");
                writer.println("Server message: Введите ваш никнэйм.");
                writer.flush();
                client.setClientName(reader.readLine());
            }
            writer.println(ACCEPTED);
            writer.println("Server message: Добро пожаловать, " + client.getClientName());
            writer.flush();
        }
        catch (IOException ignore) {
        }
    }

    public void sendClientID(ClientInfo client, PrintWriter writer) {
        writer.println();
    }

    public static void pong(PrintWriter writer) {
        writer.println(PONG);
        writer.flush();
    }

    public void start() throws IOException {
        while (true) {
            final ClientInfo client = new ClientInfo(server.accept());
            //final Socket client = server.accept();
            clients.add(client);
            new Thread(() -> {

                try {
                    Socket clientstream = client.getClientSocket();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientstream
                            .getInputStream(), ENCODING));
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientstream
                            .getOutputStream(), ENCODING));
                    writer.println("Server message: Введите ваш никнэйм.");
                    writer.flush();
                    client.setClientName(reader.readLine());
                    Server.this.checkClientName(client, reader, writer);

                    for (ClientInfo allClient : clients) {
                        PrintWriter sender = new PrintWriter(new OutputStreamWriter(allClient.getClientSocket()
                                .getOutputStream(), ENCODING), true);
                        if (allClient != client) {
                            sender.println(client.getClientName() + " входит в чат");
                        }
                        sender.flush();
                    }

                    while (true) {
                        String s = reader.readLine();
                        System.out.println(s);
                        if (!s.equals(UIClient.PING)) {
                            for (ClientInfo otherClient : clients) {
                                if ((otherClient != client) && (!otherClient.getClientName().equals(ClientInfo.NONAME))) {

                                    PrintWriter sender = new PrintWriter(new OutputStreamWriter(otherClient
                                            .getClientSocket().getOutputStream(), ENCODING), true);
                                    sender.println(client.getClientName() + ": " + s);
                                    sender.flush();
                                    System.out.println("sended from " + client.getClientName() + " to " + otherClient
                                            .getClientName());
                                }
                            }
                        }
                        System.out.println("sended ok");
                        pong(writer);
                    }

                } catch (IOException ignore) {
                }

            }).start();
        }
    }


    public static void main(String[] args) throws IOException {
        new Server().start();
    }
}
