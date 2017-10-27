package Main;

import Graphics.ShaderCompiler;
import Graphics.ShaderProgramsList;
import Graphics.VertexArray;
import Patterns.Background;
import Utils.File;
import Patterns.Sprite;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.lwjgl.glfw.GLFWVidMode;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;

import static org.lwjgl.system.MemoryUtil.NULL;

//import static org.lwjgl.opengl.GL11.*;
//import static org.lwjgl.opengl.GL13.GL_TEXTURE1;

public class Game {
    private final String[] defaultClasses = {"sprite", "background"};

    private int[] screenSize = {0, 0}; // [0] -- WIDTH; [1] -- HEIGHT
    private long window;

    public Input input;
    public Mouse mouse;
    public Vector<Runnable> initQueue = new Vector<>();
    public TextureBank textureBank = new TextureBank();

    private Vector<Actor> actors;
    private Vector<Actor> actorsRemBuffer;

    public Game(int width, int height)
    {
        this.screenSize[0] = width; this.screenSize[1] = height;
        this.actors = new Vector<>();
        this.actorsRemBuffer = new Vector<>();
    }

    private void initClasses()
    {
        VertexArray.init();//preparing common meshes
        Sprite.init();
        Background.init();
    }

    private void loadAndInitDefaultShaderPrograms()
    {
        ShaderCompiler.addShadersFromAList( File.loadTextfilesFromAList("Engine/shaders/shader_list.txt") );
        ShaderCompiler.printAllShaders();
        for (String className:
             defaultClasses) {
            int[] shaders = {
                    ShaderCompiler.getShader("default/" + className + "/vertex.default"),
                    ShaderCompiler.getShader("default/" + className + "/fragment.default")
            };

            ShaderProgramsList.CreateShaderProgram("default/" + className, shaders);
        }
    }

    public void init() throws Error
    {
        if (!glfwInit()) {
            throw new Error("Could not initialize GLFW!");
        }

        //creating a window
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
        window = glfwCreateWindow(screenSize[0], screenSize[1], "OpenGL learning", NULL, NULL);

        if (window == NULL) {
            throw new Error("Could not create GLFW window!");
        }

        System.out.println("Window is created.");

        //initializing inputs
        mouse = new Mouse(window, screenSize);
        input = new Input();

        //moving the window to the window of the screen
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidmode.width() - screenSize[0]) / 2,
                                 (vidmode.height() - screenSize[1]) / 2);

        //another initializing of OpenGL
        glfwMakeContextCurrent(window);
        createCapabilities();

        //setting up the keyboard
        glfwSetKeyCallback(window, input);
        //glfwSetInputMode(window, GLFW_STICKY_KEYS, 1);

        //TODO: delete this. render settings must be inside of "draw" methods
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_ALPHA_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        System.out.println("OpenGL: " + glGetString(GL_VERSION));

        //setting up the camera
        glViewport(0, 0, screenSize[0], screenSize[1]);
        Camera.initCamera(screenSize[0], screenSize[1]);

        //loading shaders
        loadAndInitDefaultShaderPrograms();

        //initializing classes
        initClasses();

        //initializing customs
        for (Runnable initMethod:
             initQueue) {
            initMethod.run();
        }
        initQueue.clear();

        //for beauty
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
    }

    private void updateActors()
    {
        for (Actor actor : actors) {
            if (actor.alive)
                actor.update();
            if (actor.willBeRemoved())
                actorsRemBuffer.add(actor);
        }

        if (!actorsRemBuffer.isEmpty())
            actors.removeAll(actorsRemBuffer);
    }

    private void drawActors()
    {
        for (Actor actor : actors)
            if (actor.visible)
                actor.draw();
    }

    public void addActor(Actor actor)
    {
        actors.add(actor);
    }

    private void update()
    {
        glfwPollEvents();
        input.updateInput();
        mouse.updateMouse();
        updateActors();
    }

    private void render()
    {
        Camera.update();

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        drawActors();

        int error = glGetError();
        if (error != GL_NO_ERROR)
            System.out.println(error);

        glfwSwapBuffers(window);
    }

    public void mainloop(int fps)
    {
        glfwShowWindow(window);
        System.out.println("Window is showed.");

        System.out.println("Mainloop started.");
        while (true){
            update();
            render();
            if (glfwWindowShouldClose(window)) {
                System.out.println("Window is closed.");
                break;
            }
            //frames++;
            syncFrameRate(fps, System.nanoTime());
            //if (frames % FPS == 0)
            //    System.out.println("Frames from start: " + frames);
        }

        glfwDestroyWindow(window);
        glfwTerminate();
    }

    private void syncFrameRate(float fps, long lastNanos) {
        long targetNanos = lastNanos + (long) (1_000_000_000.0f / fps) - 1_000_000L;  // subtract 1 ms to skip the last sleep call
        try {
            while (System.nanoTime() < targetNanos) {
                Thread.sleep(1);
            }
        }
        catch (InterruptedException ignore) {}
    }
}
