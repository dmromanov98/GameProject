package View_Controller;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class MainView extends Application{
    private static Stage primaryStage;
    private static AnchorPane anchorPane;

    //Запуск окна сообщений
    public static void OpenServerMasseges(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ServerMessagesController.class.getResource("ServerMessages.fxml"));
            AnchorPane anchorPane =  loader.load();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Server Messages");
            Scene scene = new Scene(anchorPane);
            primaryStage.setResizable(false);
            ServerMessagesController controller = loader.getController();
            primaryStage.setScene(scene);
            primaryStage.show();
            controller.start();
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    controller.stop();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        initBorderPane();
    }

    //главное окно
    public static void initBorderPane(){
        try{
            FXMLLoader loader =new FXMLLoader();
            loader.setLocation(MainView.class.getResource("MainView.fxml"));
            anchorPane = loader.load();

            if(primaryStage == null)
                primaryStage = new Stage();

            Scene scene = new Scene(anchorPane);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Server");
            primaryStage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
