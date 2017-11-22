package CreatorMapJavaFx.Modules;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DecalsCreatorJavaFx {

    private static ObservableList<CustomImage> images = FXCollections.observableArrayList();

    private static final String decalsPath = "Editor/resources/decals";

    public static ObservableList<CustomImage> getImages() {
        return images;
    }

    public static void setDecalsPaths() {
        images = GettingImagesObj.getPaths(decalsPath,"decals");
    }
}
