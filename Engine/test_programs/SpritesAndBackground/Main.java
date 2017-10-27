package SpritesAndBackground;

import Graphics.Texture;
import Main.Game;
import Patterns.SimpleCameraController;
import org.joml.Vector2f;

import java.util.Random;

public class Main implements Runnable {
    private Thread thread;
    private Game game;
    private int WIDTH = 800, HEIGHT = 600;

    public void start() {
        thread = new Thread(this, "Game");
        thread.run();
    }

    private void createGuys(int count, Texture tex) {
        Random random = new Random();
        float x, y;
        for (int i = 0; i < count; i++) {
            x = random.nextFloat() * WIDTH - .5f * WIDTH;
            y = random.nextFloat() * HEIGHT - .5f * HEIGHT;
            SquaredGuys a = new SquaredGuys(new Vector2f(x, y));
            a.transform.rotate((float) (2 * Math.PI * random.nextDouble()));
            a.texture = tex;
            a.transform.layer = (float) i / count;
            game.addActor(a);
            //System.out.println(triangles[i]);
        }
    }

    @Override
    public void run() {
        game = new Game(WIDTH, HEIGHT);

        game.init();

        try {
            game.textureBank.addFromDisk("dick", "Engine/test_resources/dick.png");
            game.textureBank.addFromDisk("space", "Engine/test_resources/cassiopeia1280.jpeg");
        } catch (Exception e) { e.printStackTrace(); return;}

        game.addActor(new SimpleCameraController(game));

        //Camera.getTransform().translate(0, 0);
        try {
            game.addActor(new MyBack(game.textureBank.Get("space").getTexture()));
            createGuys(100, game.textureBank.Get("dick").getTexture());
        } catch (Exception e) {e.printStackTrace(); return;}

        game.mainloop(30);
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
