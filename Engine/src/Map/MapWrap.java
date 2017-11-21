package Map;

import Physics.CollideArea;
import Physics.CollisionSpace;
import Wraps.Wrap;

import java.util.HashMap;
import java.util.Vector;

public class MapWrap
{
    public MapWrap(Vector<Wrap> objects, HashMap<String, CollisionSpace> collideAreas)
    {
        this.collideAreas = new HashMap<>(collideAreas);

        for (CollideArea area:
             this.collideAreas.values()) {
            area = area.copy();
        }

        this.objects = new Vector<>(objects);

        for (Wrap w:
             this.objects) {
            w = w.copy();
        }
    }

    public final Vector<Wrap> objects;
    public final HashMap<String, CollisionSpace> collideAreas;
}
