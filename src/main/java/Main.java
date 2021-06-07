import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ui.views.MainStage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        MainStage mainStage = MainStage.getInstance();
        mainStage.setStage(primaryStage);
        mainStage.setRootPane();
        mainStage.setMainScene();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
