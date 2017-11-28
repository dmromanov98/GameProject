package Server;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//Class of NetworkInterfaces they IP and Name
public class NetInterfaces {
    private StringProperty IP;
    private StringProperty Name;

    public NetInterfaces(String IP, String Name) {
        this.IP = new SimpleStringProperty(IP);
        this.Name = new SimpleStringProperty(Name);
    }

    public StringProperty IPProperty() {
        return IP;
    }

    public StringProperty nameProperty() {
        return Name;
    }

}
