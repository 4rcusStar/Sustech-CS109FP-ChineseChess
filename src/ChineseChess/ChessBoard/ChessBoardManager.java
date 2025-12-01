package ChineseChess.ChessBoard;

import ChineseChess.ChessPiece.*;
import Engine.Components.Component;
import Engine.Components.PointerDetector;
import Engine.Components.SpriteRenderer;
import Engine.Components.Transform;
import Engine.Input;
import javafx.scene.image.Image;


import java.util.LinkedList;
import java.util.List;

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

    private final List<int[]> allPlaces = new LinkedList<>();//表示所有格点
    private List<int[]> movablePlaces = new LinkedList<>();//当有被选中的棋子时，该变量为棋子可移动至的格点
    private List<int[]> eatablePlaces = new LinkedList<>();

    private boolean isMouseInChessBoard = false;

    private Side currentSide = Side.RED;

    public boolean isBlackInCheck()
    {
        return isBlackInCheck;
    }

    public boolean isRedInCheck()
    {
        return isRedInCheck;
    }

    public boolean isGameOver()
    {
        return isGameOver;
    }

    private boolean isRedInCheck = false;
    private boolean isBlackInCheck = false;

    private boolean isGameOver = false;
    Side winnerSide = null;

    /**
     * 返回胜利方，如果游戏没有结束，返回null
     * @return 胜利方
     */
    public Side getWinnerSide()
    {
        if(!isGameOver)
            return null;
        return winnerSide;
    }

    public <T> T[][] deepCopy(T[][] original)
    {
        if (original == null) return null;
        T[][] copy = original.clone();
        for (int i = 0; i < original.length; i++)
        {
            copy[i] = original[i].clone();
        }
        return copy;
    }

    public ChessPieceManager getPieceManagerByName(String name)
    {
        ChessPiece[][] currentChessPieces = deepCopy(chessPieces);
        for (ChessPiece[] i : currentChessPieces)
        {
            for (ChessPiece chess : i)
            {
                if (chess != null)
                {
                    if (chess.getName().equals(name))
                    {
                        return chess.getComponent(ChessPieceManager.class);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void onAwake()
    {
        transform = getGameObject().getComponent(Transform.class);
        spriteRenderer = getGameObject().getComponent(SpriteRenderer.class);
        pointerDetector = getGameObject().getComponent(PointerDetector.class);
        spriteRenderer.setRenderPriority(-999);
    }

    public void onStart()
    {
        spriteRenderer.setSprite(sprite);
        spriteRenderer.setSize(800, 800);
        //构造格点
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                allPlaces.add(new int[]{i, j});
            }
        }
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
        return new float[]{x - 40, y - 40};
    }

    public static int[] transformPosToCoord(float transX, float transY)
    {
        int coordX;
        int coordY;
        coordX = Math.min((int) ((transX + 40 - 82) / 80.6f), 8);
        coordY = Math.min((int) ((transY + 40 - 52f) / 78f), 9);
        return new int[]{coordX, coordY};
    }

    public int[] getPlace(int coordX, int coordY)
    {
        for (int[] place : allPlaces)
        {
            if (coordX == place[0] && coordY == place[1])
            {
                return place;
            }
        }
        return null;
    }

    public List<int[]> getMovablePlaces()
    {
        return movablePlaces;
    }

    public List<int[]> getEatablePlaces()
    {
        return eatablePlaces;
    }

    public int getPointingX()
    {
        return pointingX < 9 ? pointingX : 8;
    }

    public int getPointingY()
    {
        return pointingY < 10 ? pointingY : 9;
    }


    public Side getCurrentSide()
    {
        return currentSide;
    }

    public void update()
    {

        if(isGameOver) return;
        //更新鼠标指针
        updatePointingStatus();
        //System.out.printf("(%.0f,%.0f)\n",Input.getMouseY(),Input.getMouseX());
        if (Input.isMouseClicked())
        {
            int[] pointedPlace = getPlace(pointingX, pointingY);
            ChessPiece pointedPiece = getChessPieceAt(pointingX, pointingY);
            boolean isJustEaten = false;
            if (selectedChessPiece != null)
            {
                //如果不是当前回合，则不会选中,也不会执行任何逻辑
                if (selectedChessPiece.getComponent(ChessPieceManager.class).getSide() != currentSide)
                {
                    selectedChessPiece = pointedPiece;
                    updateSelectedValidPlaces();
                    return;
                }

                //动子逻辑
                if (movablePlaces.contains(pointedPlace))
                {
                    selectedChessPiece.getComponent(PieceMovementManager.class).moveTo(pointedPlace[0], pointedPlace[1]);
                    checkIfGameOver();
                    updateAllPlaces();
                    checkIfInCheck();
                    switchTurn();//转换回合
                }
                //吃子逻辑
                if (eatablePlaces.contains(pointedPlace))
                {
                    selectedChessPiece.getComponent(PieceMovementManager.class).eat(pointedPlace[0], pointedPlace[1]);
                    isJustEaten = true;
                    checkIfGameOver();
                    updateAllPlaces();
                    checkIfInCheck();
                    switchTurn();

                }
            }
            updateAllPlaces();
            selectedChessPiece = isJustEaten ? null : pointedPiece;
        }
        updateSelectedValidPlaces();
    }

    public void updateSelectedValidPlaces()
    {
        if (selectedChessPiece != null)
        {
            ChessPieceManager selectedPieceManager = selectedChessPiece.getComponent(ChessPieceManager.class);
            selectedPieceManager.updateValidPlaces();
            movablePlaces = selectedPieceManager.getMovablePlaces();
            eatablePlaces = selectedPieceManager.getEatablePlaces();
        }
    }

    public void updateAllPlaces()
    {
        ChessPiece[][] allPieces = deepCopy(chessPieces);
        for(ChessPiece[] line : allPieces)
        {
            for(ChessPiece p :line)
            {
                if(p==null) continue;

                ChessPieceManager pManager = p.getComponent(ChessPieceManager.class);
                pManager.updateValidPlaces();
            }
        }
    }

    private void updatePointingStatus()
    {
        float mouseX = (float) Input.getMouseX();
        float mouseY = (float) Input.getMouseY();
        //检测鼠标是否在界外,若在界外则不更新坐标
        isMouseInChessBoard = !(mouseX > 800 || mouseX < 0 || mouseY > 800 || mouseY < 0);
        if (isMouseInChessBoard)
        {
            pointingX = transformPosToCoord(mouseX, mouseY)[0];
            pointingY = transformPosToCoord(mouseX, mouseY)[1];
        }
    }

    /**
     * 切换回合
     */
    public void switchTurn()
    {
        currentSide = (currentSide == Side.RED ? Side.BLACK : Side.RED);
        System.out.println(isBlackInCheck);
        System.out.println(isRedInCheck);
    }

    /**
     * 检查是否被将军
     */
    public void checkIfInCheck()
    {
        checkIfBlackInCheck();
        checkIfRedInCheck();
    }
    private void checkIfBlackInCheck()
    {
        ChessPieceManager blackGeneral = getPieceManagerByName("BLACK_GENERAL_0");
        for(ChessPiece[] lines:chessPieces)
        {
            for (ChessPiece piece : lines)
            {
                if (piece == null) continue;
                if (blackGeneral == null) return;
                for (int[] eatablePlace : piece.getComponent(ChessPieceManager.class).getEatablePlaces())
                {
                    if (eatablePlace[0] == blackGeneral.getCoordX() && eatablePlace[1] == blackGeneral.getCoordY())
                    {
                        isBlackInCheck = true;
                        return;
                    }
                }
            }
        }
        isBlackInCheck = false;
    }
    private void checkIfRedInCheck()
    {
        ChessPieceManager redGeneral = getPieceManagerByName("RED_GENERAL_0");
        for(ChessPiece[] lines:chessPieces)
        {
            for(ChessPiece piece:lines)
            {
                if(piece==null) continue;
                if(redGeneral==null) return;
                for(int[] eatablePlace: piece.getComponent(ChessPieceManager.class).getEatablePlaces())
                {
                    if (eatablePlace[0] == redGeneral.getCoordX() && eatablePlace[1] == redGeneral.getCoordY())
                    {
                        isRedInCheck = true;
                        return;
                    }
                }
            }
        }
        isRedInCheck = false;
    }

    /**
     * 检查游戏是否结束
     */
    public void checkIfGameOver()
    {
        boolean isRedGeneralExist=false;
        boolean isBlackGeneralExist=false;
        for(ChessPiece[] lines:chessPieces)
        {
            for(ChessPiece piece:lines)
            {
                if(piece==null) continue;
                ChessPieceManager pieceManager = piece.getComponent(ChessPieceManager.class);
                if(pieceManager.getType()== PieceType.GENERAL)
                {
                    if(pieceManager.getSide()==Side.BLACK)
                        isBlackGeneralExist=true;
                    else
                        isRedGeneralExist=true;
                }
            }
        }
        isGameOver=!isBlackGeneralExist||!isRedGeneralExist;
        if(isGameOver)
        {
            winnerSide = isBlackGeneralExist?Side.BLACK:Side.RED;
            System.out.printf("Game Over,Winner:%s",winnerSide);
        }
    }

    public ChessPiece getChessPieceAt(int x, int y)
    {
        return chessPieces[x][y];
    }

    public void setPieceAt(ChessPiece chessPiece, int x, int y)
    {
        chessPieces[x][y] = chessPiece;
    }

    public void setSelectedChessPiece(ChessPiece selectedChessPiece)
    {
        this.selectedChessPiece = selectedChessPiece;
    }

    public ChessPiece getSelectedChessPiece()
    {
        return selectedChessPiece;
    }

}
