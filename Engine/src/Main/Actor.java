package Main;//import SpriteTest.Engine.MyMath.Vector2;

public abstract class Actor
{
    public boolean alive = false;
    public boolean visible = false;

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