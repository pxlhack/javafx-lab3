package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MaleBee extends Bee {
    MaleBee() {
        super();
        Image image = new Image("/images/m_bee.png");
        MaleBee.imageView = new ImageView(image);
    }

    @Override
    public void move() {

    }
}
