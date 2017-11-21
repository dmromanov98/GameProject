package Editor;

import Main.Game;
import Wraps.BackgroundWrap;
import Wraps.DecalWrap;
import Wraps.Wrap;

public class GameThread extends Thread
{
    public Game game;
    public Editor editor;

    //TODO: гавно моча с этим моментом. но продумывать что-то лучше времени нет
    public void toMode0()
    {
        Editor.brushMode = 0;
    }

    public static void toMode1(BackgroundWrap wrap)
    {
        Editor.currentBackgroundWrap = wrap;
        if (Editor.brushMode == 1){
            Editor.brushMode = -1;
        }
        else
            Editor.brushMode = 1;
    }

    public static void toMode2(DecalWrap wrap)
    {
        Editor.currentDecalWrap = wrap;
        if (Editor.brushMode == 2){
            Editor.brushMode = -2;
        }
        else
            Editor.brushMode = 2;

    }

    public void toMode3(Wrap wrap)
    {
        Editor.currentWrap = wrap;
        if (Editor.brushMode == 3){
            Editor.brushMode = -3;
        }
        else
            Editor.brushMode = 3;
    }

    private int fps;
    private int width, height;
    private String[] textureList;
    private Wrap[] wraps;

    public GameThread(int width, int height, int fps, String[] textureList) //создание новой карты
    {
        super("Editor");
        setPriority(MAX_PRIORITY);
        this.fps = fps;
        this.width = width;
        this.height = height;
        this.textureList = textureList;
        wraps = null;
    }

    private void init()
    {
        game = new Game(width, height);
        game.init();
        game.fps = fps;
        game.textureBank.addTexturesFromList(textureList);
        Brush.init(game);
        if (wraps == null)
            editor = new Editor(game);
        else
            editor = Editor.fromWraps(wraps,game);
        game.map = editor;
    }

    public GameThread(int width, int height, int fps, String[] textureList, Wrap[] wraps) //загрузка карты
    {
        super("Editor");
        setPriority(MAX_PRIORITY);
        this.fps = fps;
        this.width = width;
        this.height = height;
        this.textureList = textureList;
        this.wraps = wraps;
    }

    @Override
    public void run()
    {
        super.run();
        init();
        game.mainloop();
        game.closeGame();
    }

}
