package CreatorMapJavaFx;

        import CreatorMapJavaFx.Modules.BackgroundCreatorJavaFx;
        import CreatorMapJavaFx.ObjectsInJavaFXWindow.WindowPathsCreator ;
        import Editor.GameThread;
        import javafx.application.Application;
        import javafx.stage.Stage;

public class Main extends Application{

    static Thread thread;

    public static void main(String[] args) {
        thread = new Thread(()-> launch(WindowPathsCreator.class, args));
        //thread.start();
        //launch(WindowPathsCreator.class, args);
        BackgroundCreatorJavaFx.setBackgroundPaths();
        GameThread gt;

        String[] texture = new String[6];
        for(int i = 0;i<6;i++){
            texture[i] = BackgroundCreatorJavaFx.getImages().get(i).getIdentify()+"|"+BackgroundCreatorJavaFx.getImages().get(i).getPath();
        }

        gt = new GameThread(800,600,60,texture);
        thread.start();
        gt.run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {}
}
