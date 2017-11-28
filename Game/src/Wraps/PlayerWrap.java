package Wraps;

import Main.Actor;
import Main.Game;
import Objects.Player;

public class PlayerWrap extends Wrap
{
    private int ID = Wrap.spriteID;

    @Override
    public int getID() {
        return ID;
    }

    public PlayerWrap()
    {
        super();
        /*TODO*/
    }

    @Override
    public Wrap copy()
    {
        /*TODO*/
        return null;
    }

    @Override
    public Actor getActor(Game game)
    {
        Player res = new Player();
        /*TODO: write player's constructor*/
        return res;
    }
}

