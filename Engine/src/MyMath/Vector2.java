package MyMath;

public class Vector2
{
    public Double x, y;
    public static Vector2 Null = new Vector2(0,0);

    public Vector2(double x, double y)
    {
        this.x = x; this.y = y;
    }

    public Vector2(double[] cords)
    {
        this.x = cords[0];
        this.y = cords[1];
    }

    public Vector2(Vector2 v)
    {
        this.x = new Double(v.x);
        this.y = new Double(v.y);
    }

    public static Vector2 sum(Vector2 v1, Vector2 v2)
    {
        return new Vector2(v1.x + v2.x, v1.y + v2.y);
    }

    public static Vector2 dif(Vector2 v1, Vector2 v2)
    {
        return new Vector2(v1.x - v2.x, v1.y - v2.y);
    }

    public void inc(Vector2 v)
    {
        this.x += v.x;
        this.y += v.y;
    }

    public void inc(double x, double y)
    {
        this.x += x;
        this.y += y;
    }

    public void dec(Vector2 v)
    {
        this.x -= v.x;
        this.y -= v.y;
    }

    public void dec(double x, double y)
    {
        this.x -= x;
        this.y -= y;
    }

    public void mult(double k)
    {
        this.x *= k;
        this.y *= k;
    }

    public double length()
    {
        return Math.sqrt(x*x + y*y);
    }

    public void normalize()
    {
        double len = this.length();
        this.x /= len;
        this.y /= len;
    }

    public void rotate(double deg)
    {
        double x = this.x, y = this.y;
        this.x = x*Math.cos(deg) - y*Math.sin(deg);
        this.y = x*Math.sin(deg) + y*Math.cos(deg);
    }

    public static Vector2 rotate(Vector2 v, double deg)
    {
        return new Vector2(v.x*Math.cos(deg) - v.y*Math.sin(deg),
                           v.x*Math.sin(deg) + v.y*Math.cos(deg));
    }

    public void rotateAround(Vector2 v, double deg)
    {
        Vector2 rel_vec = Vector2.dif(this, v);
        rel_vec.rotate(deg);
        this.x = v.x + rel_vec.x;
        this.y = v.y + rel_vec.y;
    }

    @Override
    public String toString() {
        return super.toString() + " x =" + x + "; y =" + y;
    }
}
