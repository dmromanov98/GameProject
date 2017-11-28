package Editor;

import Main.Game;
import Map.MapWrap;
import Wraps.BackgroundWrap;
import Wraps.DecalWrap;
import Wraps.Wrap;

public class EditorThread extends Thread {
    public Game game;
    public Editor editor;

    //TODO: гавно моча с этим моментом. но продумывать что-то лучше времени нет
    public static synchronized void toMode0() {
        Editor.brushMode = 0;
    }

    public static synchronized void toMode1(BackgroundWrap wrap) {
        Editor.currentBackgroundWrap = wrap;
        if (Editor.brushMode == 1) {
            Editor.brushMode = -1;
        } else
            Editor.brushMode = 1;
    }

    public static synchronized void toMode2(DecalWrap wrap) {
        Editor.currentDecalWrap = wrap;
        if (Editor.brushMode == 2) {
            Editor.brushMode = -2;
        } else
            Editor.brushMode = 2;

    }

    public static synchronized void toMode3(Wrap wrap) {
        Editor.currentWrap = wrap;
        if (Editor.brushMode == 3) {
            Editor.brushMode = -3;
        } else
            Editor.brushMode = 3;
    }

    public static synchronized void toMode4(String nameOfCollisionSpace) {
        Editor.currentCollisionArea = nameOfCollisionSpace;
        if (Editor.brushMode == 4) {
            Editor.brushMode = -4;
        } else
            Editor.brushMode = 4;
    }

    public static MapWrap getOutputMapWrap() {

        //TODO:OUT TEST
        System.out.println(outputMapWrap.collideAreas.get("1").getRectangles());

        return outputMapWrap;
    }

    public static MapWrap outputMapWrap;

    private int fps;
    private int width, height;
    private String[] textureList;
    private MapWrap mapWrap;

    public EditorThread(int width, int height, int fps, String[] textureList) //создание новой карты
    {
        super("Editor");
        setPriority(MAX_PRIORITY);
        this.fps = fps;
        this.width = width;
        this.height = height;
        this.textureList = textureList;
        mapWrap = null;
    }

    private void init()
    {
        game = new Game(width, height);
        game.init();
        game.fps = fps;
        game.textureBank.addTexturesFromList(textureList);
        Brush.init(game);
        if (mapWrap == null)
            editor = new Editor(game);
        else
            editor = new Editor(game, mapWrap);
        game.map = editor;
    }

    public EditorThread(int width, int height, int fps, String[] textureList, MapWrap wrap) //загрузка карты
    {
        super("Editor");
        setPriority(MAX_PRIORITY);
        this.fps = fps;
        this.width = width;
        this.height = height;
        this.textureList = textureList;
        this.mapWrap = wrap;
    }

    @Override
    public void run() {
        super.run();
        init();
        game.mainloop();
        outputMapWrap = editor.getMapWrap();
        game.closeGame();
    }

}
