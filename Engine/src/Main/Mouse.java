package Main;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import java.util.Vector;

public class Mouse
{
    public static int BUTTON_PRESS = 1;
    public static int BUTTON_RELEASE = 0;
    public static int BUTTON_HOLD = 3;

    public static int MOUSE_BUTTON_RIGHT = GLFW.GLFW_MOUSE_BUTTON_RIGHT;
    public static int MOUSE_BUTTON_LEFT = GLFW.GLFW_MOUSE_BUTTON_LEFT;
    public static int MOUSE_BUTTON_MIDDLE = GLFW.GLFW_MOUSE_BUTTON_MIDDLE;

    static private class MousePos extends GLFWCursorPosCallback
    {
        public Vector2f pos = new Vector2f(0,0);
        private long window;

        public MousePos(long window, float xpos, float ypos)
        {
            this.window = window;
            setMousePos(xpos, ypos);
        }

        public void setMousePos(float x, float y)
        {
            this.pos.x = x;
            this.pos.y = y;
            GLFW.glfwSetCursorPos(window, pos.x, pos.y);
        }

        @Override
        public void invoke(long window, double xpos, double ypos)
        {
            this.pos.x = (float)xpos;
            this.pos.y = (float)ypos;
        }
    }

    static private class MouseButton extends GLFWMouseButtonCallback
    {
        public boolean holdLB, holdRB, holdMB;
        private Vector<MouseAction> mouseActions;

        public MouseButton(Vector<MouseAction> mouseActions)
        {
            holdLB = false;
            holdMB = false;
            holdRB = false;
            this.mouseActions = mouseActions;
        }

        @Override
        public void invoke(long window, int button, int action, int mods)
        {
            for (MouseAction mouseAction:
                 mouseActions) {
                mouseAction.check(button, action);
            }

            switch (button){
                case GLFW.GLFW_MOUSE_BUTTON_RIGHT:
                    this.holdRB = (action == BUTTON_PRESS) || (this.holdRB && !(action == BUTTON_RELEASE));
                    break;
                case GLFW.GLFW_MOUSE_BUTTON_LEFT:
                    this.holdLB = (action == BUTTON_PRESS) || (this.holdLB && !(action == BUTTON_RELEASE));
                    break;
                case GLFW.GLFW_MOUSE_BUTTON_MIDDLE:
                    this.holdMB = (action == BUTTON_PRESS) || (this.holdMB && !(action == BUTTON_RELEASE));
                    break;
            }
        }
    }

    public static class MouseAction
    {
        public int button, action;
        public Runnable mouseMethod;

        public MouseAction(int button, int action, Runnable mouseMethod)
        {
            this.button = button;
            this.action = action;
            this.mouseMethod = mouseMethod;
        }

        public void check(int key, int action)
        {
            if (this.button == key && this.action == action)
                mouseMethod.run();
        }
    }


    private MousePos mousePos;
    private MouseButton mouseButton;
    private int[] screenSize;
    private Vector<MouseAction> mouseActions;
    private Vector<MouseAction> mouseActionsBuffer;

    public Mouse(long window, int[] screenSize)
    {
        this.screenSize = screenSize;
        mouseActions = new Vector<>();
        mouseActionsBuffer = new Vector<>();

        mousePos = new MousePos(window, screenSize[0]/2, screenSize[1]/2);
        mouseButton = new MouseButton(mouseActions);

        GLFW.glfwSetMouseButtonCallback(window, mouseButton);
        GLFW.glfwSetCursorPosCallback(window, mousePos);
    }

    public void addMouseAction(MouseAction mouseAction)
    {
        mouseActionsBuffer.add(mouseAction);
    }

    public Vector2f getMousePos()
    {
        return mousePos.pos;
    }

    public void updateMouse()
    {
        for (MouseAction mouseAction:
                mouseActions) {
            if (mouseAction.action == BUTTON_HOLD){
                switch (mouseAction.button){
                    case GLFW.GLFW_MOUSE_BUTTON_RIGHT:
                        if (mouseButton.holdRB)
                            mouseAction.mouseMethod.run();
                        break;
                    case GLFW.GLFW_MOUSE_BUTTON_LEFT:
                        if (mouseButton.holdLB)
                            mouseAction.mouseMethod.run();
                        break;
                    case GLFW.GLFW_MOUSE_BUTTON_MIDDLE:
                        if (mouseButton.holdMB)
                            mouseAction.mouseMethod.run();
                        break;
                }}
        }

        if (!mouseActionsBuffer.isEmpty()){
            mouseActions.addAll(mouseActionsBuffer);
            mouseActionsBuffer.clear();
        }
    }
}
