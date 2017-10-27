package SpritesAndBackground;

import Patterns.Sprite;
import org.joml.Vector2f;

public class SquaredGuys extends Sprite
{
    private static float scale = 50;

    public SquaredGuys(Vector2f pos)
    {
        super(pos, 0);
        this.alive = true;

        transform.setScale( new Vector2f( scale, scale));
        //this.setRotation(Math.PI);
    }

    private static float magnetSpeed = 1;
    private void mouseMagnet(Vector2f pos)
    {
        Vector2f magnetVec = transform.getPosition().sub(pos);
        magnetVec.normalize().mul(magnetSpeed);
        this.transform.translate( magnetVec );
    }

    private void moveForward()
    {
        this.transform.moveForward(forwardSpeed*5);
    }

    private static double delRadius = 30;
    private void deleteThis(Vector2f pos)
    {
        if (transform.getPosition().sub(pos).length() < delRadius)
            this.delete();
    }

    private static float forwardSpeed = 1;
    private static int[] frames_in_state = {60, 60, 1};
    private int state = 0;
    private int frames = 0;
    @Override
    public void update()
    {
        frames++;
        switch (state){
            case 0:
                this.transform.turn( (float)Math.PI / frames_in_state[0] / 2 );
                break;
            case 1:
                this.transform.moveForward(forwardSpeed);
                break;
            case 2:
                state = 0;
                frames = 0;
                break;
        }

        if (frames == frames_in_state[state]){
            state++;
            frames = 0;
        }
    }
}
