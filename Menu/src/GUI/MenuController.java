package GUI;

import CreatorMapJavaFx.ViewController.WindowPathsCreator;
import View_Controller.MainView;

public class MenuController {

    private static GUIs guis;

    public static void setGuis(GUIs guis) {
        MenuController.guis = guis;
    }

    public void startGame() {
    }

    public void openServerWindow() {
        MainView.initBorderPane();
    }

    public void openMapEditorWindow() {
        WindowPathsCreator.initWindow();
    }

    public void openSettings() {
        guis.initSettings();
    }
}
