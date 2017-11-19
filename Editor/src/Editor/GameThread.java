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

    public void toMode1(BackgroundWrap wrap)
    {
        Editor.currentBackgroundWrap = wrap;
        Editor.brushMode = 1;
    }

    public void toMode2(DecalWrap wrap)
    {
        Editor.currentDecalWrap = wrap;
        Editor.brushMode = 2;
    }

    public void toMode3(Wrap wrap)
    {
        Editor.currentWrap = wrap;
        Editor.brushMode = 3;
    }

    public GameThread(int width, int height, int fps, String[] textureList) //создание новой карты
    {
        super("Editor");
        game = new Game(width, height);
        game.init();
        game.fps = fps;
        Brush.init(game);
        game.textureBank.addTexturesFromList(textureList);
        editor = new Editor(game);
        game.map = editor;
    }

    public GameThread(int width, int height, int fps, String[] textureList, Wrap[] wraps) //загрузка карты
    {
        super("Editor");
        game = new Game(width, height);
        game.init();
        game.fps = fps;
        Brush.init(game);
        game.textureBank.addTexturesFromList(textureList);
        editor = Editor.fromWraps(wraps, game);
        game.map = editor;
    }

    @Override
    public void run()
    {
        super.run();
    }


}
