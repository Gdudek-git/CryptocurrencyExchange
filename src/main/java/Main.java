
import javafx.application.Application;
import javafx.stage.Stage;
import ui.controllers.MainStage;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        MainStage mainStage = MainStage.getInstance();
        mainStage.setStage(primaryStage);
        mainStage.setRootPane();
        mainStage.setMainScene();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
