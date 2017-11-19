package Editor;

import Main.Actor;
import Main.Game;
import Main.Mouse;
import Main.Transform;
import Patterns.Sprite;
import Physics.Rectangle;
import Wraps.BackgroundWrap;
import Wraps.DecalWrap;
import Wraps.Wrap;
import org.joml.Vector2f;

public class Brush {
    public static void init(Game game) {
        try {
            game.textureBank.addFromDisk("shape", "Engine\\src\\Map\\shape.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Shape extends Sprite {
        public Shape() {
            super(new Vector2f(0, 0), 0);
        }

        public void circle(Rectangle rect) {
            this.transform = rect.toTransform();
            if (renderIndex == -1) {
                renderIndex = 2;
            }
        }

        public void circle(Transform transform) {
            this.transform = new Transform(transform);
            if (renderIndex == -1) {
                renderIndex = 2;
            }
        }

        public void hide() {
            renderIndex = -1;
        }

        @Override
        public void update() {
        }
    }

    private Shape shape;

    private Actor currentActor;

    private Wrap actorWrap;
    private DecalWrap decalWrap;
    private BackgroundWrap backgroundWrap;

    private byte mode = 0; //чтобы кэшировать при работе с обработкой клавиатуры и мыши

    private boolean objectIsChosen = false,
            objectIsDecal = false,
            haveToFindObject = false,
            haveToDeleteObject = false,
            objectIsRotating = false;

    public Brush()
    {
        shape = new Shape();
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
        mode = 0;
    }

    private void changeMode(byte targetMode)
    {
        cleanBrush();
        switch (targetMode){
            case 0: break;
            case 1: backgroundWrap = Editor.currentBackgroundWrap.copy(); break;
            case 2: decalWrap = Editor.currentDecalWrap.copy(); break;
            case 3: actorWrap = Editor.currentWrap.copy(); break;
        }
        mode = targetMode;
    }

    Vector2f lastMousePos = new Vector2f(0,0);

    public void update(Editor editor, Game game)
    {
        if (mode != Editor.brushMode) {
            changeMode(Editor.brushMode);
        }

        switch (mode) {
            case 0: //перетаскиваем объекты
                if (objectIsChosen) { //что-то выделяли??
                    if (currentActor == null) { //на самом деле ничего не выделили? исправляем.
                        objectIsChosen = false;
                    } else {
                        if (objectIsRotating) {
                            Vector2f pos = game.mouse.getAbsoluteMousePos(),
                                     dif = new Vector2f(pos.x - firstPos.x, pos.y - firstPos.y);
                            if (!objectIsDecal) {
                                currentActor.tryToGetTransform().angle = (float) Math.atan2(dif.y, dif.x);
                            }
                        }

                        if (haveToFindObject || haveToDeleteObject) {
                            Transform transform = currentActor.tryToGetTransform();
                            Vector2f mousePos = game.mouse.getAbsoluteMousePos();
                            if (transform.getRectArea().inArea(mousePos)) { //попали курсором?
                                if (haveToDeleteObject) { //объект надо удалить? ну ок :(
                                    currentActor.delete();
                                    currentActor = null;
                                    break;
                                }
                                if (!objectIsDecal) { //объект простой спрайт? ну тогда все просто
                                    transform.move(mousePos.add(-lastMousePos.x, -lastMousePos.y)); //ок, мы опять попали мышкой в объект и его передвинули
                                }
                                break; //все хорошо. пока до следующей итерации
                            } else { //не попали? ладно. идем ниже и ищем
                                shape.hide();
                            }
                        }
                    }
                }

                currentActor = null; //пытаюсь найти новый объект
                for (Actor a :
                        editor.getActors()) { //ищу среди живых объектов
                    Transform transform = a.tryToGetTransform();
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
                    if (haveToFindObject){
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

                    decalWrap.transform.move(game.mouse.getAbsoluteMousePos().add(-lastMousePos.x, -lastMousePos.y));
                    shape.circle(decalWrap.transform);
                }
                break;
            case 3: break;

        }
        lastMousePos = game.mouse.getAbsoluteMousePos();//для рассчета дельты
        haveToFindObject = false; //очищаю клик мышки
    }

    public void draw() {
        shape.draw();
    }
}
