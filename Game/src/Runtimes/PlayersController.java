package Runtimes;

import Main.Actor;
import Player.Player;

import java.util.Vector;

public class PlayersController extends Actor
{
    public Vector<Player> players, playersDelBuffer;

    public PlayersController()
    {
        renderIndex = 2;
        this.players = new Vector<>();
    }

    public synchronized Vector<Player> getOrAddPlayer(Player player) //хихи, маленький лайфкак, чтобы не было приколов с потоками
    {
        if (player != null)
            players.add(player);

        return players;
    }

    @Override
    public void update()
    {
        for (Player p:
                getOrAddPlayer(null)) {
            p.update();
        }
    }

    @Override
    public void draw()
    {
        for (Player p:
                getOrAddPlayer(null)) {
            p.draw();
        }
    }
}
