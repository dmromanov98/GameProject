package Server;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client {
    private StringProperty IP;
    private StringProperty PORT;
    private StringProperty NickName;
    private StringProperty Status;
    private int[] Pos;

    public String getPORT() {
        return PORT.get();
    }

    public void setIP(String IP) {
        this.IP = new SimpleStringProperty(IP);
    }

    public String getIP() {
        return IP.get();
    }

    public void setPORT(String PORT) {
        this.PORT = new SimpleStringProperty(PORT);
    }

    public void setNickName(String NickName) {
        this.NickName = new SimpleStringProperty(NickName);
    }

    public void setStatus(String Status) {
        this.Status = new SimpleStringProperty(Status);
    }

    public void setPos(int[] Pos) {
        this.Pos = Pos;
    }

    public int[] getPos() {
        return Pos;
    }

    public StringProperty IPProperty() {
        return IP;
    }

    public StringProperty PORTProperty() {
        return PORT;
    }

    public StringProperty nickNameProperty() {
        return NickName;
    }

    public StringProperty statusProperty() {
        return Status;
    }

    /*public Client(String IP,String PORT,String NickName,String Status,int[] Pos){
        this.IP = new SimpleStringProperty(IP);
        this.PORT = new SimpleStringProperty(PORT);
        this.NickName = new SimpleStringProperty(NickName);
        this.Status = new SimpleStringProperty(Status);
        this.Pos = Pos;
    }*/

}
