package ChineseChess.ChessBoard;

import Engine.Components.Component;
import Engine.Components.PointerDetector;
import Engine.Components.SpriteRenderer;
import Engine.Components.Transform;
import Engine.Input;
import javafx.scene.image.Image;

public class ChessBoardManager extends Component
{
    Transform transform;
    SpriteRenderer spriteRenderer;
    PointerDetector pointerDetector;
    Image sprite = new Image("file:/C:/Users/ASUS/IdeaProjects/FP-ChineseChess/src/ChessBoard.png");
    private int pointingX;
    private int pointingY;

    @Override
    public void onAwake()
    {
        transform = getGameObject().getComponent(Transform.class);
        spriteRenderer = getGameObject().getComponent(SpriteRenderer.class);
        pointerDetector = getGameObject().getComponent(PointerDetector.class);
    }

    public void onStart()
    {
        spriteRenderer.setSprite(sprite);
        spriteRenderer.setSize(800, 800);
    }

    /**
     *
     * @param coordX 棋盘坐标X
     * @param coordY 棋盘坐标Y
     * @return float[0]:像素坐标X,float[1]:像素坐标Y
     */
    public static float[] coordToTransformPos(int coordX, int coordY)
    {
        float[] coordMap = new float[2];
        coordMap[0] = 82 + coordX * 80.6f - 40;
        coordMap[1] = 52 + coordY * 78.f - 40;
        return coordMap;
    }

    public static int[] TransformPosToCoord(float transX, float transY)
    {
        int coordX;
        int coordY;
        coordX = (int) ((transX + 40 - 82) / 80.6f);
        coordY = (int) ((transY + 40 - 52f) / 78f);
        return new int[]{coordX, coordY};
    }

    public int getPointingX()
    {
        return pointingX;
    }

    public int getPointingY()
    {
        return pointingY;
    }


    public void update()
    {
        updatePointingStatus();
        //System.out.printf("(%.0f,%.0f)\n",Input.getMouseY(),Input.getMouseX());
    }

    private void updatePointingStatus()
    {
        float mouseX = (float) Input.getMouseX();
        float mouseY = (float) Input.getMouseY();
        int coordX = TransformPosToCoord(mouseX, mouseY)[0];
        int coordY = TransformPosToCoord(mouseX, mouseY)[1];
        System.out.printf("Pointing(%d,%d)\n", coordX, coordY);
    }

}
