package CreatorMapJavaFx.Modules;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BackgroundCreatorJavaFx {

    private static ObservableList<CustomImage> images = FXCollections.observableArrayList();

    private static final String backgroundPath = "Editor/resources/backgrounds";

    public static ObservableList<CustomImage> getImages() {
        return images;
    }

    public static void setBackgroundPaths() {
        images = GettingImagesObj.getPaths(backgroundPath);
    }
}
