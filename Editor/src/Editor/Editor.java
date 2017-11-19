package Editor;

import Main.Actor;
import Map.Map;
import Patterns.Background;
import Wraps.DecalWrap;
import Wraps.Wrap;
import Wraps.BackgroundWrap;

import java.util.Vector;

public class Editor extends Map
{
    public static Wrap currentWrap = null;
    public static DecalWrap currentDecalWrap = null;
    public static BackgroundWrap currentBackgroundWrap = null;
    public static byte brushMode = 0; //0 -- редачим то, что видим, 1 -- создать бэкграунд, 2 -- ставить спрайты, 3 -- ставить декали

    public Editor()
    {
        super();
        brush = new Brush();
    }

    public Vector<Actor> getActors()
    {
        return actors;
    }

    public Vector<Actor> getDecals()
    {
        return decals;
    }

    public Vector<Actor> getBackgrounds()
    {
        return backgrounds;
    }

    private Brush brush;

}
