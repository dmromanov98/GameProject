package CreatorMapJavaFx.ObjectsInJavaFXWindow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class WindowPathsCreator extends Application {
    private static Stage primaryStage;
    private AnchorPane anchorPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        initWindow();
    }

    public void initWindow(){
        try{
            FXMLLoader loader =new FXMLLoader();
            loader.setLocation(WindowPathsCreator.class.getResource("Window.fxml"));
            anchorPane = loader.load();
            Scene scene = new Scene(anchorPane);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Objects");
            primaryStage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
