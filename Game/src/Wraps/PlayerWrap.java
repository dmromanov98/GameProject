package Wraps;

import Main.Actor;
import Main.Game;
import Player.Player;

public class PlayerWrap extends Wrap
{
    public int ID = Wrap.spriteID;

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

