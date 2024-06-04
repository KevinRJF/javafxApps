package com.example.javafxapps;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ButtonDemo extends Application {

	/** constants */
	private static final int APP_WIDTH = 600;
	private static final int APP_HEIGHT = 400;

	/** GUI variables */
	private BorderPane mainPane;
	private Button bnCenter, bnNorth, bnSouth, bnWest, bnEast;

	/** non-GUI/functional variables */
	private int nClicks;

	@Override
	public void start(Stage stage) {

		mainPane = new BorderPane();

		setupButtons();

		Scene scene = new Scene(mainPane, APP_WIDTH, APP_HEIGHT);

		stage.setScene(scene);
		stage.setTitle("Button Demo");

		stage.show(); // until this, no sizes are set
	}

	private void setupButtons() {

		// to add graphic to a button
		// 1. make sure the image file is accessible (project folder)
		// 2. create an Image object from the file (in step 1)
		// 3. create an ImageView object from the Image object (in step 2)
		// 4. set the graphic on button/label using setGraphic(ImageView object)

		Image imgFrog = new Image("file:sfrog.png");
		ImageView ivFrog = new ImageView(imgFrog);

		// bnCenter = new Button("Click Me: center");
		bnCenter = new Button();
		bnCenter.setGraphic(ivFrog);

		bnNorth = new Button("North");
		bnSouth = new Button("South");
		bnEast = new Button("East");
		bnWest = new Button("West");

		bnNorth.setPrefWidth(APP_WIDTH);
		bnSouth.setPrefWidth(APP_WIDTH);

		// we expect -1 because JavaFX did not automatically size the buttons yet
		System.out.println(bnNorth.getHeight() + " " + bnNorth.getMaxHeight() + " " + bnNorth.getMinHeight() + " "
				+ bnNorth.getPrefHeight());

		// event handler: code to respond to user's action on this button
		// full lambda expression: creates a method on the fly (no name)
		bnCenter.setOnAction(e -> {
			++nClicks;
			System.out.println("Center clicked " + nClicks + " times");

			// change this button's label to reflect click count
			bnCenter.setText(nClicks + " clicks");
		});

		bnNorth.setOnAction(e -> {
			nClicks = 0;
			System.out.println("North clicked: reset click count");
		});

		bnSouth.setOnAction(e -> {
			System.out.println("South clicked");
		});

		bnEast.setOnAction(e -> {
			System.out.println("East clicked");
		});

		bnWest.setOnAction(e -> {
			System.out.println("West clicked");
		});

		// must add to container (mainPane)
		mainPane.setCenter(bnCenter);

		mainPane.setTop(bnNorth);
		mainPane.setBottom(bnSouth);
		mainPane.setLeft(bnWest);
		mainPane.setRight(bnEast);

//        Region bnNort = null;

//		bnNort.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
//		
//		private static final String BLUE = "-fx-background-color: blue";
//		private static final String RED = "-fx-background-color: red";
//		
//		bnSouth.setStyle(BLUE);

	}

	public static void main(String[] args) {
		launch(args);
	}

}
