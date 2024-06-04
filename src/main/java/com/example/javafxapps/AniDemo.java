package com.example.javafxapps;

import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.VLineTo;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AniDemo extends Application {

    public static final double APP_WIDTH  = 700;
    public static final double APP_HEIGHT = 700;

    // (1) create the object (Node) to animate
    // (2) construct a Transition
    // (3) customize Transition: duration, cycle count, node, auto reverse
    //     Translate: ByX, ByY
    // (4) start animation
    private Group demoTranslate() {
        Rectangle r1 = new Rectangle(100, 100, 50, 75);

        r1.setFill(Color.BLUE);
        r1.setStroke(Color.ORANGE);

        TranslateTransition move = new TranslateTransition();
        move.setDuration(Duration.millis(300));
        move.setCycleCount(10);
        move.setNode(r1);
        move.setAutoReverse(true);
        move.setByX(50);
        move.setByY(25);

        move.play();

        Group group = new Group(r1);

        return group;
    }

    private Group demoRotate() {
        Rectangle r1 = new Rectangle(100, 100, 50, 75);

        r1.setFill(Color.ORANGE);
        r1.setStroke(Color.BLUE);

        RotateTransition move = new RotateTransition();
        move.setDuration(Duration.millis(300));
        move.setCycleCount(10);
        move.setNode(r1);
        move.setAutoReverse(true);
        move.setByAngle(90);

        move.play();

        Group group = new Group(r1);

        return group;
    }

    private Group demoScale() {
        Rectangle r1 = new Rectangle(100, 100, 50, 75);

        r1.setFill(Color.ORANGE);
        r1.setStroke(Color.BLUE);

        ScaleTransition scale = new ScaleTransition();
        scale.setDuration(Duration.millis(300));
        scale.setCycleCount(10);
        scale.setNode(r1);
        scale.setAutoReverse(true);
        scale.setByX(3);
        scale.setByY(8);

        scale.play();

        Group group = new Group(r1);

        return group;
    }

    private Group demoRotateScaleTranslate() {

        ImageView ivFrog = new ImageView(new Image("file:sfrog.png"));
        ivFrog.setX(50);
        ivFrog.setY(100);        

        TranslateTransition move = new TranslateTransition(Duration.millis(1000), ivFrog);
        move.setByX(200);
        move.setAutoReverse(true);
        move.setCycleCount(4);

        ScaleTransition    scale = new ScaleTransition(Duration.millis(2000), ivFrog);
        scale.setByX(1.5);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);

        RotateTransition  rotate = new RotateTransition(Duration.millis(500), ivFrog);
        rotate.setByAngle(360);
        rotate.setCycleCount(8);

        Group group = new Group(ivFrog);

        move.play();
        scale.play();
        rotate.play();

        return group;
    }

    // path transition 1: vertical/horizontal paths
    private Group demoPath1() {

        ImageView ivFrog = new ImageView(new Image("file:sfrog.png"));
        //ivFrog.setX(50);
        //ivFrog.setY(100);        

        Rectangle rectPath = new Rectangle(50, 100, APP_WIDTH - 100, APP_HEIGHT - 200);
        rectPath.setFill(Color.ORANGE);
        rectPath.setStroke(Color.BROWN);

        Ellipse ovalPath = new Ellipse(350, 250, APP_WIDTH/3, APP_HEIGHT/3);
        ovalPath.setFill(Color.ORANGE);
        ovalPath.setStroke(Color.BROWN);

        PathTransition path1 = new PathTransition(Duration.seconds(5), ovalPath, ivFrog);
        path1.setCycleCount(2);

        Group group = new Group(ovalPath, ivFrog);

        path1.play();

        return group;
    }

    private Group demoPath2() {
        ImageView ivFrog1 = new ImageView(new Image("file:sfrog.png"));
        ImageView ivFrog2 = new ImageView(new Image("file:sfrog.png"));

        double startX = 50;
        double startY = 50;

        double endX   = APP_WIDTH - 50;
        double gapY   = 100;

        double y      = startY + gapY;

        MoveTo  start = new MoveTo(startX, startY);
        HLineTo line1 = new HLineTo(endX); 
        VLineTo line2 = new VLineTo(y);             y += gapY;
        HLineTo line3 = new HLineTo(startX);
        VLineTo line4 = new VLineTo(y);         y += gapY;
        HLineTo line5 = new HLineTo(endX);

        // Path IS-A Shape
        Path path1 = new Path(start, line1, line2, line3, line4, line5);

        PathTransition pathT1 = new PathTransition(Duration.seconds(5), path1, ivFrog1);
        pathT1.setAutoReverse(true);
        pathT1.setCycleCount(2);

        // -----

        Text path2 = new Text(startX, y, "WELCOME!");
        path2.setFont(new Font(100));
        path2.setStroke(Color.BLUE);
        path2.setFill(Color.ORANGE);

        PathTransition pathT2 = new PathTransition(Duration.seconds(5), path2, ivFrog2);
        pathT2.setAutoReverse(true);
        pathT2.setCycleCount(2);

        Group group = new Group(path1, ivFrog1, path2, ivFrog2);

        pathT1.play();
        pathT2.play();

        return group;
    }

    // curved paths: Quadratic (QuadCurveTo), Cubic (CubicCurveTo)
    private Group demoPath3() {
        ImageView ivFrog1 = new ImageView(new Image("file:sfrog.png"));
        ImageView ivFrog2 = new ImageView(new Image("file:sfrog.png"));

        MoveTo      start1 = new MoveTo(50, 300);
                
        QuadCurveTo curve1 = new QuadCurveTo(300, 200, 550, 300); 
        Path        path1  = new Path(start1, curve1);
        //path1.setFill(Color.ORANGE);
        path1.setStroke(Color.BLUE);
        path1.setStrokeWidth(20);
        
        PathTransition pathT1 = new PathTransition(Duration.seconds(5), path1, ivFrog1);

        MoveTo         start2 = new MoveTo(50, 500);
        CubicCurveTo curve2 = new CubicCurveTo(217, 300, 384, 600, 550, 500);
        
        Path         path2  = new Path(start2, curve2);
        
        PathTransition pathT2 = new PathTransition(Duration.seconds(5), path2, ivFrog2);
        
        
        Group group = new Group(path1, ivFrog1, path2, ivFrog2);

        pathT1.play();
        pathT2.play();

        return group;
    }


    @Override
    public void start(Stage stage) {

        Group groupT = demoTranslate();
        Group groupR = demoRotate();
        Group groupS = demoScale();
        Group groupC = demoRotateScaleTranslate();
        Group groupP1 = demoPath1();
        Group groupP2 = demoPath2();
        Group groupP3 = demoPath3();
        
        Group group = groupP3;

        Scene scene = new Scene(group, APP_WIDTH, APP_HEIGHT);

        stage.setTitle("CS112 Animation Demo");
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
