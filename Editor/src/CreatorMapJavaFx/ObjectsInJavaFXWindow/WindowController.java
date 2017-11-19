package CreatorMapJavaFx.ObjectsInJavaFXWindow;

import CreatorMapJavaFx.Modules.*;
import Editor.Brush;
import com.jfoenix.controls.JFXTextField;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

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
    private ListView listDekailsPaths;

    @FXML
    private JFXTextField textDekailsLayout;

    @FXML
    private JFXTextField textDekailsHeight;

    @FXML
    private JFXTextField textDekailsWidth;

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
        BackgroundCreatorJavaFx.setBackgroundPaths();
        DekailsCreatorJavaFx.setDekailsPaths();
        SpritesCreatorJavaFx.setSpritesPaths();
        updatePaths();
    }

    public void updatePaths(){
        listBackgroundPaths.setItems(BackgroundCreatorJavaFx.getImages());
        listDekailsPaths.setItems(DekailsCreatorJavaFx.getImages());
        listSpritesTextures.setItems(SpritesCreatorJavaFx.getImages());

//        list.setItems(FilesPaths.getImages());
//        pathsList.setItems(FilesPaths.getPathsObs());
    }

    public void getInfoAboutObject() {
//        CustomImage ci = (CustomImage) list.getFocusModel().getFocusedItem();
//        String path = ci.getPath();
//        TODO: вывод path
//        System.out.println(ci.getPath());
    }

    public void deletePath() {
//        String s = (String) pathsList.getFocusModel().getFocusedItem();
//        FilesPaths.deleteFromListAndFile(s);
//        updatePaths();
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

    public void btnDekailsAdd() {
        String path = "";
        int layout;
        try {
            layout = Integer.parseInt(textDekailsLayout.getText());
            CustomImage ci = (CustomImage) listDekailsPaths.getFocusModel().getFocusedItem();
            float height = (float) ci.getImage().getHeight();
            float width = (float) ci.getImage().getWidth();
            path = ci.getIdentify()+"|"+ci.getPath();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"LAYOUT MUST BE A NUMBER!");
        }

        System.out.println(path);
    }

    public void btnSpritesAdd() {
        String path = "";
        int layout;
        try {
            layout = Integer.parseInt(textSpritesLayout.getText());
            CustomImage ci = (CustomImage) listSpritesTextures.getFocusModel().getFocusedItem();
            float height = (float) ci.getImage().getHeight();
            float width = (float) ci.getImage().getWidth();
            path = ci.getIdentify()+"|"+ci.getPath();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"LAYOUT MUST BE A NUMBER!");
        }

        System.out.println(path);
    }

    public void btnEditSaveMap() {
    }

    public void setModeNull() {

    }

    public void setModeOne() {

    }

    public void setModeTwo() {

    }

    public void setModeThree() {

    }

    public void listDekailsClick() {
        CustomImage ci = (CustomImage) listDekailsPaths.getFocusModel().getFocusedItem();
        float height = (float) ci.getImage().getHeight();
        float width = (float) ci.getImage().getWidth();
        textDekailsHeight.setText(String.valueOf(height));
        textDekailsWidth.setText(String.valueOf(width));
    }

    public void listSpritesTextureClick() {
        CustomImage ci = (CustomImage) listSpritesTextures.getFocusModel().getFocusedItem();
        float height = (float) ci.getImage().getHeight();
        float width = (float) ci.getImage().getWidth();
        textSpritesHeight.setText(String.valueOf(height));
        textSpritesWidth.setText(String.valueOf(width));
    }
}
