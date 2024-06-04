package com.example.javafxapps;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;	
import javafx.util.Duration;

public class GalleryApp extends Application {

    /** constants */
    private static final int APP_WIDTH  = 640;
    private static final int APP_HEIGHT = 640;

    private static final int SPACE      =   5;
    private static final double INTERVAL   =  0.5;

    private static final int N_IMAGES   =   20;

    /** GUI variables */
    private BorderPane mainPane;
    private Button     bnNext, bnPrev;
    private Label      lbImage;

    // GUI: control panel
    private HBox       controlPanel;
    private Button     bnPlay, bnStop;
    private Label      lbCurrent, lbTotal;

    // GUI: animation
    private Timeline   timer;
    private KeyFrame   keyFrame;

    /** non-GUI (functional) variables */
    private int currImage = 0;
    private ImageView[] images;


    @Override
    public void start(Stage mainStage) {

        mainPane = new BorderPane();

        setupButtons();
        setupControls();  // bnPlay, bnStop, status bar
        setupImages();    // Label + ImageViews
        setupTimeline();  // animation

        Scene scene = new Scene(mainPane, APP_WIDTH, APP_HEIGHT);

        mainStage.setScene(scene);
        mainStage.setTitle("CS112 Gallery");

        mainStage.show();
    }

    private void setupButtons() {
        // instantiate buttons
        bnNext = new Button(">>");
        bnPrev = new Button("<<");

        // set button height to the entire application
        bnNext.setPrefHeight(APP_HEIGHT);
        bnPrev.setPrefHeight(APP_HEIGHT);

        // functionality: add event handler (listener)
        bnNext.setOnAction(e -> {
            // increment currImage index
            ++currImage;

            // error check: wrap-around if we reached the last image
            if (currImage >= N_IMAGES) {
                currImage = 0;
            }

            updateImage();
        });

        bnPrev.setOnAction(e  -> {
            // decrement
            --currImage;

            // error check
            if (currImage < 0) {
                currImage = N_IMAGES -1;                
            }

            updateImage();
        });


        // add the buttons to mainPane
        mainPane.setRight(bnNext);
        mainPane.setLeft(bnPrev);
    }

    private void updateImage() {
        lbImage.setGraphic(images[currImage]);
        lbCurrent.setText("" + (currImage + 1));
    }

    private void setupTimeline() {
        // parameter 1: duration
        // parameter 2: action listener -- identical to bnNext action listener
        keyFrame = new KeyFrame(Duration.seconds(INTERVAL), e -> {
            // increment currImage index
            ++currImage;

            // error check: wrap-around if we reached the last image
            if (currImage >= N_IMAGES) {
                currImage = 0;
            }

            updateImage();
        });
        
        timer = new Timeline(keyFrame);
        timer.setCycleCount(Animation.INDEFINITE);
        
    }

    private void setupControls() {
        controlPanel = new HBox(SPACE);

        bnPlay = new Button("Play");
        bnStop = new Button("Stop");

        bnPlay.setOnAction(e -> {
            timer.play();
        });

        bnStop.setOnAction(e -> {
            timer.stop();
        });

        // event handler for the buttons

        lbCurrent = new Label("" + (currImage + 1));
        lbTotal   = new Label(" / " + N_IMAGES);

        // add all of these buttons/labels to controlPanel ==> FIX LATER
        controlPanel.getChildren().addAll(bnPlay, bnStop, lbCurrent, lbTotal);

        mainPane.setTop(controlPanel);
    }

    private void setupImages() {
        // instantiate (create) image view array
        images = new ImageView[N_IMAGES];

        // in a loop, load image file one at a time, and add to the array
        // f1.png, f2.png, ... , f30.png
        for (int i = 0; i < N_IMAGES; ++i) {
            String    fn  = "file:f" + (i+1) + ".png";    // "file:"    local file
            Image     img = new Image(fn);
            ImageView iv  = new ImageView(img);

            // resize iv if necessary/desired

            images[i] = iv;
        }

        // create a label
        lbImage = new Label();

        // add current image (image 0) to the label
        lbImage.setGraphic(images[currImage]);

        // add label to mainPane
        mainPane.setCenter(lbImage);
    }

}

