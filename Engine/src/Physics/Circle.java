package Physics;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Circle extends CollideArea
{
    private Vector3f center;
    private float radius;

    public Circle( Vector3f center, float radius)
    {
        this.center = new Vector3f(center);
        this.radius = radius;
    }

    @Override
    public boolean inArea(Vector2f dot)
    {
        return dot.sub(new Vector2f(center.x, center.y)).length() <= radius;
    }
}
