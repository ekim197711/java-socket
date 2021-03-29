package com.example.javasocket.client;


import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

@Slf4j
public class MySocketClient {
    private Socket clientSocket;
    private PrintWriter outputToServer;
    private BufferedReader inputFromServer;
    static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 8084;

    public static void main(String[] args) throws IOException {
        new MySocketClient().startConnection();
    }

    public void startConnection() throws IOException {
        clientSocket = new Socket(ADDRESS, PORT);
        outputToServer = new PrintWriter(clientSocket.getOutputStream(), true);
        inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        Scanner sc = new Scanner(System.in);
        String toServer=  "";
        while (!"stop".equalsIgnoreCase(toServer)){
            toServer = sc.nextLine();
            log.info("Send this to server: {}", toServer);
            outputToServer.println(toServer);
            String responseFromServer = inputFromServer.readLine();
            log.info("Response from server: {}", responseFromServer);
        }
        inputFromServer.close();
        outputToServer.close();
        clientSocket.close();
    }
}
