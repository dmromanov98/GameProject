package CreatorMapJavaFx.Modules;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomImage extends ImageView {

    private String path;

    public CustomImage(String path) {
        super(new Image(path));
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
