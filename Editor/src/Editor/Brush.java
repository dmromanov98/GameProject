package Editor;

import Graphics.Texture;
import Main.Actor;
import Main.Game;
import Main.Mouse;
import Main.Transform;
import Patterns.Sprite;
import Physics.CollisionSpace;
import Physics.Rectangle;
import Wraps.BackgroundWrap;
import Wraps.DecalWrap;
import Wraps.Wrap;
import org.joml.Vector2f;

import java.util.Vector;

public class Brush
{
    public static void init(Game game)
    {
        try {
            game.textureBank.addFromDisk("shape", "Editor\\src\\Editor\\shape.png");
            Shape.defRectTex = Texture.monoColor(255,0,0,150);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Shape extends Sprite
    {
        public static Texture defRectTex;
        public Shape()
        {
            super(new Vector2f(0, 0), 0f);
        }

        public Shape(Rectangle rect)
        {
            super(new Vector2f(0, 0), 0f);
            texture = defRectTex;
            circle(rect);
            renderIndex = 2;
        }

        public void circle(Rectangle rect)
        {
            this.transform = rect.toTransform();
            if (renderIndex == -1) {
                renderIndex = 2;
            }
        }

        public void circle(Transform transform)
        {
            this.transform = new Transform(transform);
            this.transform.layer = 0f;
            if (renderIndex == -1) {
                renderIndex = 2;
            }
        }

        public void hide()
        {
            renderIndex = -1;
            transform = null;
        }

        @Override
        public void update() {}
    }

    private static final float deltaLayer = -0.00001f;
    private float layerShift = 0; //при множественном создании объектов

    private Shape shape;

    Actor currentActor;

    private Wrap actorWrap;
    private DecalWrap decalWrap;
    private BackgroundWrap backgroundWrap;

    private Vector<Shape> shapesOfCollisionAreas = new Vector<>();
    private Vector<Shape> shapesOfCollisionAreasRemBuffer = new Vector<>();

    public String currentCollisionArea;

    private short mode = 0; //чтобы кэшировать при работе с обработкой клавиатуры и мыши

    private boolean objectIsChosen = false,
            objectIsDecal = false,
            haveToFindObject = false,
            haveToDeleteObject = false,
            objectIsRotating = false,
            objectIsMoving = false,
            rectangleIsCreating = false;

    private byte rectangleStageOfCreate = 0;
    private Vector2f rectShift, rectRectA, rectRectB;

    public Brush(Game game)
    {
        shape = new Shape();
        try {
            shape.texture = game.textureBank.Get("shape").getTexture();
        } catch (Exception e){e.printStackTrace();}
    }

    public void initControls(Game game)
    {
        game.mouse.addMouseAction( new Mouse.MouseAction( Mouse.MOUSE_BUTTON_LEFT, Mouse.BUTTON_PRESS,
                () -> leftMouseHold() ) );

        game.mouse.addMouseAction( new Mouse.MouseAction( Mouse.MOUSE_BUTTON_LEFT, Mouse.BUTTON_RELEASE,
                () -> objectIsMoving = false ) );

        game.mouse.addMouseAction( new Mouse.MouseAction( Mouse.MOUSE_BUTTON_RIGHT, Mouse.BUTTON_PRESS,
                () -> rightMouseClick() ) );

        game.mouse.addMouseAction( new Mouse.MouseAction( Mouse.MOUSE_BUTTON_MIDDLE, Mouse.BUTTON_PRESS,
                () -> middleMouseClick( game.mouse.getAbsoluteMousePos() ) ) );

        game.mouse.addMouseAction( new Mouse.MouseAction( Mouse.MOUSE_BUTTON_MIDDLE, Mouse.BUTTON_RELEASE,
                () -> middleMouseRelease() ) );
    }

    public void leftMouseHold()
    {
        objectIsChosen = true;
        haveToFindObject = true;
    }

    public void rightMouseClick()
    {
        haveToDeleteObject = true;
    }

    private Vector2f firstPos;
    public void middleMouseClick(Vector2f pos)
    {
        firstPos = pos;
        objectIsRotating = true;
    }

    public void middleMouseRelease()
    {
        firstPos = null;
        objectIsRotating = false;
    }

    public void createShapesFromCollisionSpace(CollisionSpace space)
    {
        shapesOfCollisionAreas.clear();
        layerShift = 0;
        for (Rectangle rect:
                space.getRectangles()) {
            Shape shp = new Shape(rect);
            layerShift += deltaLayer;
            shp.transform.layer = layerShift;
            shapesOfCollisionAreas.add(shp);
        }
    }

    public void cleanBrush()
    {
        currentActor = null;
        objectIsRotating = false;
        objectIsChosen = false;
        objectIsDecal = false;
        haveToFindObject = false;
        haveToDeleteObject = false;
        decalWrap = null;
        actorWrap = null;
        backgroundWrap = null;
        layerShift = 0;
        shape.hide();
        objectIsMoving = false;
        rectangleIsCreating = false;
        rectangleStageOfCreate = 0;
        rectShift = null;
        rectRectA = null;
        rectRectB = null;
        currentCollisionArea = null;
        shapesOfCollisionAreas.clear();
    }

    private void changeMode(short targetMode, Editor editor,Game game)
    {
        if (Math.abs(mode) == 4){
            CollisionSpace space = editor.collisionSpaces.get(currentCollisionArea);
            space.clear();
            for (Shape shp:
                    shapesOfCollisionAreas) {
                space.addArea(shp.transform.getRectArea());
            }
        }

        cleanBrush();
        mode = targetMode;
        switch (Math.abs(targetMode)){
            case 1:
                backgroundWrap = Editor.currentBackgroundWrap.copy();
                break;
            case 2:
                decalWrap = Editor.currentDecalWrap.copy();
                break;
            case 3:
                actorWrap = Editor.currentWrap.copy();
                currentActor = actorWrap.getActor(game);
                break;
            case 4:
                currentCollisionArea = Editor.currentCollisionArea;

                if (!editor.collisionSpaces.containsKey(currentCollisionArea))
                    editor.collisionSpaces.put(currentCollisionArea, new CollisionSpace());

                createShapesFromCollisionSpace(editor.collisionSpaces.get(currentCollisionArea));
                break;
        }
    }

    Vector2f lastMousePos = new Vector2f(0,0);

    public void update(Editor editor, Game game)
    {
        if (mode != Editor.brushMode) {
            changeMode(Editor.brushMode, editor, game);
        }

        switch (Math.abs(mode)) {
            case 0: //перетаскиваем объекты
                if (objectIsChosen) { //что-то выделяли??
                    if (currentActor == null) { //на самом деле ничего не выделили? исправляем.
                        objectIsChosen = false;
                        haveToDeleteObject = false;
                    } else {
                        if (objectIsRotating) {
                            Vector2f pos = game.mouse.getAbsoluteMousePos(),
                                     dif = new Vector2f(pos.x - firstPos.x, pos.y - firstPos.y);
                            if (!objectIsDecal) {
                                Transform transform = currentActor.tryToGetTransform();
                                transform.angle = (float) Math.atan2(dif.y, dif.x);
                                shape.circle(transform);
                            }
                            break; //вертим? ну тогда нам явно не стоит объект перемещать
                        }

                        if (haveToFindObject || haveToDeleteObject || objectIsMoving) {
                            Transform transform = currentActor.tryToGetTransform();
                            Vector2f mousePos = game.mouse.getAbsoluteMousePos();
                            if (transform.getRectArea().inArea(mousePos) || objectIsMoving) { //попали курсором?
                                if (haveToDeleteObject) { //объект надо удалить? ну ок :(
                                    currentActor.delete();
                                    currentActor = null;
                                    break;
                                }
                                if (!objectIsDecal) { //объект простой спрайт? ну тогда все просто
                                    transform.translate(mousePos.add(-lastMousePos.x, -lastMousePos.y)); //ок, мы опять попали мышкой в объект и его передвинули
                                    shape.circle(transform);
                                    objectIsMoving = true;
                                }
                                break; //все хорошо. пока до следующей итерации
                            } else { //не попали? ладно. идем ниже и ищем
                                shape.hide();
                            }
                        }
                    }
                }

                shape.hide();
                currentActor = null; //пытаюсь найти новый объект

                for (Actor a :
                        editor.getActors()) { //ищу среди живых объектов

                    Transform transform = null;
                    if(a != null)
                        transform = a.tryToGetTransform();

                    if (transform != null)
                        if (transform.getRectArea().inArea(game.mouse.getAbsoluteMousePos())) {
                            currentActor = a;
                            objectIsDecal = false;
                            break;
                        }
                }

                if (currentActor == null) { //не нашел? ищу среди декалей
                    for (Actor a :
                            editor.getDecals()) {
                        Transform transform = a.tryToGetTransform();
                        if (transform != null)
                            if (transform.getRectArea().inArea(game.mouse.getAbsoluteMousePos())) {
                                currentActor = a;
                                objectIsDecal = true;
                                break;
                            }
                    }
                }

                if (currentActor != null) { //если нашел, сообщаю об этом
                    shape.circle(currentActor.tryToGetTransform());
                    if (haveToFindObject || haveToDeleteObject){
                        objectIsChosen = true;
                    }
                } else {
                    shape.hide();
                }
                break;
            case 1: //создание бэкграундов
                if (backgroundWrap != null && haveToFindObject){
                    editor.getBackgrounds().add( backgroundWrap.getActor(game) );
                    backgroundWrap = null;
                }
                if (haveToDeleteObject) {
                    EditorThread.toMode0();
                    haveToDeleteObject = false;
                }
                break;
            case 2:
                if (decalWrap != null){
                    if (objectIsRotating) { //вращаем?
                        Vector2f pos = game.mouse.getAbsoluteMousePos(),
                                 dif = new Vector2f(pos.x - firstPos.x, pos.y - firstPos.y);
                        decalWrap.transform.angle = (float) Math.atan2(dif.y, dif.x);
                        shape.circle(decalWrap.transform);
                        break; //когда крутим нам не нужно перемещать объект
                    }

                    decalWrap.transform.setPosition(game.mouse.getAbsoluteMousePos());
                    shape.circle(decalWrap.transform);

                    if (haveToFindObject) {
                        decalWrap.transform.layer += layerShift; //чтобы при множественном создании объектов они не были как говно
                        editor.getDecals().add( decalWrap.getActor(game) );
                        layerShift -= deltaLayer;
                    }
                }
                if (haveToDeleteObject) {
                    EditorThread.toMode0();
                    haveToDeleteObject = false;
                }
                break;
            case 3:
                if (actorWrap != null && currentActor != null){
                    Transform transform  = currentActor.tryToGetTransform();
                    if (objectIsRotating) { //вращаем?
                        Vector2f pos = game.mouse.getAbsoluteMousePos(),
                                dif = new Vector2f(pos.x - firstPos.x, pos.y - firstPos.y);

                        transform.angle = (float) Math.atan2(dif.y, dif.x);
                        shape.circle(transform);
                        break; //когда крутим нам не нужно перемещать объект
                    }

                    transform.translate(game.mouse.getAbsoluteMousePos());

                    if (haveToFindObject) {
                        transform.layer += layerShift; //чтобы при множественном создании объектов они не были как говно
                        Actor actor = actorWrap.getActor(game);
                        actor.tryToGetTransform().
                                setPosition(transform.getPosition()).
                                setScale(transform.getScale()).
                                setAngle(transform.angle).
                                setLayer(transform.layer);
                        editor.getActors().add( actor );
                        layerShift -= deltaLayer;
                    }
                }
                if (haveToDeleteObject) {
                    EditorThread.toMode0();
                    haveToDeleteObject = false;
                }
                break;
            case 4:
                for (Shape shp:
                        shapesOfCollisionAreas) {
                    if (shp.willBeRemoved())
                        shapesOfCollisionAreasRemBuffer.add(shp);
                }

                if (!shapesOfCollisionAreasRemBuffer.isEmpty()) {
                    shapesOfCollisionAreas.removeAll(shapesOfCollisionAreasRemBuffer);
                    shapesOfCollisionAreasRemBuffer.clear();
                }

                if(rectangleIsCreating){ //если мы тыкнули в пустое поле, то запускается процесс созания нового прямоугольника
                    //TODO: этот код работает неккоректно. на самом деле область получается какая-то не такая.
                    /*if(haveToFindObject || rectangleStageOfCreate == 0){
                        rectangleStageOfCreate += 1;
                        switch (rectangleStageOfCreate){
                            case 1:
                                rectShift = game.mouse.getAbsoluteMousePos();//первая точка прямоугольника
                                break;
                            case 2:
                                rectRectA = game.mouse.getAbsoluteMousePos().add(-rectShift.x, -rectShift.y);//вторая точка прямоугольника
                                break;
                            case 3://третья точка прямоугольника
                                rectRectB = game.mouse.getAbsoluteMousePos().add(-rectShift.x - rectRectA.x, -rectShift.y - rectRectA.y);
                                rectangleIsCreating = false;
                                Rectangle newRect = new Rectangle(rectShift, rectRectA, rectRectB);
                                Shape shp = new Shape(newRect);
                                layerShift += deltaLayer;
                                shp.transform.layer = layerShift;
                                shapesOfCollisionAreas.add(shp);
                                break;
                        }
                    }
                    if (rectangleStageOfCreate == 2){
                        rectRectB = game.mouse.getAbsoluteMousePos().add(-rectShift.x - rectRectA.x, -rectShift.y - rectRectA.y);
                        shape.circle(new Rectangle(rectShift, rectRectA, rectRectB));
                    }*/
                    if(haveToFindObject || rectangleStageOfCreate == 0) {
                        rectangleStageOfCreate += 1;
                        switch (rectangleStageOfCreate) {
                            case 1:
                                rectShift = game.mouse.getAbsoluteMousePos();//первая точка прямоугольника
                                currentActor = new Shape();
                                currentActor.renderIndex = 2;
                                currentActor.tryToGetTransform().setPosition(rectShift);
                                break;
                            case 2:
                                rectRectA = game.mouse.getAbsoluteMousePos().add(-rectShift.x, -rectShift.y);
                                layerShift += deltaLayer;

                                Shape shp = new Shape();
                                shp.renderIndex = 2;
                                shp.transform.setPosition(rectShift)
                                             .setScale(rectRectA.x, rectRectA.x)
                                             .setLayer(layerShift);
                                layerShift += deltaLayer;
                                shp.transform.layer = layerShift;
                                shp.texture = Shape.defRectTex;
                                shapesOfCollisionAreas.add(shp);
                                currentActor = null;
                                rectangleIsCreating = false;
                                break;
                        }
                    }

                    if (rectangleStageOfCreate == 1){
                        rectRectA = game.mouse.getAbsoluteMousePos().add(-rectShift.x, -rectShift.y);
                        currentActor.tryToGetTransform().setScale(rectRectA.x, rectRectA.x).setLayer(layerShift + deltaLayer);
                    }
                    break;
                }

                if (objectIsChosen) { //что-то выделяли??
                    if (currentActor == null) { //на самом деле ничего не выделили? исправляем.
                        objectIsChosen = false;
                        haveToDeleteObject = false;
                    } else {
                        Transform transform = currentActor.tryToGetTransform();
                        if (objectIsRotating) {
                            Vector2f pos = game.mouse.getAbsoluteMousePos(),
                                    dif = new Vector2f(pos.x - firstPos.x, pos.y - firstPos.y);
                                transform.angle = (float) Math.atan2(dif.y, dif.x);
                                shape.circle(transform);
                            break; //вертим? ну тогда нам явно не стоит объект перемещать
                        }

                        if (haveToFindObject || haveToDeleteObject || objectIsMoving) {
                            Vector2f mousePos = game.mouse.getAbsoluteMousePos();
                            if (transform.getRectArea().inArea(mousePos) || objectIsMoving) { //попали курсором?
                                if (haveToDeleteObject) { //объект надо удалить? ну ок :(
                                    currentActor.delete();
                                    currentActor = null;
                                    break;
                                }
                                transform.translate(mousePos.add(-lastMousePos.x, -lastMousePos.y)); //ок, мы опять попали мышкой в объект и его передвинули
                                shape.circle(transform);
                                objectIsMoving = true;
                                break; //все хорошо. пока до следующей итерации
                            } else { //не попали? ладно. идем ниже и ищем
                                shape.hide();
                            }
                        } else {
                            objectIsMoving = false;
                        }
                    }
                }

                shape.hide();
                currentActor = null; //пытаюсь найти новый объект
                for (Actor a :
                        shapesOfCollisionAreas) {
                    Transform transform = a.tryToGetTransform();
                    if (transform != null)
                        if (transform.getRectArea().inArea(game.mouse.getAbsoluteMousePos())) {
                            currentActor = a;
                            break;
                        }
                }

                if (currentActor != null) { //если нашел, сообщаю об этом
                    shape.circle(currentActor.tryToGetTransform());
                    if (haveToFindObject || haveToDeleteObject){
                        objectIsChosen = true;
                    }
                } else { //не нашли? тогда переходим в режим создания новых областей
                    shape.hide();
                    if (haveToFindObject){
                        rectangleIsCreating = true;
                        rectangleStageOfCreate = 0;
                    }
                }
                break;
        }
        lastMousePos = game.mouse.getAbsoluteMousePos();//для рассчета дельты
        haveToFindObject = false; //очищаю клик мышкb
    }

    public void draw() {
        if (shape.renderIndex > -1)
            shape.draw();
        if (mode == 4)
            for (Shape shp:
                 shapesOfCollisionAreas) {
                if (shp.renderIndex > -1)
                    shp.draw();
            }
    }
}
