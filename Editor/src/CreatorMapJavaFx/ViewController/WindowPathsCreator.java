package CreatorMapJavaFx.ViewController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class WindowPathsCreator extends Application {
    private static Stage primaryStage;
    private static AnchorPane anchorPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        initWindow();
    }

    public static void initWindow() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(WindowPathsCreator.class.getResource("Window.fxml"));
            anchorPane = loader.load();
            Scene scene = new Scene(anchorPane);

            if (primaryStage == null)
                primaryStage = new Stage();

            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Map Editor");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
