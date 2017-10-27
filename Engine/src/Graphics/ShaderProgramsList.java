package Graphics;

import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgramsList
{
    private HashMap<String, Integer> shaders;
    public ShaderProgramsList()
    {
        shaders = new HashMap<>();
    }

    public void CreateShaderProgram(String name, int[] shaders)
    {
        int program = glCreateProgram();
        for (int shader:
             shaders) {
            glAttachShader(program, shader);
        }

        glLinkProgram(program);
        glValidateProgram(program);

        this.shaders.put(name, program);
    }

    public int getShaderProgram(String name)
    {
        return shaders.getOrDefault(name, -1);
    }
}
