package Engine;

import javafx.scene.canvas.Canvas;

public class Input
{
    private static Canvas canvas;
    private static boolean isMousePressed;
    private static boolean isMouseReleased;
    private static boolean isMouseClicked;
    private static double mouseX;
    private static double mouseY;
    /**
     * 在创建canvas之后，给Input绑上Canvas
     * @param canvas 绑定的canvas
     */
    public static void bindCanvas(Canvas canvas)
    {
        Input.canvas = canvas;
        canvas.setOnMousePressed(event -> isMousePressed = true);
        canvas.setOnMouseReleased(event -> isMousePressed =false);
        canvas.setOnMouseMoved(event ->
                {
                    mouseX = event.getX();
                    mouseY = event.getY();
                }
        );
        canvas.setOnMouseClicked(event ->isMouseClicked =true);
    }

    public static boolean isMouseClicked()
    {
        if (isMouseClicked)
        {
            isMouseClicked = false;
            return true;
        }
        return false;
    }
    /**
     * 当鼠标左键按住时为true
     * @return 鼠标左键是否按住
     */
    public static boolean isMousePressed()
    {
        return isMousePressed;
    }

    /**
     *  当鼠标左键松开时为true
     * @return 鼠标左键是否松开
     */
    public static boolean isMouseReleased()
    {
        return isMouseReleased;
    }

     static void resetMouse()
    {
        isMouseClicked = false;
    }

    /**
     * 获取鼠标的相对窗口坐标X
     * @return 鼠标的相对窗口坐标X
     */
    public static double getMouseX()
    {
        return mouseX;
    }

    /**
     * 获取鼠标的相对窗口坐标Y
     * @return 鼠标的相对窗口坐标Y
     */
    public static double getMouseY()
    {
        return mouseY;
    }

}
