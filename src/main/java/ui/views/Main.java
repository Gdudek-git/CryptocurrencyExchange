package ui.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        MainStage mainStage = MainStage.getInstance();
        mainStage.setStage(primaryStage);


        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        mainStage.setRootPane(rootPane);
        mainStage.setMainScene();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
