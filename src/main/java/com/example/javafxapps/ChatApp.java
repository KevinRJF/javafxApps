package com.example.javafxapps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ChatApp extends Application {

    private static double APP_WIDTH  = 600;
    private static double APP_HEIGHT = 200;
    private static double SPACE      =  10;
    
    private BorderPane mainPane;
    private HBox       serverBox, msgBox;
    private TextArea   contentArea;
    private TextField  tfServer, tfPort, tfMsg;
    private Button     bnConnect, bnSend;
    
    // non-GUI variables
    private BufferedReader fromServer;
    private PrintWriter    toServer;
    
    public void start(Stage stage) {
        mainPane = new BorderPane();
        
        setupControls();
        setupContent();
        
        Scene scene = new Scene(mainPane, APP_WIDTH, APP_HEIGHT);
        
        stage.setScene(scene);
        stage.setTitle("CS112 Chat");
        
        stage.show();
    }

    private void setupControls() {
        serverBox = new HBox(SPACE);
        msgBox = new HBox(SPACE);
        
        serverBox.setAlignment(Pos.CENTER);
        msgBox.setAlignment(Pos.CENTER);
        
        mainPane.setTop(serverBox);
        mainPane.setBottom(msgBox);
        
        tfServer = new TextField();
        tfServer.setPrefWidth(300);
        tfPort   = new TextField();
        tfPort.setPrefWidth(75);
        
        bnConnect = new Button("Connect");
        
        bnConnect.setOnAction(e -> {
            String server = tfServer.getText();
            int    portNo = Integer.parseInt(tfPort.getText());
            
            try {
                connect(server, portNo);
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        });
        
        tfMsg     = new TextField();
        tfMsg.setPrefWidth(APP_WIDTH - 100);
        
        bnSend    = new Button("Send");
        
        bnSend.setOnAction(e -> {
            //contentArea.appendText(tfMsg.getText() + "\n");
            
            toServer.println(tfMsg.getText());
            toServer.flush();
            
            tfMsg.setText("");
        });
        
        tfMsg.setOnKeyPressed(e -> {
			if(e.getCode()== KeyCode.ENTER) {
				bnSend.fire();
			}
		});
        
        serverBox.getChildren().addAll(new Label("Server: "), tfServer, new Label("  Port: "), tfPort, bnConnect);
        
        msgBox.getChildren().addAll(tfMsg, bnSend);
    }
    
    private void setupContent() {
        contentArea = new TextArea();
        contentArea.setEditable(false);
        
        
        mainPane.setCenter(contentArea);
    }
    
    // monitor fromServer to receive messages from other users
    // and print to client console (eventuallmsg = fromServer.readLine();y add to text area)
    private class ClientThread extends Thread {
            private BufferedReader fromServer;
            
            public ClientThread(BufferedReader from) {
                fromServer = from;
                
                start();
            }
            
            public void run() {
                String msg = "";
                
                while (true) {
                    try {
                        msg = fromServer.readLine();
                        
                        // client console
                        System.out.println(msg);
                        
                        // add to text area
                        contentArea.appendText(msg + "\n");
                        
                    }
                    catch(IOException e) {
                        e.printStackTrace();
                    }
                }        
            }
        }
    
    private void connect(String server, int portNo) throws IOException {
        // establish connection to the server
        Socket clientSocket = new Socket("localhost", portNo);        
        
        // create communication channels
        // 1. Scanner to read what user types on the keyboard
        // 2. PrintWriter to send what user typed
        // 3. BufferedReader to receive what server sends
        
        Scanner keyboard = new Scanner(System.in); // System.in: standard input (keyboard)
        
        toServer   = new PrintWriter(clientSocket.getOutputStream());
        fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        String message = fromServer.readLine();
        System.out.println(message);
        contentArea.appendText(message + "\n");
        
        message = fromServer.readLine(); /// enter your name
        System.out.print(message);       // print to console for user
        System.out.flush();
        contentArea.appendText(message + "\n");
            
        ClientThread clientThread = new ClientThread(fromServer);
        
    }
    

    public static void main(String[] args) {
        launch(args);
    }
    
}

