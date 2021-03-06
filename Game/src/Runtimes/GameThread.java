/*
* Значит так, основные данные для того, чтобы не забыть:
* 1)у карты должны быть следующие свойства:
*   * содержать хотя бы один спавнер
*   * содержать следующте коллайд спейсес:
*       ** "solids"
*       ** ""
*
*
* */
package Runtimes;

import Main.Game;
import Map.MapWrap;
import Map.Map;
import Objects.Player;
import Objects.Snowball;

public class GameThread extends Thread {
    private int fps;
    private int width, height;
    private String[] textureList;
    private MapWrap mapWrap;

    public GameThread(int width, int height, int fps, String[] textureList) {
        super("Game");
        setPriority(MAX_PRIORITY);
        this.fps = fps;
        this.width = width;
        this.height = height;
        this.textureList = textureList;
        mapWrap = null;
    }

    public Game game;

    private void init() {
        game = new Game(width, height);
        game.init();
        game.fps = fps;
        game.textureBank.addTexturesFromList(textureList);
    }

    @Override
    public void run() {
        super.run();
        init();

        Player.gameThread = this;
        Snowball.gameThread = this;

        ClientThread clientThread = new ClientThread();
        clientThread.playersController = new PlayersController();
        clientThread.start();

        mapWrap = clientThread.getMap();
        game.map = new Map(game, mapWrap);
        game.map.addActor(clientThread.playersController);

        /*TODO: game begins*/
        game.mainloop();
        game.closeGame();
    }


}
