
import javafx.application.Application;
import javafx.stage.Stage;
import ui.controllers.View;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        View view = View.getInstance();
        view.setStage(primaryStage);
        view.setRootPane();
        view.setMainScene();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
