package ChineseChess.ChessPiece;
import ChineseChess.ChessBoard.ChessBoardManager;
import Engine.*;
import Engine.Components.Component;
import Engine.Components.PointerDetector;
import Engine.Components.SpriteRenderer;
import Engine.Components.Transform;

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
     * @param x 棋盘坐标x
     * @param y 棋盘坐标y
     * @param velocity 速度
     */
    public void moveTo(int x,int y,float velocity)
    {
        float[] tPos = ChessBoardManager.coordToTransformPos(x,y);
        transform.moveTo(tPos[0],tPos[1],velocity);
    }

    public void moveTo(int x,int y)
    {
        moveTo(x,y,1000);
    }

    public void setTo(int x, int y)
    {
        float[] tPos = ChessBoardManager.coordToTransformPos(x,y);
        transform.setPosition(tPos[0],tPos[1]);
    }

    boolean moveStarted = false;
    @Override
    public void update()
    {

    }
}
