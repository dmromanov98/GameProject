package Editor;

import Main.Game;

public class GameThread extends Thread
{
    public Game game;

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
