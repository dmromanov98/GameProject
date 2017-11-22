package CreatorMapJavaFx.ObjectsInJavaFXWindow;

import CreatorMapJavaFx.Modules.*;
import Editor.EditorThread;
import Main.Transform;
import Wraps.BackgroundWrap;
import Wraps.DecalWrap;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

import static CreatorMapJavaFx.Modules.CollisionsNames.addCollision;
import static CreatorMapJavaFx.Modules.TexturesInfo.getAllTextures;
import static CreatorMapJavaFx.Modules.TexturesInfo.getTextures;
import static Editor.EditorThread.toMode0;
import static Editor.EditorThread.toMode4;


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
    private JFXTextField textCollisionName;

    @FXML
    private JFXTextField textEditFPS;

    @FXML
    private ListView listCollisions;

    @FXML
    private JFXTextField textSpritesWidth;

    @FXML
    private JFXTextField textEditWindowHeight;

    @FXML
    private JFXTextField textEditWindowWidth;

    private boolean modeNullEntered = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        getAllTextures();

        textEditWindowHeight.setText("1024");
        textEditWindowWidth.setText("1280");

        textDecalsLayout.setText(".99");
        textBackgroundLayout.setText(".99");
        textSpritesLayout.setText(".99");

        updatePaths();
    }

    public void updatePaths() {
        listBackgroundPaths.setItems(BackgroundCreatorJavaFx.getImages());
        listDecalsPaths.setItems(DecalsCreatorJavaFx.getImages());
        listSpritesTextures.setItems(SpritesCreatorJavaFx.getImages());
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
                EditorThread.toMode1(backgroundWrap);

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
                float height = Float.parseFloat(textDecalsHeight.getText());//ci.getImage().getHeight();
                float width = Float.parseFloat(textDecalsWidth.getText()); //ci.getImage().getWidth();


                Transform transform = new Transform(layout, width, height);
                DecalWrap wrap = new DecalWrap(transform, ci.getKey());
                EditorThread.toMode2(wrap);

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
        if (!modeNullEntered) {
            toMode0();
            modeNullEntered = true;
        }
    }

    public void setModeOne() {
        modeNullEntered = false;
    }

    public void setModeTwo() {
        modeNullEntered = false;
    }

    public void setModeThree() {
        modeNullEntered = false;
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

    public void btnEditOpenWindow() {
        try {
            int height = Integer.parseInt(textEditWindowHeight.getText());
            int width = Integer.parseInt(textEditWindowWidth.getText());
            int fps = Integer.parseInt(textEditFPS.getText());

            EditorThread gt;
            gt = new EditorThread(width, height, fps, getTextures());
            gt.run();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "HEIGHT,WIDTH,FPS - NUMBERS");
        }
    }

    public void listCollisionsSendCollider() {
        String collision = (String) listCollisions.getFocusModel().getFocusedItem();
        if (collision != null)
            toMode4(collision);
    }

    public void btnCollisionAdd() {
        addCollision(textCollisionName.getText());
        listCollisions.setItems(CollisionsNames.getCollisions());
    }

}
