package CreatorMapJavaFx.ObjectsInJavaFXWindow;

import Modules.CustomImage;
import Modules.FilesPaths;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;


public class WindowController implements Initializable{

    @FXML
    private JFXTextField textEditMapPath;

    @FXML
    private ListView listBackgroundPaths;

    @FXML
    private JFXTextField textBackgroundLayout;

    @FXML
    private ListView listDetailsPaths;

    @FXML
    private JFXTextField textDetailsLayout;

    @FXML
    private JFXTextField textDetailsHeight;

    @FXML
    private JFXTextField textDetailsWidth;

    @FXML
    private ListView listSpritesClasses;

    @FXML
    private ListView listSpritesTextures;

    @FXML
    private JFXTextField textSpritesLayout;

    @FXML
    private JFXTextField textSpritesHeight;

    @FXML
    private JFXTextField textSpritesWidth;

    @FXML
    private ListView list;

    @FXML
    private ListView pathsList;

    @FXML
    private JFXTextField textPath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FilesPaths.readPathFiles();
        updatePaths();
    }

    public void updatePaths(){
        list.setItems(FilesPaths.getImages());
        pathsList.setItems(FilesPaths.getPathsObs());
    }

    public void getInfoAboutObject() {
        CustomImage ci = (CustomImage) list.getFocusModel().getFocusedItem();
        String path = ci.getPath();

        //TODO: вывод path

        //System.out.println(ci.getPath());
    }


    public void deletePath() {
        String s = (String) pathsList.getFocusModel().getFocusedItem();
        FilesPaths.deleteFromListAndFile(s);
        updatePaths();
    }

    public void addPath() {
        FilesPaths.addPathToListAndFile(textPath.getText());
        updatePaths();
    }

    public void btnEditMapOpen() {
    }

    public void btnEditMapNewMap() {
    }

    public void btnBackgroundDelete() {
    }

    public void btnBackgroundAdd() {
    }

    public void btnDetailsAdd() {
    }

    public void btnSpritesAdd() {
    }
}
