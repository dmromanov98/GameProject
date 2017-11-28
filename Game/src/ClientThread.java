import Map.MapWrap;
import Player.Player;
import Wraps.PlayerWrap;

import java.util.Vector;

public class ClientThread extends Thread
{
    public PlayersController playersController = new PlayersController();

    public synchronized void addPlayer(PlayerWrap wrap) //получаем магическим образом с серва врап, дальше тыркаем вот этот метод
    {

    }

    public ClientThread(){}

    @Override
    public void run() {
        super.run();
        /*TODO: initialize client*/
        /*TODO: run client*/
    }

    public synchronized MapWrap getMap()
    {
        /*TODO: getting map from the server*/
        return null;
    }



}
