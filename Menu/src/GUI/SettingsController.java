package GUI;


import Client.Client;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import Module.GameCharacters;
import javafx.scene.input.MouseEvent;

import javax.swing.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private JFXTextField textNickName;

    @FXML
    private JFXTextField textServerIP;

    @FXML
    private JFXTextField textServerPort;

    @FXML
    private JFXTextField textWindowWidth;

    @FXML
    private JFXTextField textWindowHeight;

    @FXML
    private JFXTextField textFPS;

    @FXML
    private ImageView imageViewCharacter;

    private static GUIs guis;

    public static void setGuis(GUIs guis) {
        SettingsController.guis = guis;
    }

    public void applySettings() {

        int width;
        int height;
        int fps;

        Client.setNickname(textNickName.getText());
        Client.setIP(textServerIP.getText());

        try {
            Client.setPORT(Integer.parseInt(textServerPort.getText()));
        }catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null,"PORT MUST BE A NUMBER!");
        }

        try {

            width = Integer.parseInt(textWindowWidth.getText());
            height = Integer.parseInt(textWindowHeight.getText());
            fps = Integer.parseInt(textFPS.getText());

        }catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null,"WIDTH,HEIGHT,FPS MUST BE A NUMBERS!");
        }

    }

    public void backToMenu() {
        guis.initWindow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textNickName.setText(Client.getNickname());
        textServerIP.setText(Client.getIP());
        textServerPort.setText(Client.getPORT().toString());
        textWindowWidth.setText("800");
        textWindowHeight.setText("600");
        textFPS.setText("60");
        GameCharacters.setCharactersPaths();
        imageViewCharacter.setImage(GameCharacters.setNextCharacter().getImage());
    }

    public void nextCharacter() {
        imageViewCharacter.setImage(GameCharacters.setNextCharacter().getImage());
    }
}
