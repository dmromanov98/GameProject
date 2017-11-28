package Wraps;

import Main.Actor;
import Main.Game;

public abstract class Wrap {
    public static final int actorID = 0,
                            spriteID = 1,
                            decalID = 2,
                            backgroundID = 3;

    public int ID = 0; // Default actor ID

    public abstract Actor getActor(Game game);

    public abstract Wrap copy();
}
