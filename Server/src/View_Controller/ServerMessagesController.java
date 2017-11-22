package View_Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ServerMessagesController extends Thread implements Initializable{

    private static boolean newMessage; // статус получения сообщений
    private static ArrayList<String> messages = new ArrayList<>();
    private static ArrayList<String> newMessages = new ArrayList<>();

    @FXML
    private TextArea text;

    //добавление сообщений в лист сообщений
    public static void add(String s){
        newMessage = true;
        newMessages.add(s);
    }

    //вывод сообщений
    public synchronized void setMessage(){
        for(String s: newMessages) {
            messages.add(s);
            text.setText(text.getText() + "\n" + s);
        }
        newMessage = false;
        newMessages.clear();
    }

    @Override
    public void run() {
        while(true){
            if(newMessage)
                setMessage();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(String s:messages)
            text.setText(text.getText() + "\n" + s);
    }
}
