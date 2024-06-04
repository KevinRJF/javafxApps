package com.example.javafxapps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

    private static final int portNo = 2024;
    private static ArrayList<PrintWriter> toClients = new ArrayList<>();    
    
    private static class ServerThread extends Thread {
        private String clientName;
        private BufferedReader fromClient;
        private PrintWriter    toClient;
        
        public ServerThread(String name, BufferedReader from, PrintWriter to) {
            clientName = name;
            fromClient = from;
            toClient   = to;
            
            start(); // NOT run()
        }
        
        @Override
        public void run() {
            boolean isOver = false;
            
            String msg = "";
            
            System.out.println("Listening to " + clientName);
            
            while (!isOver) {
                try {
                    msg = clientName + ": " + fromClient.readLine();
                    
                    // print to server console
                    System.out.println(msg);
                    
                    // broadcast all clients
                    broadcast(msg);
                    
                    isOver = msg.endsWith("exit") || msg.endsWith("Exit");
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
            
        }
        
    }
    
    
    public static void broadcast(String msg) {
        for (PrintWriter pw : toClients) {
            pw.println(msg);
            pw.flush();
        }
    }
    
    public static void main(String[] args) throws IOException {
        
        ServerSocket serverSocket = new ServerSocket(portNo);
        
        System.out.println("ChatServer is running");
        
        while (true) {
            
            // accept connection request from a client
            Socket clientSocket = serverSocket.accept(); // 
            
            // establish communication channels
            PrintWriter    toClient   = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            toClients.add(toClient);
            
            toClient.println("Welcome to Chat Server");
            toClient.flush();   // force the server to send the message to the client
            
            toClient.println("Enter your name: ");
            toClient.flush();
            
            String clientName = fromClient.readLine();
            String msg = clientName + " connected";
            System.out.println(msg);
            
            ServerThread dedicatedServer = new ServerThread(clientName, fromClient, toClient);
            
            // broadcast: this message
            broadcast(msg);
            
            
//            fromClient.close();
//            toClient.close();
//            
//            clientSocket.close();
            
        }

    }

}

