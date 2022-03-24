package main;


import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ModalWindow {
    ModalWindow() {
        this.info = new TextArea(" ");
        this.info.setEditable(false);
        this.info.setFocusTraversable(false);

        this.okButton = new Button("Ok");
        this.okButton.setLayoutX(50);
        this.okButton.setLayoutY(120);

        this.cancelButton = new Button("Cancel");
        this.cancelButton.setLayoutX(100);
        this.cancelButton.setLayoutY(120);

    }

    public Button okButton;
    public Button cancelButton;
    public TextArea info;
}
