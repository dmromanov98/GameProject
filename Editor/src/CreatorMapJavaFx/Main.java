package CreatorMapJavaFx;

import CreatorMapJavaFx.ObjectsInJavaFXWindow.WindowPathsCreator;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    static Thread thread;

    public static void main(String[] args) {

        //thread = new Thread(()-> launch(WindowPathsCreator.class, args));

        launch(WindowPathsCreator.class, args);

        //thread.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    }
}
