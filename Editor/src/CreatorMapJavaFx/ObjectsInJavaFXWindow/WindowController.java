package CreatorMapJavaFx.ObjectsInJavaFXWindow;

import CreatorMapJavaFx.Modules.BackgroundCreatorJavaFx;
import CreatorMapJavaFx.Modules.CustomImage ;
import CreatorMapJavaFx.Modules.DekailsCreatorJavaFx;
import CreatorMapJavaFx.Modules.FilesPaths ;
import Editor.Brush;
import com.jfoenix.controls.JFXTextField;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import javax.swing.*;
import java.net.URL;
import java.util.HashMap;
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

    private HashMap<String,Boolean> mode=new HashMap<>(){{
        put("edit",false);
        put("background",false);
        put("dekails",false);
        put("sprites",false);
    }};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BackgroundCreatorJavaFx.setBackgroundPaths();
        DekailsCreatorJavaFx.setDekailsPaths();
        updatePaths();
    }

    public void updatePaths(){
        listBackgroundPaths.setItems(BackgroundCreatorJavaFx.getImages());
        listDetailsPaths.setItems(DekailsCreatorJavaFx.getImages());



//        list.setItems(FilesPaths.getImages());
//        pathsList.setItems(FilesPaths.getPathsObs());
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


    //добавление бэкграунда
    public void btnBackgroundAdd() {
        String path = "";
        int layout;

        try {
            layout = Integer.parseInt(textBackgroundLayout.getText());
            CustomImage ci = (CustomImage) listBackgroundPaths.getFocusModel().getFocusedItem();
            path = ci.getIdentify()+"|"+ci.getPath();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"LAYOUT MUST BE A NUMBER!");
        }

        System.out.println(path);
    }

    public void btnDetailsAdd() {
    }

    public void btnSpritesAdd() {
    }

    public void btnEditSaveMap() {
    }

    private void updateHash(){
        mode.put("edit",false);
        mode.put("background",false);
        mode.put("dekails",false);
        mode.put("sprites",false);
    }


    public void setModeNull() {
        updateHash();
        if(!mode.get("edit")){
            System.out.println(0);
            mode.put("edit",true);
        }
    }

    public void setModeOne() {
        updateHash();
        if(!mode.get("background")){
            System.out.println(1);
            mode.put("background",true);
        }
    }

    public void setModeTwo() {
        updateHash();
        if(!mode.get("dekails")){
            System.out.println(2);
            mode.put("background",true);
        }
    }

    public void setModeThree() {
        updateHash();
        if(!mode.get("sprites")){
            System.out.println(3);
            mode.put("sprites",true);
        }
    }
}
