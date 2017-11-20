package CreatorMapJavaFx.ObjectsInJavaFXWindow;

import CreatorMapJavaFx.Modules.*;
import Editor.Brush;
import Editor.GameThread;
import Graphics.Texture;
import Main.Transform;
import Utils.File;
import Wraps.BackgroundWrap;
import Wraps.DecalWrap;
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


public class WindowController implements Initializable {

    @FXML
    private JFXTextField textEditMapPath;

    @FXML
    private ListView listBackgroundPaths;

    @FXML
    private JFXTextField textBackgroundLayout;

    @FXML
    private ListView listDecalsPaths;

    @FXML
    private JFXTextField textDecalsLayout;

    @FXML
    private JFXTextField textDecalsHeight;

    @FXML
    private JFXTextField textDecalsWidth;

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
        updatePaths();
    }

    public void updatePaths() {
        listBackgroundPaths.setItems(BackgroundCreatorJavaFx.getImages());
        listDecalsPaths.setItems(DecalsCreatorJavaFx.getImages());
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
        float layout = 0;
        CustomImage ci = null;
        try {
            layout = Float.parseFloat(textBackgroundLayout.getText());
            if (layout < 1 && layout > -1) {

                textSpritesLayout.setText(String.valueOf(layout));
                textDecalsLayout.setText(String.valueOf(layout));

                System.out.println(layout);
                System.out.println(layout + " LAYOUT");

                ci = (CustomImage) listBackgroundPaths.getFocusModel().getFocusedItem();

                BackgroundWrap backgroundWrap = new BackgroundWrap(ci.getKey(), layout);
                GameThread.toMode1(backgroundWrap);

            } else {
                JOptionPane.showMessageDialog(null, "LAYOUT MUST BE A NUMBER! 1- < NUMBER < 1");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "LAYOUT MUST BE A NUMBER! 1- < NUMBER < 1");
        }
    }

    public void btnDecalsAdd() {
        float layout;
        try {
            layout = Float.parseFloat(textDecalsLayout.getText());
            if (layout < 1 && layout > -1) {

                textSpritesLayout.setText(String.valueOf(layout));
                textBackgroundLayout.setText(String.valueOf(layout));

                System.out.println(textDecalsLayout.getText());
                System.out.println(layout + " LAYOUT");

                CustomImage ci = (CustomImage) listDecalsPaths.getFocusModel().getFocusedItem();
                float height = (float) ci.getImage().getHeight();
                float width = (float) ci.getImage().getWidth();


                Transform transform = new Transform(layout, width, height);
                DecalWrap wrap = new DecalWrap(transform, ci.getKey());
                GameThread.toMode2(wrap);

            } else {
                JOptionPane.showMessageDialog(null, "LAYOUT MUST BE A NUMBER! 1- < NUMBER < 1");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "LAYOUT MUST BE A NUMBER! 1- < NUMBER < 1");
        }

    }

    public void btnSpritesAdd() {
        String path = "";
        int layout;
        try {
            layout = Integer.parseInt(textSpritesLayout.getText());
            if (layout < 1 && layout > -1) {

                textBackgroundLayout.setText(String.valueOf(layout));
                textBackgroundLayout.setText(String.valueOf(layout));

                CustomImage ci = (CustomImage) listSpritesTextures.getFocusModel().getFocusedItem();
                float height = (float) ci.getImage().getHeight();
                float width = (float) ci.getImage().getWidth();

                path = ci.getKey() + "|" + ci.getPath();

            } else {
                JOptionPane.showMessageDialog(null, "LAYOUT MUST BE A NUMBER! 1- < NUMBER < 1");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "LAYOUT MUST BE A NUMBER! 1- < NUMBER < 1");
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

    public void listDecalsClick() {
        CustomImage ci = (CustomImage) listDecalsPaths.getFocusModel().getFocusedItem();
        float height = (float) ci.getImage().getHeight();
        float width = (float) ci.getImage().getWidth();
        textDecalsHeight.setText(String.valueOf(height));
        textDecalsWidth.setText(String.valueOf(width));
    }

    public void listSpritesTextureClick() {
        CustomImage ci = (CustomImage) listSpritesTextures.getFocusModel().getFocusedItem();
        float height = (float) ci.getImage().getHeight();
        float width = (float) ci.getImage().getWidth();
        textSpritesHeight.setText(String.valueOf(height));
        textSpritesWidth.setText(String.valueOf(width));
    }
}
