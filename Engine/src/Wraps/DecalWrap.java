package Wraps;

import Graphics.Texture;
import Main.Actor;
import Main.Game;
import Main.Transform;
import Map.Decal;

public class DecalWrap extends Wrap
{
    public static final Class original = Decal.class;

    public Transform transform;
    public String texName;

    public DecalWrap(Transform transform, String texName)
    {
        this.transform = new Transform(transform);
        this.texName = texName;
    }

    public DecalWrap(DecalWrap wrap)
    {
        this.transform = new Transform(wrap.transform);
        this.texName = wrap.texName;
    }

    @Override
    public DecalWrap copy()
    {
        return new DecalWrap(this);
    }

    @Override
    public Actor getActor(Game game) {
        Texture texture;
        try {
            texture = game.textureBank.Get(texName).getTexture();
        } catch (Exception e){e.printStackTrace(); texture = Texture.monoColor(255,255,255,255);}

        return new Decal(new Transform(transform), texture).setSource(this);
    }
}
