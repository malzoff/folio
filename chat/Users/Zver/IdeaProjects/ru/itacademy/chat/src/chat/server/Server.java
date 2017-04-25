package chat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import chat.*;


/**
 * Created by Zver on 13.08.2016.
 */
public class Server {
    public static final String ENCODING = "UTF-8";
    public static final int    PORT     = 8888;
    private final ServerSocket     server;
    //private final List<Socket> clients;
    private final List<ClientInfo> clients;

    public Server() throws IOException {
        server = new ServerSocket(PORT);
        clients = new ArrayList<>();
    }

    public boolean isUnicName(ClientInfo client) {
        boolean isUnic        = true;
        String  currentname   = client.getClientname();
        Socket  currentsocket = client.getClientsocket();

        if (clients.size() > 1) {
            for (ClientInfo anotherClient : clients) {
                String acName   = anotherClient.getClientname();
                Socket acSocket = anotherClient.getClientsocket();
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
                client.setClientname(reader.readLine());
            }
            writer.println("Server message: Добро пожаловать, " + client.getClientname());
            writer.flush();
        }
        catch (IOException ignore) {
        }
    }

    public void sendClientID(ClientInfo client, PrintWriter writer) {
        writer.println();
    }

    public void start() throws IOException {
        while (true) {
            final ClientInfo client = new ClientInfo(server.accept());
            //final Socket client = server.accept();
            clients.add(client);
            new Thread(() -> {

                try {
                    Socket clientstream = client.getClientsocket();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientstream
                            .getInputStream(), ENCODING));
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientstream
                            .getOutputStream(), ENCODING));
                    writer.println("Server message: Введите ваш никнэйм.");
                    writer.flush();
                    client.setClientname(reader.readLine());
                    checkClientName(client, reader, writer);

                    for (ClientInfo allClient : clients) {
                        PrintWriter sender = new PrintWriter(new OutputStreamWriter(allClient.getClientsocket()
                                .getOutputStream(), ENCODING), true);
                        if (allClient != client) {
                            sender.println(client.getClientname() + " входит в чат");
                        }
                        sender.flush();
                    }

                    while (true) {
                        String s = reader.readLine();
                        System.out.println(s);
                        for (ClientInfo otherClient : clients) {
                            if ((otherClient != client) && (!otherClient.getClientname().equals(ClientInfo.NONAME))) {
                                PrintWriter sender = new PrintWriter(new OutputStreamWriter(otherClient
                                        .getClientsocket().getOutputStream(), ENCODING), true);
                                sender.println(client.getClientname() + ": " + s);
                                sender.flush();
                                System.out.println("sended from " + client.getClientname() + " to " + otherClient
                                        .getClientname());
                            }
                        }
                        System.out.println("sended ok");
                    }

                }
                catch (IOException ignore) {
                }

            }).start();
        }
    }



    public static void main(String[] args) throws IOException {
        new Server().start();
    }
}