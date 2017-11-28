package CreatorMapJavaFx.Modules;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CollisionsNames {
    private static ObservableList<String> collisions = FXCollections.observableArrayList();

    public static void addCollision(String s) {
        collisions.add(s);
    }

    public static ObservableList<String> getCollisions() {
        return collisions;
    }
}
