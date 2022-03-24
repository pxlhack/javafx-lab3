package main;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WorkerBee extends Bee {
    WorkerBee() {
        super();
        Image image = new Image("/images/w_bee.png");
        WorkerBee.imageView = new ImageView(image);
    }

    @Override
    public void move() {

    }
}
