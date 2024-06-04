package com.example.javafxapps;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private BufferedReader serverIn;
    private PrintWriter    serverOut;

    private class MonitorServer extends Thread {

        public void run() {

            String msg;

            while (true) {
                try {
                    msg = serverIn.readLine();

                    System.out.println(msg);
                    // textArea.appendText(msg);
                }
                catch(IOException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    public static void main(String[] args) throws IOException {
        String server = "localhost"; // (current machine)
        int    portNo = 2024;

        ChatClient client = new ChatClient();
        client.connect(server, portNo);

    }

    public void connect(String server, int portNo) throws IOException {


        Socket clientSocket = new Socket(server, portNo);
        serverOut = new PrintWriter(clientSocket.getOutputStream());
        serverIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        // need 3 communication channels
        // 1. Scanner using System.in (keyboard) to read what the user types on the keyboard
        // 2. PrintWriter to send what user typed to the server
        // 3. BufferedReader to receive what server sends

        Scanner keyboard = new Scanner(System.in);

        PrintWriter serverOut = new PrintWriter(clientSocket.getOutputStream());
        BufferedReader serverIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String msg = serverIn.readLine();
        System.out.println(msg);

        msg = serverIn.readLine(); // prompt for name
        System.out.print(msg + " ");

        String name = keyboard.nextLine();
        serverOut.println(name);
        serverOut.flush();

        // activate MonitorServer
        MonitorServer monitorServer = new MonitorServer();
        monitorServer.start();


        boolean isOver = false;
        while (!isOver) {
            System.out.print("You: ");
            msg = keyboard.nextLine();

            serverOut.println(msg);
            serverOut.flush();

            isOver = msg.equalsIgnoreCase("bye");
        }

        serverIn.close();
        serverOut.close();
        keyboard.close();

        clientSocket.close();
    }


}

