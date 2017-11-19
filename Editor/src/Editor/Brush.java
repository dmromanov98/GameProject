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
            super(new Vector2f(0, 0), .9999f);
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

    private boolean objectIsChosen = false,
            objectIsDecal = false,
            haveToFindObject = false,
            haveToDeleteObject = false;

    public Brush() {
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
    }

    public void middleMouseHold(Vector2f pos) //вращение объектов
    {
        if (currentActor != null && objectIsChosen && !objectIsDecal){
            Vector2f dif = new Vector2f(pos.x - firstPos.x, pos.y - firstPos.y);
            currentActor.tryToGetTransform().angle = (float)Math.atan2( dif.y, dif.x );
        }
    }

    public void update(Editor editor, Mouse mouse)
    {
        switch (Editor.brushMode) {
            case 0: //перетаскиваем объекты
                if (objectIsChosen) { //что-то выделяли??
                    if (currentActor == null) { //на самом деле ничего не выделили? исправляем.
                        objectIsChosen = false;
                    } else {
                        if (haveToFindObject || haveToDeleteObject) {
                            Transform transform = currentActor.tryToGetTransform();
                            Vector2f mousePos = mouse.getAbsoluteMousePos();
                            if (transform.getRectArea().inArea(mousePos)) { //попали курсором?
                                if (haveToDeleteObject) { //объект надо удалить? ну ок :(
                                    currentActor.delete();
                                    currentActor = null;
                                    break;
                                }
                                if (!objectIsDecal) { //объект простой спрайт? ну тогда все просто
                                    transform.move(mousePos); //ок, мы опять попали мышкой в объект и его передвинули
                                }
                                haveToFindObject = false;
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
                        if (transform.getRectArea().inArea(mouse.getAbsoluteMousePos())) {
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
                            if (transform.getRectArea().inArea(mouse.getAbsoluteMousePos())) {
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

                haveToFindObject = false;

                break;
            case 1: break;
        }
    }

    public void draw() {
        shape.draw();
    }
}
