package Objects;

import Main.Actor;
import Main.Transform;

public class Spawner extends Actor
{
    public Transform transform;

    public Spawner(Transform transform)
    {
        renderIndex = -1;

    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {}
}
