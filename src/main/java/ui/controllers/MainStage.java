package ui.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;

public class MainStage
{

    private static MainStage mainStage;
    private AnchorPane rootPane;
    private Stage rootStage;

    private MainStage()
    {
    }


    static{
        mainStage = new MainStage();
    }

    public static MainStage getInstance()
    {
        return mainStage;
    }

    public void setStage(Stage rootStage)
    {
        this.rootStage = rootStage;
    }

    public void setRootPane() throws IOException {
        this.rootPane = FXMLLoader.load(MainStage.class.getResource("views/LoginView.fxml"));

    }

    public void setMainScene()
    {
        rootStage.setScene(new Scene(rootPane,800,600));
        rootStage.setResizable(false);
        rootStage.setTitle("Grzegorz Dudek");
        rootStage.centerOnScreen();
        rootStage.show();
    }

    public void setScene(Scene newScene)
    {
        rootStage.setScene(newScene);
    }


    public static Scene getScene(String sceneName)
    {
        try {
            AnchorPane newPane =  FXMLLoader.load(MainStage.class.getResource("views/"+sceneName));
            return new Scene(newPane,800,600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
