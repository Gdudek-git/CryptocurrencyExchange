package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.ui.stage.MainStage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        MainStage mainStage = MainStage.getInstance();
        mainStage.setStage(primaryStage);
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/main/java/ui/stage/LoginView.fxml"));
        mainStage.setRootPane(rootPane);
        mainStage.setMainScene();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
