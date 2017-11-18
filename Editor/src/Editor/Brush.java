package Editor;

import Main.Actor;
import Main.Game;
import Main.Transform;
import Patterns.Sprite;
import Physics.Rectangle;
import Wraps.DecalWrap;
import Wraps.Wrap;
import org.joml.Vector2f;

public class Brush extends Actor
{
    public static void init(Game game)
    {
        try{
        game.textureBank.addFromDisk("shape", "Engine\\src\\Map\\shape.png");}
        catch (Exception e){e.printStackTrace();}
    }

    public static class Shape extends Sprite
    {
        public Shape()
        {
            super(new Vector2f(0,0), .9999f);
        }

        public void circle(Rectangle rect)
        {

        }

        public void circle(Transform transform)
        {
            if (renderIndex == -1){
                this.transform = new Transform(transform);
                renderIndex = 2;
            }
        }

        public void hide()
        {
            renderIndex = -1;
        }

        @Override
        public void update() {}
    }

    private Shape shape;


    private Actor currentActor;

    private byte mode; //

    private Wrap actorWrap;
    private DecalWrap decalWrap;

    public Brush()
    {
        alive = true;
        renderIndex = 3;
        shape = new Shape();
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        shape.draw();
    }
}
