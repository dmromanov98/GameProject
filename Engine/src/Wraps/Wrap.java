package Wraps;

import Main.Actor;
import Main.Game;

public abstract class Wrap
{
    public static final Class original = Actor.class;
    public abstract Actor getActor(Game game);

    public Class getOriginal()
    {
        return original;
    }
}
