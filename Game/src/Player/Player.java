package Player;

import Patterns.Sprite;
import Physics.PhysTransform;
import Runtimes.GameThread;

public class Player extends Sprite
{
    public static GameThread gameThread;
    public float life;

    public Player()
    {}

    public static final String[] areas = {"solids", ""};

    public void spawn(Spawner spawner)
    {
        life = 1000f;
        transform = new PhysTransform(gameThread.game.map, 80f, areas);
    }

    @Override
    public void update() {

    }
}
