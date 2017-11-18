package Editor;

import Main.Game;
import Wraps.Wrap;

public class GameThread extends Thread
{
    public Game game;
    public Editor editor;

    public GameThread(int width, int height, int fps, String[] textureList)
    {
        super("Editor");
        game = new Game(width, height);
        game.init();
        game.fps = fps;
        editor = new Editor();
        game.map = editor;
        game.textureBank.addTexturesFromList(textureList);
    }



    @Override
    public void run()
    {
        super.run();

    }


}
