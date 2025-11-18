package ChineseChess.ChessPiece;
import ChineseChess.ChessBoard.ChessBoardManager;
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
    ChessBoardManager chessBoardManager;

    ChessPiece pendingEat =null;

    @Override
    public void onAwake()
    {
        spriteRenderer = getGameObject().getComponent(SpriteRenderer.class);
        transform = getGameObject().getComponent(Transform.class);
        pointerDetector = getGameObject().getComponent(PointerDetector.class);
        chessPieceManager = getGameObject().getComponent(ChessPieceManager.class);
        chessBoardManager = getGameObject().getParent().getComponent(ChessBoardManager.class);
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
    public synchronized void moveTo(int x,int y,float velocity)
    {
        float[] tPos = ChessBoardManager.coordToTransformPos(x,y);
        //通知棋盘和棋子更新数据,移动
        chessBoardManager.setPieceAt(null,chessPieceManager.getCoordX(),chessPieceManager.getCoordY());
        transform.moveTo(tPos[0],tPos[1],velocity);
        chessPieceManager.setCoord(x,y);
        chessBoardManager.setPieceAt((ChessPiece) this.getGameObject(),x,y);
        chessPieceManager.updateValidPlaces();
    }

    public void moveTo(int x,int y)
    {
        moveTo(x,y,1000);
    }

    public void eat(int x,int y)
    {
        pendingEat = chessBoardManager.getChessPieceAt(x, y);
        moveTo(x,y);
    }

    public void setTo(int x, int y)
    {
        float[] tPos = ChessBoardManager.coordToTransformPos(x,y);
        //通知棋盘和棋子更新数据
        chessBoardManager.setPieceAt(null,chessPieceManager.getCoordX(),chessPieceManager.getCoordY());
        transform.setPosition(tPos[0],tPos[1]);
        chessPieceManager.setCoord(x,y);
        chessBoardManager.setPieceAt((ChessPiece) this.getGameObject(),x,y);
    }

    boolean moveStarted = false;
    @Override
    public void update()
    {
        if(pendingEat==null)
            return;
        if(!transform.isMoving())
        {
            onEat();
        }
    }

    /**
     * 在棋子移动到被吃棋子上执行的逻辑
     */
    private void onEat()
    {
        pendingEat.destroy();
        pendingEat = null;
    }
}
