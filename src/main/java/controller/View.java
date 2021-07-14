package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;

public final class View
{
    private static View view;
    private AnchorPane rootPane;
    private Stage rootStage;


    public static View getInstance()
    {
        if(view==null)
        {
            view = new View();
        }
        return view;
    }

    public void setStage(Stage rootStage)
    {
        this.rootStage = rootStage;
    }

    public void setRootPane() throws IOException {

        this.rootPane = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
    }

    public void setMainScene()
    {
        rootStage.setScene(new Scene(rootPane,800,600));
        rootStage.setResizable(false);
        rootStage.setTitle("Grzegorz Dudek");
        rootStage.centerOnScreen();
        rootStage.show();
    }

    public void setView(Scene newScene)
    {
        rootStage.setScene(newScene);
    }


    public static Scene getView(String sceneName)
    {
        try {
            AnchorPane newPane =  FXMLLoader.load(View.class.getResource("/view/"+sceneName));
            return new Scene(newPane,800,600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
