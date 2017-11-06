package Patterns.Map;

import Graphics.Shader;
import Graphics.ShaderProgramsList;
import Graphics.Texture;
import Graphics.VertexArray;
import Main.Actor;
import Main.Transform;
import MyMath.Matrix4f;

public class Decal extends Actor
{
    private final Texture texture;
    private final Matrix4f matrix;
    private final VertexArray mesh = VertexArray.quad;

    private static Shader shader;

    public static void init()
    {
        try {
            shader = ShaderProgramsList.getShaderProgram("default/decal");
        }catch (Exception e){e.printStackTrace(); shader = null;}
    }

    public Decal(Transform transform, Texture texture)
    {
        this.texture = texture;
        matrix = transform.getMatrix();
        alive = false;
        renderIndex = 1;
    }

    @Override
    public void update() {}

    @Override
    public void draw()
    {
        shader.setUniformMat4f("model", matrix);
        texture.bind(shader);
        mesh.draw();
        texture.unbind();
    }
}
