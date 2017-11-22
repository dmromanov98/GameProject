package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class GUIs extends Application {
    private static Stage primaryStage;
    private AnchorPane anchorPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        MenuController.setGuis(this);
        initWindow();
    }

    public void initWindow(){
        try{
            FXMLLoader loader =new FXMLLoader();
            loader.setLocation(GUIs.class.getResource("Menu.fxml"));
            anchorPane = new AnchorPane();

            ImageView iv = new ImageView(new Image("file:Menu/resources/menu.jpg"));
            anchorPane.getChildren().addAll(iv,loader.load());

            Scene scene = new Scene(anchorPane);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.setTitle("GameMenu");
            primaryStage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
