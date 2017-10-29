package SpritesAndBackground;

import Graphics.Texture;
import Patterns.Background;

public class MyBack extends Background
{
    public MyBack(Texture texture)
    {
        super(texture);
        setScrollSpeed(.25f);
    }
    @Override
    public void update() {}
}
