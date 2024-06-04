package com.example.javafxapps;

import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WhackAFrog extends Application {

    /** constants: final static */
    private static final int N_ROWS         =   5;
    private static final int N_COLS         =   5;
    private static final int BN_SIZE        =  70;
    private static final int CONTROL_HEIGHT =  30;
    private static final int APP_WIDTH      = BN_SIZE * (N_COLS + 2);
    private static final int APP_HEIGHT     = BN_SIZE * N_ROWS + CONTROL_HEIGHT;
    private static final String IMG_FILE    = "file:sfrog.png";
    private static final int SPACE          = 10;

    private static final int GAME_DURATION  = 30;  // 15 seconds per game
    private static final int DEFAULT_INTERVAL = 1;
    private static final int SECOND           = 1;
    private static final double EASY_RATE = 1;
    private static final double HARD_RATE = 2;
    private static final double DEFAULT_RATE = 0.5;


    /** GUI variables */
    private BorderPane   mainPane;
    private GridPane     gameBoard;
    private HBox         controlPanel;
    private Button       bnStart, bnStop;
    private Button[][]   buttons;
    private ImageView    ivFrog;
    private Timeline     timerCountDown, timerGameSpeed;
    private Label        lbTimeLeft, lbScore;
    private ComboBox<String> dropLevel;
    private Slider 		slider;

    /** non-GUI (functional) */
    private static Random random = new Random();

    private int timeLeft, score;
    private double interval;
    private Button bnFrog;    // not a new Button, but it points to the button that has the Frog

    @Override
    public void start(Stage mainStage) {

        mainPane = new BorderPane();

        setupControls();
        setupBoard();
        loadImage();
        setupTimers();

        Scene scene = new Scene(mainPane, APP_WIDTH, APP_HEIGHT);
        mainStage.setScene(scene);
        //mainStage.setTitle("CS112 Whack-A-Frog");

        mainStage.show();
    }

    private void setupControls() {
        controlPanel = new HBox(SPACE);

        bnStart = new Button("Start");
        bnStop  = new Button("Stop");

        bnStop.setDisable(true);

        // add event handlers to the buttons (count down timers)
        bnStart.setOnAction(e -> {
            // initialize game stats (score, timeLeft)
            score = 0;
            timeLeft = GAME_DURATION;

            // start the timers
            timerCountDown.play();
            timerGameSpeed.play();

            // enable the game buttons (enable the buttons in the board)
            disableBoard(false);

            // disable bnStart, enable bnStop
            bnStart.setDisable(true);
            bnStop.setDisable(false);
        });

        bnStop.setOnAction(e -> {
            // stop the timers
            timerCountDown.stop();
            timerGameSpeed.stop();

            // enable bnStart, disable bnStop
            bnStart.setDisable(false);
            bnStop.setDisable(true);

            // disable all game buttons
            disableBoard(true);
        });

        // create a slider for level control
        interval = DEFAULT_INTERVAL;

        dropLevel = new ComboBox<>();
        dropLevel.getItems().addAll("Easy", "Medium", "Hard");

        //01. default level

        dropLevel.setValue("Medium");

        //02. how to update time

        dropLevel.setOnAction(e -> {
            String level = dropLevel.getValue();

            if(level.equals(EASY_RATE)) {
                timerGameSpeed.setRate(0.5);
            }
            else if(level.equals(HARD_RATE)) {
                timerGameSpeed.setRate(2);
            } else {
                timerGameSpeed.setRate(DEFAULT_RATE);
            }
        });

        slider = new Slider(EASY_RATE, HARD_RATE, DEFAULT_RATE);


        // create labels for time left, score
        lbTimeLeft = new Label("" + timeLeft);
        lbScore    = new Label("" + score);

        controlPanel.getChildren().addAll(bnStart, bnStop,
//                new Label("level: "), dropLevel,
                new Label("Level: "), slider,
                new Label("time left: "), lbTimeLeft,
                new Label("score: "), lbScore);

        mainPane.setTop(controlPanel);
    }

    private void loadImage() {
        ivFrog = new ImageView(new Image(IMG_FILE));
    }

    private void setupBoard() {
        gameBoard = new GridPane();

        buttons = new Button[N_ROWS][N_COLS];

        for (int r = 0; r < N_ROWS; ++r) {
            for (int c = 0; c < N_COLS; ++c) {
                Button b = new Button();
                b.setDisable(true);
                b.setPrefSize(BN_SIZE, BN_SIZE);

                // main functionalities
                // (1) allow user to click to whack a Frog
                // (2) update game stats
                b.setOnAction(e -> {   // e: the click event on this button

                    // if user hit the Frog (this Button has the Frog)
                    // e.getSource() returns the object (button) that user just clicked
                    if (e.getSource() == bnFrog) {
                        System.out.println("found the frog");

                        ++score;
                        lbScore.setText("" + score);
                    }
                    // otherwise
                    else {
                        System.out.println("missed the frog");
                    }

                });

                buttons[r][c] = b;

                gameBoard.add(b, c, r);
            }
        }

        mainPane.setCenter(gameBoard);
    }

    // enable or disable all game board buttons
    private void disableBoard(boolean status) { // true: disable, false: enable
        for (int r = 0; r < N_ROWS; ++r) {
            for (int c = 0; c < N_COLS; ++c) {
                buttons[r][c].setDisable(status);
            }
        }
    }

    private void setupTimers() {

        KeyFrame kfCountDown = new KeyFrame(Duration.seconds(SECOND), e -> {
            --timeLeft;

            lbTimeLeft.setText("" + timeLeft);

            if (timeLeft == 0) {
                // game over
                timerCountDown.stop();
                timerGameSpeed.stop();

                // enable bnStart, disable bnStop
                bnStart.setDisable(false);
                bnStop.setDisable(true);

                // disable all game buttons
                disableBoard(true);

            }

        });

        KeyFrame kfGameSpeed = new KeyFrame(Duration.seconds(interval), e -> {
            jump();
        });

        // instantiate timelines and set cycle count to indefinite (for now)
        timerCountDown = new Timeline(kfCountDown);
        timerGameSpeed = new Timeline(kfGameSpeed);

        timerGameSpeed.rateProperty().bind(slider.valueProperty());

        timerCountDown.setCycleCount(Animation.INDEFINITE);
        timerGameSpeed.setCycleCount(Animation.INDEFINITE);

    }

    private void jump() {
        // generate two random integers for row, column
        int row = random.nextInt(N_ROWS); // 0 ~ N_ROWS - 1
        int col = random.nextInt(N_COLS);

        bnFrog = buttons[row][col];

        // put the frog image on the button at (row, column)
        bnFrog.setGraphic(ivFrog);
    }


    public static void main(String[] args) {
        launch(args);
    }

}

