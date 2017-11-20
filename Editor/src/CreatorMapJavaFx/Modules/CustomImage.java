package CreatorMapJavaFx.Modules;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomImage extends ImageView {

    private String path;
    private String key;

    public CustomImage(String path,String key) {
        super(new Image("file:"+path));
        this.path = path;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getPath() {
        return path;
    }

}
