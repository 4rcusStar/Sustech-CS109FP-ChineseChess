package ChineseChess.ChessPiece;

import ChineseChess.ChessBoard.ChessBoardManager;
import Engine.Components.Component;
import Engine.Components.PointerDetector;
import Engine.Components.SpriteRenderer;
import Engine.Components.Transform;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

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
    private ChessBoardManager chessBoard;

    List<int[]> movablePlaces = new ArrayList<>();
    List<int[]> eatablePlaces = new ArrayList<>();

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
        chessBoard = getGameObject().getParent().getComponent(ChessBoardManager.class);
        applyInfo();
    }

    /**
     * 为棋子绑定初始状态
     */
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
                    chessBoard.setPieceAt((ChessPiece) this.getGameObject(),coordX,coordY);
                }
                case ADVISOR ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/red-shi.png"));
                    coordX = pieceIndex == 1 ? 3 : 5;
                    coordY = 0;
                    chessBoard.setPieceAt((ChessPiece) this.getGameObject(), coordX, coordY);
                }
                case ELEPHANT ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/red-xiang.png"));
                    coordX = pieceIndex == 1 ? 2 : 6;
                    coordY = 0;
                    chessBoard.setPieceAt((ChessPiece) this.getGameObject(), coordX, coordY);
                }
                case HORSE ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/red-ma.png"));
                    coordX = pieceIndex == 1 ? 1 : 7;
                    coordY = 0;
                    chessBoard.setPieceAt((ChessPiece) this.getGameObject(), coordX, coordY);
                }
                case ROOK ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/red-ju.png"));
                    coordX = pieceIndex == 1 ? 0 : 8;
                    coordY = 0;
                    chessBoard.setPieceAt((ChessPiece) this.getGameObject(), coordX, coordY);
                }
                case CANNON ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/red-pao.png"));
                    coordX = pieceIndex == 1 ? 1 : 7;
                    coordY = 2;
                    chessBoard.setPieceAt((ChessPiece) this.getGameObject(), coordX, coordY);
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
                    chessBoard.setPieceAt((ChessPiece) this.getGameObject(), coordX, coordY);
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
                    chessBoard.setPieceAt((ChessPiece) this.getGameObject(), coordX, coordY);
                }
                case ADVISOR ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/black-shi.png"));
                    coordX = pieceIndex == 1 ? 3 : 5;
                    coordY = 9;
                    chessBoard.setPieceAt((ChessPiece) this.getGameObject(), coordX, coordY);
                }
                case ELEPHANT ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/black-xiang.png"));
                    coordX = pieceIndex == 1 ? 2 : 6;
                    coordY = 9;
                    chessBoard.setPieceAt((ChessPiece) this.getGameObject(), coordX, coordY);
                }
                case HORSE ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/black-ma.png"));
                    coordX = pieceIndex == 1 ? 1 : 7;
                    coordY = 9;
                    chessBoard.setPieceAt((ChessPiece) this.getGameObject(), coordX, coordY);
                }
                case ROOK ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/black-ju.png"));
                    coordX = pieceIndex == 1 ? 0 : 8;
                    coordY = 9;
                    chessBoard.setPieceAt((ChessPiece) this.getGameObject(), coordX, coordY);
                }
                case CANNON ->
                {
                    spriteRenderer.setSprite(new Image("file:src/resources/images/black-pao.png"));
                    coordX = pieceIndex == 1 ? 1 : 7;
                    coordY = 7;
                    chessBoard.setPieceAt((ChessPiece) this.getGameObject(), coordX, coordY);
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
                    chessBoard.setPieceAt((ChessPiece) this.getGameObject(), coordX, coordY);
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
        //设置初始可移动/吃子位置
        updateValidPlaces();
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

    /**获取以棋子为原点（0,0）的（x,y）处的棋子：若在棋盘之外或者没有，则返回空
     *
     * @param x 相对棋子坐标x
     * @param y 相对棋子坐标y
     * @return 棋子为原点（0,0）的（x,y）处的棋子
     */
    public ChessPiece getChessPieceRelatively(int x,int y)
    {
        int absoluteX = coordX + x;
        int absoluteY = coordY + y;
        if(absoluteX>=9||absoluteY>=10)
            return null;
        return chessBoard.getChessPieceAt(absoluteX,absoluteY);
    }


    /**
     * 获取棋子合法的移动位置
     */
    public List<int[]> getMovablePlaces()
    {
        return movablePlaces;
    }

    public List<int[]> getEatablePlaces()
    {
        return eatablePlaces;
    }

    /**
     * 在位置更新后需要调用以更新棋子的合法吃子位置和移动位置
     */
    public void updateValidPlaces()
    {
        movablePlaces.clear();
        eatablePlaces.clear();

        //TODO:完成所有棋子的移动逻辑：使用chessBoard.getPlace(i, j)获取位置，在movablePlaces中添加可以移动的位置，在eatablePlaces中添加可以吃子的位置
        switch(type)
        {
            //车的合法移动位置
            case ROOK ->
            {
                // 向右
                for (int i = coordX + 1; i < 9; i++)
                {
                    ChessPiece chessHere = chessBoard.getChessPieceAt(i, coordY);
                    if (chessHere == null)
                    {
                        movablePlaces.add(chessBoard.getPlace(i, coordY));
                    } else
                    {
                        //如果不是同一阵营:
                        if (!chessHere.getComponent(ChessPieceManager.class).isSameSide((ChessPiece) this.getGameObject()))
                        {
                            eatablePlaces.add(chessBoard.getPlace(i, coordY));
                        }
                        break;
                    }
                }
                // 向左
                for (int i = coordX - 1; i >= 0; i--)
                {
                    ChessPiece chessHere = chessBoard.getChessPieceAt(i, coordY);

                    if (chessHere == null)
                    {
                        movablePlaces.add(chessBoard.getPlace(i, coordY));
                    } else
                    {
                        if (!chessHere.getComponent(ChessPieceManager.class).isSameSide((ChessPiece) this.getGameObject()))
                        {
                            eatablePlaces.add(chessBoard.getPlace(i, coordY));
                        }
                        break;
                    }
                }
                // 向上
                for (int j = coordY - 1; j >= 0; j--)
                {
                    ChessPiece chessHere = chessBoard.getChessPieceAt(coordX, j);

                    if (chessHere == null)
                    {
                        movablePlaces.add(chessBoard.getPlace(coordX, j));
                    } else
                    {
                        if (!chessHere.getComponent(ChessPieceManager.class).isSameSide((ChessPiece) this.getGameObject()))
                        {
                            eatablePlaces.add(chessBoard.getPlace(coordX, j));
                        }
                        break;
                    }
                }
                // 向下
                for (int j = coordY + 1; j < 10; j++)
                {
                    ChessPiece chessHere = chessBoard.getChessPieceAt(coordX, j);

                    if (chessHere == null)
                    {
                        movablePlaces.add(chessBoard.getPlace(coordX, j));
                    } else
                    {
                        if (!chessHere.getComponent(ChessPieceManager.class).isSameSide((ChessPiece) this.getGameObject()))
                        {
                            eatablePlaces.add(chessBoard.getPlace(coordX, j));
                        }
                        break;
                    }
                }
            }
        }
    }



}
