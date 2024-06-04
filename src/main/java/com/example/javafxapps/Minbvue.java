package com.example.javafxapps;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Minbvue extends Application {
	
	private Button mainPane;
	
	public void start(Stage stage) {
		
		mainPane = new Button("boton minvue");
		
		Scene scene = new Scene(mainPane, 600, 600);
		
		stage.setScene(scene);
		stage.setTitle("minbue");
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
