package SpritesAndBackground;

import Graphics.Texture;
import Main.Game;
import Main.Transform;
import Main.Input;
import Main.Mouse;
import Map.Decal;
import Map.Map;
import Patterns.SimpleCameraController;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.util.Random;

public class Main implements Runnable {
    private Thread thread;
    private Game game;
    private final int WIDTH = 800, HEIGHT = 600;

    public void start() {
        thread = new Thread(this, "Game");
        thread.run();
    }

    private Random random = new Random();
    private float scaleMul = 3;

    public Map createMap(int dickCount, int planetCount) {
        Map res = new Map();

        try {
            res.addActor(new SimpleCameraController(game));

            float x, y, scale;

            Texture[] planetTexs = {
                    game.textureBank.Get("planet1").getTexture(),
                    game.textureBank.Get("planet2").getTexture(),
                    game.textureBank.Get("planet3").getTexture(),
                    game.textureBank.Get("planet4").getTexture()
            };

            Texture dickTex = game.textureBank.Get("dick").getTexture();

            int len = planetTexs.length;
            for (int i = 0; i < planetCount; i++) {
                x = scaleMul * (random.nextFloat() * WIDTH - .5f * WIDTH);
                y = scaleMul * (random.nextFloat() * HEIGHT - .5f * HEIGHT);
                scale = 30 + random.nextFloat() * 30;
                Transform transform = new Transform().rotate((float) (2 * Math.PI * random.nextDouble()))
                        .translate(new Vector2f(x, y))
                        .setScale(new Vector2f(scale, scale));
                transform.layer = (float) i / planetCount / 2;
                res.addDecal(new Decal(transform, planetTexs[random.nextInt(len)]));
            }

            for (int i = 0; i < dickCount; i++) {
                x = scaleMul * (random.nextFloat() * WIDTH - .5f * WIDTH);
                y = scaleMul * (random.nextFloat() * HEIGHT - .5f * HEIGHT);
                SquaredGuys a = new SquaredGuys(new Vector2f(x, y), game.mouse);
                a.transform.rotate((float) (2 * Math.PI * random.nextDouble()));
                a.texture = dickTex;
                a.transform.layer = (float) i / dickCount / 2 + .5f;
                res.addActor(a);
            }

            FunObject funObject = new FunObject(game.mouse);
            funObject.texture = game.textureBank.Get("shape").getTexture();
            res.addActor(funObject);

            res.addBackground(new MyBack(game.textureBank.Get("space").getTexture()));

        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("something gone wrong");
        }
        return res;
    }

    @Override
    public void run() {
        game = new Game(WIDTH, HEIGHT);

        game.init();

        try {
            game.textureBank.addFromDisk("dick", "Engine/test_resources/dick.png");
            game.textureBank.addFromDisk("space", "Engine/test_resources/cassiopeia1280.jpeg");
            game.textureBank.addFromDisk("planet1", "Engine/test_resources/planet1.png");
            game.textureBank.addFromDisk("planet2", "Engine/test_resources/planet2.png");
            game.textureBank.addFromDisk("planet3", "Engine/test_resources/planet3.png");
            game.textureBank.addFromDisk("planet4", "Engine/test_resources/planet4.png");
            game.textureBank.addFromDisk("shape", "Engine/test_resources/shape.png");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        System.out.println(game.textureBank);

        game.input.addKeyAction(new Input.KeyAction(GLFW.GLFW_KEY_Q, Input.KEY_PRESS,
                () -> game.mouse.setMousePos(new Vector2f(0, 0))));

        game.mouse.addMouseAction(new Mouse.MouseAction(Mouse.MOUSE_BUTTON_LEFT, Mouse.BUTTON_HOLD,
                () -> SquaredGuys.mouseHold = true));

        game.map = createMap(100, 300);

        game.fps = 60;
        game.mainloop();
        game.closeGame();
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
