package Main;//import SpriteTest.Engine.MyMath.Vector2;

public abstract class Actor
{
    public boolean alive = false;
    public short renderIndex = -1; //-1 -- not visible, 0 -- map and background, 1 -- static obj, 2 -- dynamic obj

    private boolean remFlag = false;
    public void delete()
    {
        remFlag = true;
    }

    abstract public void update();
    abstract public void draw();

    public boolean willBeRemoved()
    {
        return remFlag;
    }
}