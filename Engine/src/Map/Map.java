package Map;

import Main.Actor;
import Main.Camera;
import Main.Game;
import Patterns.Background;
import Physics.CollisionSpace;
import Wraps.Wrap;

import java.util.HashMap;
import java.util.Vector;

public class Map
{
    public HashMap<String, CollisionSpace> collisionSpaces = new HashMap<>();

    public Map()
    {
        backgrounds = new Vector<>();
        decals = new Vector<>();
        actors = new Vector<>();
        actorsRemBuffer = new Vector<>();
        decalsRemBuffer = new Vector<>();
    }

    public Map(Game game, MapWrap wrap)
    {
        backgrounds = new Vector<>();
        decals = new Vector<>();
        actors = new Vector<>();
        actorsRemBuffer = new Vector<>();
        decalsRemBuffer = new Vector<>();

        for (Wrap w:
                wrap.objects) {
            if (Decal.class.equals( w.getOriginal() ) )
                addDecal( w.getActor(game) );
            else if (Background.class.equals( w.getOriginal() ) )
                addBackground( w.getActor(game) );
            else addActor( w.getActor(game) );
        }
        this.collisionSpaces = new HashMap<>(collisionSpaces);
    }

    protected Vector<Actor> backgrounds;
    protected Vector<Actor> decals, decalsRemBuffer;
    protected Vector<Actor> actors, actorsRemBuffer;

    public void update()
    {
        for (Actor a : actors) {
            if (a.alive)
                a.update();
            if (a.willBeRemoved())
                actorsRemBuffer.add(a);
        }
    }

    public void drawAll()
    {
        for (Actor back: backgrounds) {
            if (back.renderIndex > -1)
            back.draw();
        }

        //decals
        Decal.shader.enable();
        Camera.toShader(Decal.shader);
        for (Actor decal: decals) {
            decal.draw();
            if (decal.willBeRemoved())
                decalsRemBuffer.add(decal);
        }
        Decal.shader.disable();

        for (Actor a: actors)
            if (a.renderIndex > -1)
                a.draw();


        if (!actorsRemBuffer.isEmpty()) {
            actors.removeAll(actorsRemBuffer);
            actorsRemBuffer.clear();
        }

        if (!decalsRemBuffer.isEmpty()) {
            decals.removeAll(decalsRemBuffer);
            decalsRemBuffer.clear();
        }
    }

    public void addActor(Actor a)
    {
        actors.add(a);
    }

    public void addDecal(Decal decal)
    {
        decals.add(decal);
    }

    private void addDecal(Actor decal)
    {
        decals.add(decal);
    }

    public void addBackground(Background back)
    {
        backgrounds.add(back);
    }

    private void addBackground(Actor back)
    {
        backgrounds.add(back);
    }

    public static Map fromWraps(Wrap wraps[], Game game)
    {
        Map res = new Map();
        for (Wrap w:
             wraps) {
            if (Decal.class.equals( w.getOriginal() ) )
                res.addDecal( w.getActor(game) );
            else if (Background.class.equals( w.getOriginal() ) )
                res.addBackground( w.getActor(game) );
            else res.addActor( w.getActor(game) );
        }
        return res;
    }

    public static Map fromWraps(Wrap wraps[], HashMap<String, CollisionSpace> collisionSpaces,Game game)
    {
        Map res = new Map();
        for (Wrap w:
                wraps) {
            if (Decal.class.equals( w.getOriginal() ) )
                res.addDecal( w.getActor(game) );
            else if (Background.class.equals( w.getOriginal() ) )
                res.addBackground( w.getActor(game) );
            else res.addActor( w.getActor(game) );
        }
        res.collisionSpaces = collisionSpaces;
        return res;
    }
}
