package ChineseChess.ChessPiece;
import ChineseChess.ChessBoard.ChessBoardManager;
import Engine.Audio.AudioManager;
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
        // 在移动开始前，创建残影（记录原位置）
        chessBoardManager.createGhost((ChessPiece) this.getGameObject());
        
        float[] tPos = ChessBoardManager.coordToTransformPos(x,y);
        chessBoardManager.setPieceAt(null,chessPieceManager.getCoordX(),chessPieceManager.getCoordY());
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
        if(pendingEatTransform != null)
        {
            pendingEatTransform.getX();
            pendingEatTransform.getY();
        }
        if(pendingEatSprite != null)
        {
            pendingEatSprite.setOpacity(1.0);
        }

        spriteRenderer.setRenderPriority(1);
        moveTo(x,y);
    }

    public void setTo(int x, int y)
    {
        float[] tPos = ChessBoardManager.coordToTransformPos(x,y);
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
        if (pendingMoveX >= 0 && pendingMoveY >= 0 && !transform.isMoving())
        {
            chessPieceManager.setCoord(pendingMoveX, pendingMoveY);
            chessBoardManager.setPieceAt((ChessPiece) this.getGameObject(), pendingMoveX, pendingMoveY);
            chessPieceManager.updateValidPlaces();

            boolean isEating = (pendingEat != null);
            if (isEating)
            {
                AudioManager.getInstance().playCaptureSound();
            }
            else
            {
                AudioManager.getInstance().playMoveSound();
            }

            pendingMoveX = -1;
            pendingMoveY = -1;

            if (!isEating)
            {
                chessBoardManager.onMoveResolved();
            }
        }
        
        if(pendingEat != null && !transform.isMoving() && pendingMoveX == -1 && pendingMoveY == -1)
        {
            onEat();
            spriteRenderer.setRenderPriority(0);
            chessBoardManager.onMoveResolved();
        }
    }

    private void onEat()
    {
        ChessPieceManager eatenPieceManager = pendingEat.getComponent(ChessPieceManager.class);
        if (eatenPieceManager != null)
        {
            chessBoardManager.setPieceAt(null, eatenPieceManager.getCoordX(), eatenPieceManager.getCoordY());
        }
        
        pendingEat.getComponent(SpriteRenderer.class).setRenderPriority(-1);
        pendingEat.destroy();
        pendingEat = null;
        
        int currentX = chessPieceManager.getCoordX();
        int currentY = chessPieceManager.getCoordY();
        ChessPiece currentPiece = chessBoardManager.getChessPieceAt(currentX, currentY);
        if (currentPiece != (ChessPiece) this.getGameObject())
        {
            chessBoardManager.setPieceAt((ChessPiece) this.getGameObject(), currentX, currentY);
        }
        
        chessBoardManager.updateAllPlaces();
        chessBoardManager.checkIfGameOver();
        chessBoardManager.checkIfInCheck();
    }
}
