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
    //
    private Transform pendingEatTransform;
    private SpriteRenderer pendingEatSprite;

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
        // 保存目标位置，在移动完成后更新
        pendingMoveX = x;
        pendingMoveY = y;
        transform.moveTo(tPos[0],tPos[1],velocity);
    }

    public void moveTo(int x,int y)
    {
        moveTo(x,y,500);
    }

    public void eat(int x,int y)
    {
        pendingEat = chessBoardManager.getChessPieceAt(x, y);
        if(pendingEat == null)
            return;

        pendingEatTransform = pendingEat.getComponent(Transform.class);
        pendingEatSprite = pendingEat.getComponent(SpriteRenderer.class);
        // 记录被吃子的坐标（当前未使用，可用于动画或阴影）
        if(pendingEatTransform != null)
        {
            pendingEatTransform.getX();
            pendingEatTransform.getY();
        }
        if(pendingEatSprite != null)
        {
            pendingEatSprite.setOpacity(1.0);
        }

        // 吃子时永远在其他棋子上层显示
        spriteRenderer.setRenderPriority(1);
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
    private int pendingMoveX = -1;
    private int pendingMoveY = -1;
    @Override
    public void update()
    {
        // 处理移动完成后的棋盘状态更新
        if (pendingMoveX >= 0 && pendingMoveY >= 0 && !transform.isMoving())
        {
            // 移动完成，更新棋盘状态
            chessPieceManager.setCoord(pendingMoveX, pendingMoveY);
            chessBoardManager.setPieceAt((ChessPiece) this.getGameObject(), pendingMoveX, pendingMoveY);
            chessPieceManager.updateValidPlaces();
            
            boolean isEating = (pendingEat != null);
            
            pendingMoveX = -1;
            pendingMoveY = -1;
            
            // 非吃子：移动完成后统一由棋盘处理回合收尾
            if (!isEating)
            {
                chessBoardManager.onMoveResolved();
            }
        }
        
        // 处理吃子逻辑（在移动完成后，确保位置已更新）
        if(pendingEat != null && !transform.isMoving() && pendingMoveX == -1 && pendingMoveY == -1)
        {
            // 确保吃子棋子的位置已经更新到新位置（pendingMoveX 和 pendingMoveY 已经被重置）
            onEat();
            spriteRenderer.setRenderPriority(0);
            // 吃子完成后也需要收尾与切换回合
            chessBoardManager.onMoveResolved();
        }
    }

    /**
     * 在棋子移动到被吃棋子上执行的逻辑
     */
    private void onEat()
    {
        // 在销毁前，先清除棋盘上被吃棋子的位置
        ChessPieceManager eatenPieceManager = pendingEat.getComponent(ChessPieceManager.class);
        if (eatenPieceManager != null)
        {
            chessBoardManager.setPieceAt(null, eatenPieceManager.getCoordX(), eatenPieceManager.getCoordY());
        }
        
        pendingEat.getComponent(SpriteRenderer.class).setRenderPriority(-1);
        pendingEat.destroy();
        pendingEat = null;
        
        // 确保棋子位置正确更新
        int currentX = chessPieceManager.getCoordX();
        int currentY = chessPieceManager.getCoordY();
        ChessPiece currentPiece = chessBoardManager.getChessPieceAt(currentX, currentY);
        if (currentPiece != (ChessPiece) this.getGameObject())
        {
            // 如果位置不正确，重新设置
            chessBoardManager.setPieceAt((ChessPiece) this.getGameObject(), currentX, currentY);
        }
        
        // 吃子完成后，更新所有棋子的合法位置
        chessBoardManager.updateAllPlaces();
        
        chessBoardManager.checkIfGameOver();
        chessBoardManager.checkIfInCheck();
    }
}
