package CreatorMapJavaFx.ObjectsInJavaFXWindow;

import CreatorMapJavaFx.Modules.FilesPaths;
import CreatorMapJavaFx.Modules.CustomImage;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;


public class WindowController implements Initializable{

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

}
