package com.example.javasocket.client;


import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

@Slf4j
public class MySocketClientSendSomeMessages {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 8084;

    public static void main(String[] args) throws IOException {
        new MySocketClientSendSomeMessages().startConnection();
    }

    public void startConnection() throws IOException {
        clientSocket = new Socket(ADDRESS, PORT);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        List<String> toServer=  List.of(
                "Hello my name is Mike",
                "test 123",
                "how is the weather...",
                "stop"
        );
        for (String s : toServer) {
            log.info("Send this to server: {}", s);
            out.println(s);
            String responseFromServer = in.readLine();
            log.info("Response from server: {}", responseFromServer);
        }
        in.close();
        out.close();
        clientSocket.close();
    }
}
