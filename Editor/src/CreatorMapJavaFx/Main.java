package CreatorMapJavaFx;

import CreatorMapJavaFx.ViewController.WindowPathsCreator;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(WindowPathsCreator.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    }
}
