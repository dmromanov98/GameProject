package Editor;

import Main.Camera;
import Main.Game;
import Main.Mouse;
import Main.Transform;
import Physics.Rectangle;
import org.joml.Vector2f;

public class CameraController
{
    private final Game game;

    public static final float thickness = 80f, //in pixels
                              scrollSpeed = 10f,
                              maxRescaleSpeed = 20f;

    private Rectangle leftRect, ceilRect, rightRect, floorRect;

    public CameraController(Game game)
    {
        this.game = game;
        leftRect = new Rectangle(
                new Vector2f(-.5f*game.screenSize[0]  , -.5f*game.screenSize[1]),
                new Vector2f( thickness, 0),
                new Vector2f(0, game.screenSize[1]));
        ceilRect = new Rectangle(
                new Vector2f(-.5f*game.screenSize[0]  , .5f*game.screenSize[1]),
                new Vector2f(0, -thickness),
                new Vector2f(game.screenSize[0], 0));
        rightRect = new Rectangle(
                new Vector2f(.5f*game.screenSize[0]  , .5f*game.screenSize[1]),
                new Vector2f( -thickness, 0),
                new Vector2f(0, -game.screenSize[1]));
        floorRect = new Rectangle(
                new Vector2f(.5f*game.screenSize[0]  , -.5f*game.screenSize[1]),
                new Vector2f(0, thickness),
                new Vector2f(-game.screenSize[0], 0));

        game.mouse.addWheelAction(new Mouse.WheelAction(Mouse.DELTA_WHEEL_ACTION,
                (Float f) -> Camera.getTransform().rescale( (maxRescaleSpeed - f) / maxRescaleSpeed )));
    }

    public void update()
    {
        Vector2f mousePos = game.mouse.getMousePos();
        Transform transform = Camera.getTransform();

        if (leftRect.inArea(mousePos))
            transform.translate(-scrollSpeed, 0);
        if (ceilRect.inArea(mousePos))
            transform.translate(0, scrollSpeed);
        if (rightRect.inArea(mousePos))
            transform.translate(scrollSpeed, 0);
        if (ceilRect.inArea(mousePos))
            transform.translate(0, -scrollSpeed);
    }
}
