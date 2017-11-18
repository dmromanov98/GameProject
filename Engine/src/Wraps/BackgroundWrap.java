package Wraps;

import Graphics.Texture;
import Main.Actor;
import Main.Game;
import Patterns.Background;

public class BackgroundWrap extends Wrap
{
    public static final Class original = Background.class;

    public String texName;
    public float layer;

    public BackgroundWrap(String texName, float layer)
    {
        this.texName = texName;
        this.layer = layer;
    }

    @Override
    public Actor getActor(Game game) {
        Texture texture;
        try {
            texture = game.textureBank.Get(texName).getTexture();
        } catch (Exception e){e.printStackTrace(); texture = Texture.monoColor(255,255,255,255);}

        return new Background(texture, layer);
    }
}
