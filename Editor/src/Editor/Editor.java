package Editor;

import Graphics.Texture;
import Main.Actor;
import Main.Camera;
import Main.Game;
import Map.Map;
import Map.Decal;
import Map.MapWrap;
import Patterns.Background;
import Wraps.DecalWrap;
import Wraps.Wrap;
import Wraps.BackgroundWrap;

import java.util.Vector;

public class Editor extends Map {
    public static Wrap currentWrap = null;
    public static DecalWrap currentDecalWrap = null;
    public static BackgroundWrap currentBackgroundWrap = null;
    public static String currentCollisionArea = null;
    public static short brushMode = 0; //0 -- редачим то, что видим, 1 -- создать бэкграунд, 2 -- ставить спрайты, 3 -- ставить декали

    private final Game game;

    public Editor(Game game) {
        super();
        this.game = game;
        brush = new Brush(game);
        brush.initControls(game);
        backgrounds.add(new Background(Texture.monoColor(200, 255, 255, 255), .9999f));
        cameraController = new CameraController(game);
    }

    public Editor(Game game, MapWrap wrap) {
        super(game, wrap);
        this.game = game;
        brush = new Brush(game);
        brush.initControls(game);
        backgrounds.add(new Background(Texture.monoColor(200, 255, 255, 255), .9999f));
        cameraController = new CameraController(game);
    }

    public Vector<Actor> getActors() {
        return actors;
    }

    public Vector<Actor> getDecals() {
        return decals;
    }

    public Vector<Actor> getBackgrounds() {
        return backgrounds;
    }

    @Override
    public void update() {
        brush.update(this, game);
        cameraController.update();
        super.update();
    }

    @Override
    public void drawAll() {
        for (Actor back : backgrounds) {
            if (back.renderIndex > -1)
                back.draw();
        }

        brush.draw();

        if (brush.currentActor != null)
            if (brush.currentActor.renderIndex > -1)
                brush.currentActor.draw();

        //decals
        Decal.shader.enable();
        Camera.toShader(Decal.shader);
        for (Actor decal : decals) {
            decal.draw();
            if (decal.willBeRemoved())
                decalsRemBuffer.add(decal);
        }
        Decal.shader.disable();

        for (Actor a : actors)
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

    public Vector<Wrap> getWraps() {
        Vector<Wrap> res = new Vector<>();

        for (Actor a :
                actors) {
            if (a.source != null)
                res.add(a.source);
        }

        for (Actor a :
                decals) {
            if (a.source != null)
                res.add(a.source);
        }

        for (Actor a :
                backgrounds) {
            if (a.source != null)
                res.add(a.source);
        }

        return res;
    }

    public MapWrap getMapWrap() {
        brush.changeMode((short) 0, this, game);
        return new MapWrap(getWraps(), collisionSpaces);
    }

    private Brush brush;
    private CameraController cameraController;
}
