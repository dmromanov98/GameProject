package Wraps;

import Main.Actor;
import Main.Game;
import Main.Transform;
import Patterns.Sprite;

public class SpriteWrap extends Wrap
{
    public static final Class original = Sprite.class;

    public Transform transform;
    public String texName;
    public Runnable updateMeth;

    public SpriteWrap(Transform transform, String texName, Runnable updateMeth)
    {
        this.transform = new Transform(transform);
        this.texName = texName;
        this.updateMeth = updateMeth;
    }

    @Override
    public Actor getActor(Game game) {
        Sprite res = new Sprite() {
            @Override
            public void update() {
                updateMeth.run();
            }
        };
        res.transform = transform;
        try {
            res.texture = game.textureBank.Get(texName).getTexture();
        } catch (Exception e){e.printStackTrace();}
        return res;
    }
}