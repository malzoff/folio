package chat.client;

import chat.server.Server;
import java.io.*;
import java.net.Socket;

/**
 * Created by Zver on 13.08.2016.
 */
public class Client {
    private static final String HOST = "127.0.0.1";

    public static void main(String[] args) throws IOException, InterruptedException {

        Socket socket = new Socket(HOST, Server.PORT);

        BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), Server
                .ENCODING));
        PrintWriter serverWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), Server
                .ENCODING));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Server.ENCODING));

        while (true) {
            while (reader.ready()) {
                String line = reader.readLine();
                serverWriter.println(line);
                serverWriter.flush();
            }

            while (serverReader.ready()) {
                System.out.println(serverReader.readLine());
                //System.out.println(" - got from server");
            }
            Thread.sleep(100);
        }
    }
}