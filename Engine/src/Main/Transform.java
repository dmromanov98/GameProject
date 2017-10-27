package Main;

import Graphics.Shader;
import org.joml.Vector2f;

/*
нужно будет еще уточнить ебанину эту. трабла с непонятностью, как вообще нормальные люди сохраняют корды и прочее
я не думаю, что пересоздавать матрицу каждый раз, когда я передавигаю что-то на чих есть классное решение проблемы
в случае работы с ссаными квадратиками хранить 16 флоатов чтобы двигать 4 вертекса? такое. матрица занимает памяти
больше, чем сам квадрат. оставлю этот вопрос открытым. мб ван дей перепишу это на векторы обратно.
*/
public class Transform
{
    private Vector2f position;
    private Vector2f scale;
    private float angle;
    public float layer;

    public static class OpenGLSpecification
    {
        public float[] position = {0, 0, 0};
        public float[] angle = {0};
        public float[] scale = {1, 1};

        public void sendToShader( Shader shader)
        {
            shader.setUniform3f("absolute_position", position);
            shader.setUniform1f("angle", angle);
            shader.setUniform2f("scale", scale);
        }

        @Override
        public String toString()
        {
            return "pos: {" + position[0] + ';' + position[1] + ';' + position[2] +
                    "}, angle: " + angle[0] +
                    ", size: {" + scale[0] + ';' + scale[1] + "}";
        }
    }

    public Transform()
    {
        position = new Vector2f(0, 0);
        layer = 0;
        angle = 0;
        scale = new Vector2f(0, 0);
    }

    public Transform setPosition(Vector2f position)
    {
        this.position = new Vector2f(position);
        return this;
    }

    public Transform setPosition(float x, float y)
    {
        this.position.set(x, y);
        return this;
    }

    public Vector2f getPosition()
    {
        return new Vector2f(position);
    }

    public float getAngle() {
        return angle;
    }

    public Vector2f getScale() {
        return scale;
    }

    public Transform translate(Vector2f v)
    {
        position.add(v);
        return this;
    }

    public Transform translate(float x, float y)
    {
        position.add(x, y);
        return this;
    }

    public Transform moveForward(float dist)
    {
        this.translate(new Vector2f( dist*(float)(Math.cos(angle)),
                                     dist*(float)(Math.sin(angle)) ));
        return this;
    }

    public Transform move(Vector2f vec)
    {
        double sin_res = Math.sin(angle);
        double cos_res = Math.cos(angle);
        this.translate(
                (float)(vec.x*cos_res - vec.y*sin_res),
                (float)(vec.x*sin_res + vec.y*cos_res)
        );
        return this;
    }

    public Transform turn(float angle)
    {
        this.angle += angle;
        return this;
    }

    public Transform rotate(float angle)
    {
        this.angle = angle;
        return this;
    }

    public Transform rescale(float scale)
    {
        this.scale = this.scale.mul(scale);
        return this;
    }

    public Transform scaleX(float scale)
    {
        this.scale.x*= scale;
        return this;
    }

    public Transform scaleY(float scale)
    {
        this.scale.y*= scale;
        return this;
    }

    public Transform setScale(Vector2f scale)
    {
        this.scale = scale;
        return this;
    }

    //структура такова: x, y, layer, angle, scale. Раскладываю на vector3  float  vector2
    private OpenGLSpecification oglResult = new OpenGLSpecification();//чтобы не пересоздавать лишний раз
    //for OpenGL
    public OpenGLSpecification openGLOut()
    {
        oglResult.position[0] = position.x;
        oglResult.position[1] = position.y;
        oglResult.position[2] = layer;
        oglResult.angle[0] = angle;
        oglResult.scale[0] = scale.x;
        oglResult.scale[1] = scale.y;

        //System.out.println(oglResult);

        return oglResult;
    }
}