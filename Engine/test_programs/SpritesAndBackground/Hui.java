package SpritesAndBackground;

import Patterns.Sprite;
import Main.Mouse;
import org.joml.Vector2f;

public class Hui extends Sprite
{
    Mouse mouse;
    public Hui( Mouse mouse)
    {
        super( new Vector2f(0,0), .9999f);
        this.alive = true;

        this.mouse = mouse;

        transform.setScale( new Vector2f( 30, 30));
        //this.setRotation(Math.PI);
    }

    @Override
    public void update() {
        transform.setPosition( mouse.getAbsoluteMousePos() ).turn(.01f);
    }
}
