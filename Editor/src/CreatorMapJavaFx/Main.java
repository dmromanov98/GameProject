package CreatorMapJavaFx;

        import CreatorMapJavaFx.Modules.TexturesInfo;
        import CreatorMapJavaFx.ObjectsInJavaFXWindow.WindowPathsCreator ;
        import Editor.EditorThread;
        import javafx.application.Application;
        import javafx.stage.Stage;

public class Main extends Application{

    static Thread thread;

    public static void main(String[] args) {
        thread = new Thread(()-> launch(WindowPathsCreator.class, args));

        EditorThread gt;
        gt = new EditorThread(800,600,60, TexturesInfo.getAllTextures());

        thread.start();
        gt.run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {}
}
