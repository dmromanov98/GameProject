package CreatorMapJavaFx.Modules;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DekailsCreatorJavaFx {

    //private ObservableList<String> backgroundPaths;
    //private static List<String> paths;

    private static ObservableList<CustomImage> images = FXCollections.observableArrayList();

    private static final String dekailsPath = "Editor/resources/dekails";

    public static ObservableList<CustomImage> getImages() {
        return images;
    }

    public static void setDekailsPaths() {
        images = GettingImagesObj.getPaths(dekailsPath);
    }
}
