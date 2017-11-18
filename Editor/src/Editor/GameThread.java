package Editor;

import Main.Game;
import Wraps.Wrap;

public class GameThread extends Thread
{
    public Game game;
    public static Wrap currentWrap = null;
    public static byte brushMode = 0; //0 -- редачим то, что видим, 1 -- создать бэкграунд, 2 -- ставить спрайты, 3 -- ставить декали
    public Editor editor;

    public GameThread(int width, int height, int fps)
    {
        super("Editor");
        game = new Game(width, height);
        game.init();
        game.fps = fps;
        editor = new Editor();
        game.map = editor;
    }



    @Override
    public void run()
    {
        super.run();

    }


}
