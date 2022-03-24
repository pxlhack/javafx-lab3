package main;

import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Habitat {
    Habitat(int width, int height, int N1, int N2, double P, double K) {
        this.width = width;
        this.height = height;
        this.maleTime = N1;
        this.workerTime = N2;
        this.workerSpawn = P;
        this.maleSpawn = K;
        this.maleCount = 0;
        this.workerCount = 0;
        this.allCount = 0;
        this.imageArray = new ArrayList<>(1);
        this.beeArray = new ArrayList<>(1);


        this.dailyTimeLabel = new Label("Time: 0");
        this.dailyTimeLabel.setLayoutX(200);
        this.dailyTimeLabel.setLayoutY(50);
        this.dailyTimeLabel.setFont(new Font("Consolas", 32));
        this.dailyTimeLabel.setTextFill(Color.web("#006400"));
        this.dailyTimeLabel.setVisible(false);

        this.startButton = new Button("Start");
        this.stopButton = new Button("Stop");
        this.startButton.setLayoutX(500);
        this.startButton.setLayoutY(50);

        this.stopButton.setLayoutX(550);
        this.stopButton.setLayoutY(50);
        this.stopButton.setDisable(true);

        this.showInfoButton = new RadioButton("Show info");
        this.showInfoButton.setLayoutX(500);
        this.showInfoButton.setLayoutY(200);
        this.showInfoButton.setSelected(true);


        this.showTimeCheckBox = new CheckBox("Show time");
        this.showTimeCheckBox.setLayoutX(500);
        this.showTimeCheckBox.setLayoutY(150);


        this.hideInfoButton = new RadioButton("Hide info");
        this.hideInfoButton.setLayoutX(500);
        this.hideInfoButton.setLayoutY(230);

        this.infoToggleGroup = new ToggleGroup();
        this.hideInfoButton.setToggleGroup(this.infoToggleGroup);
        this.showInfoButton.setToggleGroup(this.infoToggleGroup);


        this.menuBar = new MenuBar();
        this.menuTimeGroup = new ToggleGroup();
        this.menuInfoGroup = new ToggleGroup();

        this.playSimMenu = new Menu("Simulation");
        this.showTimeMenu = new Menu("Time");
        this.showInfoMenu = new Menu("Info");

        this.startSimMenuItem = new MenuItem("Start simulation");
        this.stopSimMenuItem = new MenuItem("Stop simulation");
        this.stopSimMenuItem.setDisable(true);

        this.showTimeMenuItem = new CheckMenuItem("Show time");

        this.showInfoWindowMenuItem = new RadioMenuItem("Show info");
        this.showInfoWindowMenuItem.setSelected(true);
        this.hideInfoWindowMenuItem = new RadioMenuItem("Hide info");


        this.showInfoWindowMenuItem.setToggleGroup(menuInfoGroup);
        this.hideInfoWindowMenuItem.setToggleGroup(menuInfoGroup);

        this.playSimMenu.getItems().addAll(startSimMenuItem, stopSimMenuItem);
        this.showTimeMenu.getItems().add(showTimeMenuItem);
        this.showInfoMenu.getItems().addAll(showInfoWindowMenuItem, hideInfoWindowMenuItem);

        this.menuBar.getMenus().addAll(playSimMenu, showInfoMenu, showTimeMenu);


        String[] spawnChance = {"0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100"};
        this.chanceSpawnMaleBox = new ComboBox<>(FXCollections.observableArrayList(spawnChance));
        this.chanceSpawnMaleBox.setValue("40");
        this.chanceSpawnMaleBox.setLayoutX(500);
        this.chanceSpawnMaleBox.setLayoutY(350);
        this.spawnMaleText = new Text("Choose chance of male bee spawn (%)");
        this.spawnMaleText.setLayoutX(chanceSpawnMaleBox.getLayoutX());
        this.spawnMaleText.setLayoutY(chanceSpawnMaleBox.getLayoutY() - 10);

        this.chanceSpawnWorkerBox = new ComboBox<>(FXCollections.observableArrayList(spawnChance));
        this.chanceSpawnWorkerBox.setValue("40");
        this.chanceSpawnWorkerBox.setLayoutX(500);
        this.chanceSpawnWorkerBox.setLayoutY(400);

        this.spawnWorkerText = new Text("Choose chance of worker bee spawn (%)");
        this.spawnWorkerText.setLayoutX(chanceSpawnWorkerBox.getLayoutX());
        this.spawnWorkerText.setLayoutY(chanceSpawnWorkerBox.getLayoutY() - 10);


        this.inputMaleTime = new TextField("2");
        this.inputMaleTime.setLayoutX(500);
        this.inputMaleTime.setLayoutY(450);
        this.timeMaleText = new Text("Time of Male bee spawn (c)");
        this.timeMaleText.setLayoutX(inputMaleTime.getLayoutX());
        this.timeMaleText.setLayoutY(inputMaleTime.getLayoutY() - 10);


        this.inputWorkerTime = new TextField("2");
        this.inputWorkerTime.setLayoutX(500);
        this.inputWorkerTime.setLayoutY(500);
        this.timeWorkerText = new Text("Time of worker bee spawn (c)");
        this.timeWorkerText.setLayoutX(inputWorkerTime.getLayoutX());
        this.timeWorkerText.setLayoutY(inputWorkerTime.getLayoutY() - 10);

        this.upLine = new Line(0, 0, width, 0);
        this.midLine = new Line(Math.round(width / 2), 0, Math.round(width / 2), height);
        this.hLine = new Line(Math.round(width / 2), Math.round(height / 2), width, Math.round(height / 2));
    }

    public void sayErrorTime() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("not number");
        alert.show();
    }


    public void update(int d_t) {

        if (d_t % this.workerTime == 0 && Math.random() <= this.workerSpawn) {

            this.beeArray.add(new WorkerBee());
            workerCount++;
            allCount++;

            ImageView workerImage = WorkerBee.imageView;
            setImage(workerImage);
        }

        double _K = 0;
        if (allCount != 0) _K = (double) maleCount / allCount;
        if (d_t % this.maleTime == 0 && _K < this.maleSpawn) {
            this.beeArray.add(new MaleBee());
            maleCount++;
            allCount++;
            ImageView maleImage = MaleBee.imageView;
            setImage(maleImage);
        }
    }

    private void setImage(ImageView imageView) {
        imageView.setLayoutX((int) (Math.random() * (width / 2 - 100)) + 50);
        imageView.setLayoutY((int) (Math.random() * (height - 100)) + 50);

        this.imageArray.add(imageView);
        group.getChildren().add(imageView);
        scene.setRoot(group);
    }

    public int width, height;
    public int maleTime, workerTime;
    public double workerSpawn, maleSpawn;
    public int maleCount,
            workerCount, allCount;
    public ArrayList<Bee> beeArray;
    public ArrayList<ImageView> imageArray;

    public Scene scene;
    public Group group;

    public Label dailyTimeLabel;

    public Button startButton, stopButton;

    public CheckBox showTimeCheckBox;

    public ToggleGroup infoToggleGroup;
    public RadioButton showInfoButton;
    public RadioButton hideInfoButton;

    public MenuBar menuBar;

    public Menu playSimMenu;
    public Menu showTimeMenu;
    public Menu showInfoMenu;

    public MenuItem startSimMenuItem, stopSimMenuItem;
    public CheckMenuItem showTimeMenuItem;
    public RadioMenuItem showInfoWindowMenuItem, hideInfoWindowMenuItem;

    public ToggleGroup menuTimeGroup;
    public ToggleGroup menuInfoGroup;

    public ComboBox<String> chanceSpawnMaleBox;
    public ComboBox<String> chanceSpawnWorkerBox;

    public Text spawnMaleText;
    public Text spawnWorkerText;

    public Text timeMaleText;
    public Text timeWorkerText;
    public TextField inputMaleTime;
    public TextField inputWorkerTime;

    public Line upLine;
    public Line midLine;
    public Line hLine;
}