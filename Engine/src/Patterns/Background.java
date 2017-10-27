package Patterns;

import Graphics.Shader;
import Graphics.ShaderProgramsList;
import Graphics.Texture;
import Graphics.VertexArray;
import Main.Actor;
import Main.Camera;
import Main.Transform;
import org.joml.Vector2f;

public abstract class Background extends Actor
{
    public static Shader defaultShader;
    public static VertexArray mesh;

    public static void init()
    {
        mesh = VertexArray.quad;
        try {
            defaultShader = ShaderProgramsList.getShaderProgram("default/background");
        } catch (ShaderProgramsList.ShaderProgramIsNotExistException e) {
            throw new Error("Cannot find default background shader. R u dumb? DO NOT MESS WITH THE ENGINE'S PATH!!!");
        }
    }

    public Transform transform;
    public Texture texture;
    public Shader shader;

    public Background(Texture texture)
    {
        visible = true;
        this.texture = texture;
        shader = defaultShader;
        float max = Float.max(texture.WIDTH, texture.HEIGHT); //чтобы дважды не писать
        transform = new Transform().setScale(new Vector2f( texture.WIDTH/max, texture.HEIGHT/max));
        transform.layer = .99999f;
    }

    @Override
    public final void draw()
    {
        shader.enable();
        Camera.toShader(shader);
        transform.openGLOut().sendToShader(shader);
        texture.bind(shader);
        mesh.render();
        shader.disable();
    }
}
