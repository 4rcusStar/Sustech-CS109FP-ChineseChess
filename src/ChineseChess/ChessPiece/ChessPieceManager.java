package ChineseChess.ChessPiece;

import ChineseChess.ChessBoard.ChessBoardManager;
import Engine.Components.Component;
import Engine.Components.PointerDetector;
import Engine.Components.SpriteRenderer;
import Engine.Components.Transform;
import javafx.scene.image.Image;

public class ChessPieceManager extends Component
{
    private final Side side;
    private final PieceType type;
    Transform transform;
    SpriteRenderer spriteRenderer;
    PointerDetector pointerDetector;
    private int coordX;
    private int coordY;
    private final int pieceIndex;

    /**
     * 目标棋子是否和本棋子同阵营
     * @param piece 目标棋子
     * @return 目标棋子是否和本棋子同阵营
     */
    public boolean isSameSide(ChessPiece piece)
    {
        return piece.getComponent(ChessPieceManager.class).getSide() == side;
    }

    public ChessPieceManager(Side side, PieceType type,int pieceIndex)
    {
        this.type = type;
        this.side = side;
        this.pieceIndex = pieceIndex;
    }

    public Side getSide()
    {
        return side;
    }

    public PieceType getType()
    {
        return type;
    }
    public int getPieceIndex()
    {
        return pieceIndex;
    }


    public void onAwake()
    {
        transform = getGameObject().getComponent(Transform.class);
        spriteRenderer = getGameObject().getComponent(SpriteRenderer.class);
        pointerDetector = getGameObject().getComponent(PointerDetector.class);
        applyInfo();
    }

    //TODO:完成类型判断
    private void applyInfo()
    {
        if(side == Side.RED)
        {
            switch(type)
            {
                case GENERAL ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/red-shuai.png"));
                    coordX = 4; coordY = 0;
                }
                case ADVISOR ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/red-shi.png"));
                    coordX = pieceIndex == 1 ? 3 : 5;
                    coordY = 0;
                }
                case ELEPHANT ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/red-xiang.png"));
                    coordX = pieceIndex == 1 ? 2 : 6;
                    coordY = 0;
                }
                case HORSE ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/red-ma.png"));
                    coordX = pieceIndex == 1 ? 1 : 7;
                    coordY = 0;
                }
                case ROOK ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/red-ju.png"));
                    coordX = pieceIndex == 1 ? 0 : 8;
                    coordY = 0;
                }
                case CANNON ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/red-pao.png"));
                    coordX = pieceIndex == 1 ? 1 : 7;
                    coordY = 2;
                }
                case SOLDIER ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/red-bing.png"));
                    switch(pieceIndex) {
                        case 1 -> { coordX = 0; coordY = 3; }
                        case 2 -> { coordX = 2; coordY = 3; }
                        case 3 -> { coordX = 4; coordY = 3; }
                        case 4 -> { coordX = 6; coordY = 3; }
                        case 5 -> { coordX = 8; coordY = 3; }
                    }
                }
            }
        }
        if(side == Side.BLACK)
        {
            switch(type)
            {
                case GENERAL ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/black-jiang.png"));
                    coordX = 4; coordY = 9;
                }
                case ADVISOR ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/black-shi.png"));
                    coordX = pieceIndex == 1 ? 3 : 5;
                    coordY = 9;
                }
                case ELEPHANT ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/black-xiang.png"));
                    coordX = pieceIndex == 1 ? 2 : 6;
                    coordY = 9;
                }
                case HORSE ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/black-ma.png"));
                    coordX = pieceIndex == 1 ? 1 : 7;
                    coordY = 9;
                }
                case ROOK ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/black-ju.png"));
                    coordX = pieceIndex == 1 ? 0 : 8;
                    coordY = 9;
                }
                case CANNON ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/black-pao.png"));
                    coordX = pieceIndex == 1 ? 1 : 7;
                    coordY = 7;
                }
                case SOLDIER ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/black-zu.png"));
                    switch(pieceIndex) {
                        case 1 -> { coordX = 0; coordY = 6; }
                        case 2 -> { coordX = 2; coordY = 6; }
                        case 3 -> { coordX = 4; coordY = 6; }
                        case 4 -> { coordX = 6; coordY = 6; }
                        case 5 -> { coordX = 8; coordY = 6; }
                    }
                }
            }
        }
        spriteRenderer.setSize(80,80);
        //
    }

    public void onStart()
    {
        float[] tPos = ChessBoardManager.coordToTransformPos(coordX,coordY);
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
