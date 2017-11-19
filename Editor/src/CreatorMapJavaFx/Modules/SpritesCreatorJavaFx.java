package CreatorMapJavaFx.Modules;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SpritesCreatorJavaFx {
    //private ObservableList<String> backgroundPaths;
    //private static List<String> paths;

    private static ObservableList<CustomImage> images = FXCollections.observableArrayList();

    private static final String spritesPath = "Editor/resources/sprites";

    public static ObservableList<CustomImage> getImages() {
        return images;
    }

    public static void setSpritesPaths() {
        images = GettingImagesObj.getPaths(spritesPath);
    }

}
