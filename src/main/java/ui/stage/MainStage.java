package main.java.ui.stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.Main;

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

    public void setRootPane(AnchorPane rootPane)
    {
        this.rootPane = rootPane;
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
            AnchorPane newPane =  FXMLLoader.load(MainStage.class.getResource("/main/java/ui/stage/"+sceneName));
            return new Scene(newPane,800,600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
