package ChineseChess;

import Engine.Component;
import Engine.PointerDetector;
import Engine.SpriteRenderer;
import Engine.Transform;

public class ChessPieceManager extends Component
{
    Transform transform;
    SpriteRenderer spriteRenderer;
    PointerDetector pointerDetector;
    private int coordX =0;
    private int coordY =4;

    public void onAwake()
    {
        transform = getGameObject().getComponent(Transform.class);
        spriteRenderer = getGameObject().getComponent(SpriteRenderer.class);
        pointerDetector = getGameObject().getComponent(PointerDetector.class);
    }

    public void onStart()
    {
        System.out.println("ChessPieceManager start");
        float[] tPos = ChessBoardManager.coordMapToTransformPos(coordX,coordY);
        transform.setPosition(tPos[0],tPos[1]);
    }

    public int getCoordX()
    {
        return coordX;
    }

    public int getCoordY()
    {
        return coordY;
    }

    public void setCoord(int coordX, int coordY)
    {
        this.coordX = coordX;
        this.coordY = coordY;
    }

}
