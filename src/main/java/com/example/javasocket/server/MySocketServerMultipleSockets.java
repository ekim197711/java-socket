package com.example.javasocket.server;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


@Slf4j
public class MySocketServerMultipleSockets {
    private static ServerSocket serverSocket;
    private static final int PORT = 8084;
    private Socket clientSocket;
    private PrintWriter outputToClient;
    private BufferedReader inputFromClient;

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(PORT);
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                try {
                    new MySocketServerMultipleSockets().startServer(serverSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void startServer(ServerSocket serverSocket) throws IOException {
        log.info("Start Socket Server BEGIN");

        clientSocket = serverSocket.accept();
        outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);
        inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String message = "";
        log.info("Connection has been established");
        while (!"stop".equalsIgnoreCase(message)) {
            message = inputFromClient.readLine();
            log.info("The client sent this message {}", message);
            if (message == null)
                continue;
            if (message.toLowerCase().contains("weather")) {
                outputToClient.println("The weather tomorrow will be sunny and maybe mixed!");
            } else if (message.toLowerCase().contains("hello ") || message.toLowerCase().contains("hi ")) {
                outputToClient.println("Hiiii I'm a server. Have a nice day");
            } else outputToClient.println("Hmmmmm... you sent me the message: [" + message + "]");
        }
        inputFromClient.close();
        outputToClient.close();
        clientSocket.close();
        serverSocket.close();
        log.info("Start Socket Server END");
    }
}
