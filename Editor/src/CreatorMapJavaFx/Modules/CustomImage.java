package CreatorMapJavaFx.Modules;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomImage extends ImageView {

    private String path;
    private String identify;

    public CustomImage(String path,String identify) {
        super(new Image(path));
        this.path = path;
        this.identify = identify;
    }

    public String getIdentify() {
        return identify;
    }

    public String getPath() {
        return path;
    }

}
