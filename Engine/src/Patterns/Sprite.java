package Patterns;

import Graphics.Shader;
import Graphics.ShaderCompiler;
import Graphics.Texture;
import Graphics.VertexArray;
import Main.Actor;
import Main.Camera;
import Main.Game;
import Main.Transform;
import Utils.File;
import org.joml.Vector2f;

public abstract class Sprite extends Actor
{
    private static float[] vertices = {
            0.5f,  0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            -0.5f,  0.5f, 0.0f
    };

    private static float[] textureCords ={
            1f, 1f,
            0f, 1f,
            0f, 0f,
            1f, 0f
    };

    private static byte[] indices = {
            0, 1, 3,
            1, 2, 3
    };

    private static VertexArray mesh;
    private static void createDrawConfiguration()
    {
        mesh = new VertexArray(vertices, indices, textureCords);
    }

    public static Shader defaultShader;
    private static void loadDefaultShaders()
    {
        int[] myShaders = {
                ShaderCompiler.getShader("vertex.test") , ShaderCompiler.getShader("fragment.test")
        };
        Game.shaderProgramsList.CreateShaderProgram( "test", myShaders );
        defaultShader = new Shader("test");
    }

    private static Texture defaultTexture;
    public static void init()
    {
        createDrawConfiguration();
        loadDefaultShaders();
        defaultTexture = Texture.monoColor(255, 255, 255, 255);
    }

    //------------------------------------------------------------------------

    public Transform transform;
    public Shader shader;
    public Texture texture = defaultTexture;

    public Sprite(Vector2f position, float layer)
    {
        transform = new Transform();
        transform.translate(position);
        transform.layer = layer;
        shader = defaultShader;
        this.visible = true;
    }

    public void draw()
    {
        shader.enable();
        Camera.toShader(shader);
        transform.openGLOut().sendToShader(shader);
        texture.bind(shader);
        mesh.render();
        shader.disable();
    }

}