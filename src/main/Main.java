package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {
    private final int N1 = 2;
    private final int N2 = 2;
    private final double P = 0.4;
    private final double K = 0.4;
    private final int width = 900;
    private final int height = 600;

    private Habitat h = new Habitat(width, height, N2, N1, P, K);
    private Timer mTimer = new Timer();
    private int dailyTime = 0;
    private boolean isWork = true;
    private boolean infoFlag = true;


    private void startSpawn() {
        h.dailyTimeLabel.setText("Time: " + dailyTime);
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    h.maleSpawn = Double.parseDouble(h.chanceSpawnMaleBox.getValue()) / 100;
                    h.workerSpawn = Double.parseDouble(h.chanceSpawnWorkerBox.getValue()) / 100;

                    try {
                        h.workerTime = Integer.parseInt(h.inputWorkerTime.getText());

                        if (h.workerTime <= 0) throw new Error();
                    } catch (Throwable t) {
                        h.sayErrorTime();
                        h.workerTime = 2;
                        h.inputWorkerTime.setText("2");
                    }

                    try {
                        h.maleTime = Integer.parseInt(h.inputMaleTime.getText());

                        if (h.maleTime <= 0) throw new Error();
                    } catch (Throwable t) {
                        h.sayErrorTime();
                        h.maleTime = 2;
                        h.inputMaleTime.setText("2");
                    }

                    h.update(dailyTime);
                    dailyTime++;
                    h.dailyTimeLabel.setText("Time: " + dailyTime);
                });
            }
        }, 1000, 1000);
    }

    private void stopSpawn(Stage primaryStage) {
        ModalWindow mw = new ModalWindow();
        Stage newWindow = new Stage();

        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(primaryStage);

        String infoString = "Time simulate: " + dailyTime;
        infoString += "\nCount worker bees: " + h.workerCount;
        infoString += "\nCount worker bees: " + h.maleCount;

        mw.info.setText(infoString);

        mTimer.cancel();
        mTimer = new Timer();


        mw.okButton.setOnAction(actionEvent -> {
            EndSim();

            newWindow.close();
        });

        mw.cancelButton.setOnAction(actionEvent -> {


            startSpawn();
            newWindow.close();

        });

        Group secondaryLayout = new Group(mw.info, mw.okButton, mw.cancelButton);
        Scene secondScene = new Scene(secondaryLayout, 300, 200);


        newWindow.setTitle("Stop simulation");
        newWindow.setScene(secondScene);

        newWindow.setX(primaryStage.getX() + 200);
        newWindow.setY(primaryStage.getY() + 100);

        if (infoFlag) {
            newWindow.show();
        } else {
            EndSim();
        }
    }

    private void EndSim() {
        h.group.getChildren().removeAll(h.imageArray);
        h.imageArray.clear();
        h.beeArray.clear();
        h.maleCount = 0;
        h.workerCount = 0;
        h.allCount = 0;
        dailyTime = 0;

        h.startButton.setDisable(false);
        h.stopButton.setDisable(!h.startButton.isDisable());
        h.startSimMenuItem.setDisable(h.startButton.isDisable());
        h.stopSimMenuItem.setDisable(!h.startButton.isDisable());

        isWork = !isWork;
    }

    private void setMenu() {
        h.startSimMenuItem.setOnAction(event -> h.startButton.fire());
        h.stopSimMenuItem.setOnAction(event -> h.stopButton.fire());

        h.showTimeMenuItem.setOnAction(event -> h.showTimeCheckBox.fire());

        h.showInfoWindowMenuItem.setOnAction(event -> h.showInfoButton.fire());
        h.hideInfoWindowMenuItem.setOnAction(event -> h.hideInfoButton.fire());
    }

    private void setTimeCheckBox() {
        h.showTimeCheckBox.setOnAction(event -> h.dailyTimeLabel.setVisible(h.showTimeCheckBox.isSelected()));
    }

    private void setInfoButton() {
        h.hideInfoButton.setOnAction(event -> infoFlag = false);
        h.showInfoButton.setOnAction(event -> infoFlag = true);
    }

    private void setProcessButton(Stage primaryStage) {
        h.startButton.setOnAction(event -> {
            if (isWork) {
                startSpawn();
                isWork = !isWork;
            }
            h.startButton.setDisable(true);
            h.stopButton.setDisable(false);
            h.startSimMenuItem.setDisable(true);
            h.stopSimMenuItem.setDisable(false);
        });

        h.stopButton.setOnAction(event -> {
            if (!isWork) stopSpawn(primaryStage);
        });
    }

    private void setKeyboard(Scene scene) {
        scene.setOnKeyTyped(keyEvent -> {
            switch (keyEvent.getCharacter().toLowerCase(Locale.ROOT)) {
                case "b", "и" -> h.startButton.fire();
                case "e", "у" -> h.stopButton.fire();
                case "t", "е" -> h.showTimeCheckBox.fire();
                default -> System.out.println("Error " + keyEvent.getCharacter());
            }
        });
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group(h.dailyTimeLabel, h.menuBar,
                h.chanceSpawnMaleBox, h.chanceSpawnWorkerBox,
                h.spawnMaleText, h.spawnWorkerText,
                h.inputMaleTime, h.inputWorkerTime,
                h.timeWorkerText, h.timeMaleText,
                h.startButton, h.stopButton,
                h.showTimeCheckBox,
                h.showInfoButton, h.hideInfoButton,
                h.upLine, h.midLine, h.hLine);
        Scene scene = new Scene(root, h.width, h.height);


        setProcessButton(primaryStage);
        setTimeCheckBox();
        setInfoButton();
        setMenu();


        h.scene = scene;
        h.group = root;
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bee simulation");
        primaryStage.getIcons().add(new Image("/images/icon.png"));

        setKeyboard(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(we -> System.exit(0));

    }

    public static void main(String[] args) {
        launch(args);
    }
}
