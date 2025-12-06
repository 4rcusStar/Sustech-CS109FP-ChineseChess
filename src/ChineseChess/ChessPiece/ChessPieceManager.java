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
        spriteRenderer.setRenderPriority(0);
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

        switch (type)
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
            case CANNON ->
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
                        if (i == 8) break;
                        for (int j = i + 1; j < 9; j++)
                        {
                            ChessPiece chessHere_1 = chessBoard.getChessPieceAt(j, coordY);
                            if (chessHere_1 != null)
                            {
                                if (!chessHere_1.getComponent(ChessPieceManager.class).isSameSide((ChessPiece) this.getGameObject()))
                                {
                                    eatablePlaces.add(chessBoard.getPlace(j, coordY));
                                    break;
                                }
                                break;
                            }
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
                        if (i == 0) break;
                        for (int j = i - 1; j >= 0; j--)
                        {
                            ChessPiece chessHere_1 = chessBoard.getChessPieceAt(j, coordY);
                            if (chessHere_1 != null)
                            {
                                if (!chessHere_1.getComponent(ChessPieceManager.class).isSameSide((ChessPiece) this.getGameObject()))
                                {
                                    eatablePlaces.add(chessBoard.getPlace(j, coordY));
                                    break;
                                }
                                break;
                            }
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
                        if (j == 0) break;
                        for (int i = j - 1; i >= 0; i--)
                        {
                            ChessPiece chessHere_1 = chessBoard.getChessPieceAt(coordX, i);
                            if (chessHere_1 != null)
                            {
                                if (!chessHere_1.getComponent(ChessPieceManager.class).isSameSide((ChessPiece) this.getGameObject()))
                                {
                                    eatablePlaces.add(chessBoard.getPlace(coordX, i));
                                    break;
                                }
                                break;
                            }
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
                        if (j == 9) break;
                        for (int i = j + 1; i < 10; i++)
                        {
                            ChessPiece chessHere_1 = chessBoard.getChessPieceAt(coordX, i);
                            if (chessHere_1 != null)
                            {
                                if (!chessHere_1.getComponent(ChessPieceManager.class).isSameSide((ChessPiece) this.getGameObject()))
                                {
                                    eatablePlaces.add(chessBoard.getPlace(coordX, i));
                                    break;
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            case SOLDIER ->
            {
                if (this.getSide() == Side.RED)
                {
                    //过河?
                    if (coordY > 4)
                    {
                        canMove(coordX + 1, coordY);
                        canMove(coordX - 1, coordY);
                        canMove(coordX, coordY + 1);

                    } else
                    {
                        canMove(coordX, coordY + 1);
                    }
                } else
                {
                    //过河?
                    if (coordY <= 4)
                    {
                        canMove(coordX + 1, coordY);
                        canMove(coordX - 1, coordY);
                        canMove(coordX, coordY - 1);

                    } else
                    {
                        canMove(coordX, coordY - 1);
                    }
                }
            }
            case HORSE ->
            {
                if (isInBoard(coordX + 1, coordY))
                {
                    if (chessBoard.getChessPieceAt(coordX + 1, coordY) == null)
                    {
                        canMove(coordX + 2, coordY + 1);
                        canMove(coordX + 2, coordY - 1);
                    }
                }
                if (isInBoard(coordX - 1, coordY))
                {
                    if (chessBoard.getChessPieceAt(coordX - 1, coordY) == null)
                    {
                        canMove(coordX - 2, coordY + 1);
                        canMove(coordX - 2, coordY - 1);
                    }
                }
                if (isInBoard(coordX, coordY + 1))
                {
                    if (chessBoard.getChessPieceAt(coordX, coordY + 1) == null)
                    {
                        canMove(coordX + 1, coordY + 2);
                        canMove(coordX - 1, coordY + 2);
                    }
                }
                if (isInBoard(coordX, coordY - 1))
                {
                    if (chessBoard.getChessPieceAt(coordX, coordY - 1) == null)
                    {
                        canMove(coordX + 1, coordY - 2);
                        canMove(coordX - 1, coordY - 2);
                    }
                }
            }
            case ELEPHANT -> {
                int[][] elephantMoves = {{2, 2}, {2, -2}, {-2, 2}, {-2, -2}};
                int[][] blockPositions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

                for (int i = 0; i < elephantMoves.length; i++) {
                    int newX = coordX + elephantMoves[i][0];
                    int newY = coordY + elephantMoves[i][1];
                    int blockX = coordX + blockPositions[i][0];
                    int blockY = coordY + blockPositions[i][1];

                    // 检查目标位置是否在棋盘范围内
                    if (newX < 0 || newX > 8 || newY < 0 || newY > 9) {
                        continue;
                    }

                    // 检查是否过河
                    boolean isValidRiver = (this.getSide() == Side.RED) ? (newY <= 4) : (newY >= 5);
                    if (!isValidRiver) {
                        continue;
                    }

                    // 检查象眼是否被堵
                    if (chessBoard.getChessPieceAt(blockX, blockY) != null) {
                        continue;
                    }

                    canMove(newX, newY);
                }
            }
            case ADVISOR ->
            {
                if (this.getSide() == Side.RED)
                {
                    if (coordY + 1 < 3 & coordX + 1 < 6)
                    {
                        canMove(coordX + 1, coordY + 1);
                    }
                    if (coordY + 1 < 3 & coordX - 1 > 2)
                    {
                        canMove(coordX - 1, coordY + 1);
                    }
                    if (coordY - 1 > -1 & coordX + 1 < 6)
                    {
                        canMove(coordX + 1, coordY - 1);
                    }
                    if (coordY - 1 > -1 & coordX - 1 > 2)
                    {
                        canMove(coordX - 1, coordY - 1);
                    }
                } else
                {
                    if (coordY + 1 < 10 & coordX + 1 < 6)
                    {
                        canMove(coordX + 1, coordY + 1);
                    }
                    if (coordY + 1 < 10 & coordX - 1 > 2)
                    {
                        canMove(coordX - 1, coordY + 1);
                    }
                    if (coordY - 1 > 6 & coordX + 1 < 6)
                    {
                        canMove(coordX + 1, coordY - 1);
                    }
                    if (coordY - 1 > 6 & coordX - 1 > 2)
                    {
                        canMove(coordX - 1, coordY - 1);
                    }
                }
            }
            case GENERAL ->
            {
                if (this.getSide() == Side.RED)
                {
                    if (coordX + 1 < 6)
                    {
                        canMove(coordX + 1, coordY);
                    }
                    if (coordX - 1 > 2)
                    {
                        canMove(coordX - 1, coordY);
                    }
                    if (coordY - 1 > -1)
                    {
                        canMove(coordX, coordY - 1);
                    }
                    if (coordY + 1 < 3)
                    {
                        canMove(coordX, coordY + 1);
                    }
                } else
                {
                    if (coordY + 1 < 10)
                    {
                        canMove(coordX, coordY + 1);
                    }
                    if (coordX - 1 > 2)
                    {
                        canMove(coordX - 1, coordY);
                    }
                    if (coordY - 1 > 6)
                    {
                        canMove(coordX, coordY - 1);
                    }
                    if (coordX + 1 < 6)
                    {
                        canMove(coordX + 1, coordY);
                    }
                }
                // 不再移除王见王的位置，改为在移动时检查
                // movablePlaces.removeIf(this::generalJudge_1);
            }
        }
        if (!type.equals(PieceType.GENERAL))
        {
            // 不再移除王见王的位置，改为在移动时检查
            // movablePlaces.removeIf(this::generalJudge_2);
        }
    }

    //将帅不能见面
    public boolean generalJudge_2(int[] movePlace)
    {
        boolean a = false;
        ChessPieceManager redGeneral = chessBoard.getPieceManagerByName("RED_GENERAL_0");
        ChessPieceManager blackGeneral = chessBoard.getPieceManagerByName("BLACK_GENERAL_0");
        if (redGeneral == null || blackGeneral == null)
            return false;
        int redGeneralCoordX = redGeneral.getCoordX();
        int blackGeneralCoordX = blackGeneral.getCoordX();
        int redGeneralCoordY = redGeneral.getCoordY();
        int blackGeneralCoordY = blackGeneral.getCoordY();
        if (redGeneralCoordX == blackGeneralCoordX && redGeneralCoordX == coordX && coordX != movePlace[0] && coordY < blackGeneralCoordY && coordY > redGeneralCoordY)
        {
            a = true;
            for (int i = redGeneralCoordY + 1; i < blackGeneralCoordY; i++)
            {
                if (i == coordY)
                {
                    continue;
                }
                if (chessBoard.getChessPieceAt(coordX, i) != null)
                {
                    a = false;
                    break;
                }
            }
        }
        return a;
    }

    public boolean generalJudge_1(int[] movePlace)
    {
        boolean a = false;
        ChessPieceManager redGeneral = chessBoard.getPieceManagerByName("RED_GENERAL_0");
        ChessPieceManager blackGeneral = chessBoard.getPieceManagerByName("BLACK_GENERAL_0");
        if (redGeneral == null || blackGeneral == null)
            return false;
        if (this.getSide() == Side.RED)
        {

            if (blackGeneral.getCoordX() == movePlace[0])
            {
                a = true;
                for (int i = coordY + 1; i < blackGeneral.getCoordY(); i++)
                {
                    if (chessBoard.getChessPieceAt(movePlace[0], i) != null)
                    {
                        a = false;
                        break;
                    }
                }
            }
        } else
        {
            if (redGeneral.getCoordX() == movePlace[0])
            {
                a = true;
                for (int i = coordY - 1; i > redGeneral.getCoordY(); i--)
                {
                    if (chessBoard.getChessPieceAt(movePlace[0], i) != null)
                    {
                        a = false;
                        break;
                    }
                }
            }
        }
        return a;
    }

    //能否移动到目标位置(x,y)适用于大部分棋子
    public void canMove(int x, int y)
    {
        if (!isInBoard(x, y)) return;
        ChessPiece chessHere = chessBoard.getChessPieceAt(x, y);
        if (chessHere == null)
        {
            movablePlaces.add(chessBoard.getPlace(x, y));
        } else
        {
            //如果不是同一阵营:
            if (!chessHere.getComponent(ChessPieceManager.class).isSameSide((ChessPiece) this.getGameObject()))
            {
                eatablePlaces.add(chessBoard.getPlace(x, y));
            }
        }
    }

    //判断（x,y）是否在棋盘内
    public boolean isInBoard(int x, int y)
    {
        return !(x < 0 || x > 8 ||y < 0 || y > 9);
    }


}
