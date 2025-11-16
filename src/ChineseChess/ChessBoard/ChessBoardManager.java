package ChineseChess.ChessBoard;

import ChineseChess.ChessPiece.ChessPiece;
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
    Image sprite = new Image("file:src/resources/images/ChessBoard.png");
    private int pointingX;
    private int pointingY;
    private ChessPiece[][] chessPieces = new ChessPiece[9][10];
    private ChessPiece selectedChessPiece;

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
        // 基准点
        final float baseX = 81.6f;
        final float baseY = 52.0f;
        // 步长
        final float stepX = 79.9f;
        final float stepY = 77.8f;

        float x = baseX + coordX * stepX;
        float y = baseY + coordY * stepY;
        return new float[]{x-40, y-40};
    }

    public static int[] transformPosToCoord(float transX, float transY)
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
        pointingX = transformPosToCoord(mouseX, mouseY)[0];
        pointingY = transformPosToCoord(mouseX, mouseY)[1];
    }

}
