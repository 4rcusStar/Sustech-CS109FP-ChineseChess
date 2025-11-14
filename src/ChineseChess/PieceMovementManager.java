package ChineseChess;
import Engine.*;

public class PieceMovementManager extends Component
{
    ChessPieceManager chessPieceManager;
    SpriteRenderer spriteRenderer;
    Transform transform;
    PointerDetector pointerDetector;

    @Override
    public void onAwake()
    {
        spriteRenderer = getGameObject().getComponent(SpriteRenderer.class);
        transform = getGameObject().getComponent(Transform.class);
        pointerDetector = getGameObject().getComponent(PointerDetector.class);
        chessPieceManager = getGameObject().getComponent(ChessPieceManager.class);
    }
    @Override
    public void onStart()
    {

    }

    /**
     * 从当前位置移动到ij
     * @param x
     * @param y
     * @param velocity
     */
    public void moveTo(int x,int y,float velocity)
    {
        float[] tPos = ChessBoardManager.coordMapToTransformPos(x,y);
        transform.moveTo(tPos[0],tPos[1],velocity);
    }

    public void moveTo(int x,int y)
    {
        moveTo(x,y,1000);
    }

    public void setTo(int x, int y)
    {
        float[] tPos = ChessBoardManager.coordMapToTransformPos(x,y);
        transform.moveTo(tPos[0],tPos[1],1000);
    }

    boolean moveStarted = false;
    @Override
    public void update()
    {
        if(pointerDetector.isPointed())
        {
            if(Input.isMouseClicked())
            {
                moveTo(3,4);
            }
        }
    }
}
