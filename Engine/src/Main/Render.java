package Main;

import Graphics.Shader;
import Graphics.ShaderProgramsList;

import java.util.Vector;

public class Render
{
    private Vector< Vector<Actor> > renderLine; //разные слои рендера. 0 -- пол карты и бэкграунд, 1 -- декали,
                                                //2 -- динамические объекты

    public Render()
    {
        renderLine = new Vector<>();
        renderLine.setSize(3);
        for(int i=0; i < 3; i++)
            renderLine.add( new Vector<>() );
    }

    private Shader decalShader;

    public void init()
    {
        try{
            decalShader = ShaderProgramsList.getShaderProgram("default/decal");
        } catch (Exception e){e.printStackTrace();}
    }

    public void addToRenderLine(Actor a)
    {
        if (a.renderIndex != -1)
        {
            renderLine.get(a.renderIndex).add(a);
        }
    }

    public void drawAll() //TODO: обобщить шейдеры для каждого типа объектов
    {
        //map
        for (Actor a:
             renderLine.get(0)) {
            a.draw();
        }

        //decals
        decalShader.enable();
        Camera.toShader(decalShader);
        for (Actor a:
                renderLine.get(1)) {
            a.draw();
        }
        decalShader.disable();

        //dynamic objects
        for (Actor a:
                renderLine.get(2)) {
            a.draw();
        }
    }
}
