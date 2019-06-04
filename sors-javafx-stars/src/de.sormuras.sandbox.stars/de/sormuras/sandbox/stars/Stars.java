package de.sormuras.sandbox.stars;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Stars extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    stage.setTitle("Stars");

    var root = new Group();
    var scene = new Scene(root);
    stage.setScene(scene);

    var canvas = new Canvas(512, 512);
    root.getChildren().add(canvas);

    var gc = canvas.getGraphicsContext2D();

    var earth = new Image("file:earth.png");
    var sun = new Image("file:sun.png");
    var space = new Image("file:space.png");

    var timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);

    var timeStart = System.currentTimeMillis();

    var keyFrame =
        new KeyFrame(
            Duration.seconds(0.017), // 60 FPS
            new EventHandler<ActionEvent>() {
              public void handle(ActionEvent ae) {
                var t = (System.currentTimeMillis() - timeStart) / 1000.0;
                var x = 232 + 128 * Math.cos(t);
                var y = 232 + 128 * Math.sin(t);

                gc.clearRect(0, 0, 512, 512);

                gc.drawImage(space, 0, 0);
                gc.drawImage(sun, x - 150, y - 150);
                gc.drawImage(earth, x, y);
              }
            });

    gc.drawImage(space, 0, 0);
    timeline.getKeyFrames().add(keyFrame);
    timeline.play();

    stage.show();
  }
}
