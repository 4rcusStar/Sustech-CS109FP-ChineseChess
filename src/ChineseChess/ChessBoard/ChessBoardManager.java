package ChineseChess.ChessBoard;

import ChineseChess.ChessPiece.*;
import ChineseChess.UI.InvalidMoveToast;
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
    private static int boardCount=0;

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
    private int turnNumber = 1; // 当前回合数（红方先手为第1回合）

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
    private String endReason = null;
    
    private InvalidMoveToast invalidMoveToast;

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

    public String getEndReason()
    {
        return endReason;
    }

    /**
     * 投降方法：立即结束游戏，设置获胜方为对方
     * @param winnerSide 获胜方（对方）
     */
    public void surrender(Side winnerSide)
    {
        if (isGameOver)
        {
            return; // 游戏已经结束，不处理
        }
        
        isGameOver = true;
        this.winnerSide = winnerSide;
        this.endReason = "Surrender";
        System.out.printf("Game Over (Surrender), Winner: %s\n", winnerSide);
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
        boardCount++;
        System.out.printf("boardCount: %d\n",boardCount);
    }

    public void onStart()
    {
        spriteRenderer.setSprite(sprite);
        spriteRenderer.setSize(800, 800);
        
        // 创建无效移动提示浮框
        Engine.Core.GameObject toastObj = new Engine.Core.GameObject("InvalidMoveToast", 0, 0);
        invalidMoveToast = new InvalidMoveToast();
        toastObj.addComponent(invalidMoveToast);
        getGameObject().addChild(toastObj);
        System.out.println("InvalidMoveToast created and added to ChessBoard");
        //构造格点
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                allPlaces.add(new int[]{i, j});
            }
        }

        // 开局就检查一次（极端局面无合法步）
        checkIfInCheck();
        evaluateCurrentSideLegalMoves();
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

    public int getTurnNumber()
    {
        return turnNumber;
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
            boolean isJustMoved = false;
            // 情况一：当前没有选中的棋子
            if (selectedChessPiece == null)
            {
                if (pointedPiece != null)
                {
                    ChessPieceManager pManager = pointedPiece.getComponent(ChessPieceManager.class);
                    // 只有在当前回合一方时才允许选中
                    if (pManager != null && pManager.getSide() == currentSide)
                    {
                        selectedChessPiece = pointedPiece;
                        updateSelectedValidPlaces();
                    }
                    else
                    {
                        selectedChessPiece = null;
                        movablePlaces.clear();
                        eatablePlaces.clear();
                    }
                }
                else
                {
                    // 点在空格上，取消选中
                    selectedChessPiece = null;
                    movablePlaces.clear();
                    eatablePlaces.clear();
                }
                updateAllPlaces();
            }
            // 情况二：已经有选中的棋子
            else
            {
                ChessPieceManager selectedManager = selectedChessPiece.getComponent(ChessPieceManager.class);
                // 如果当前选中的棋子不属于当前回合，则先按无选中重新处理本次点击
                if (selectedManager == null || selectedManager.getSide() != currentSide)
                {
                    selectedChessPiece = null;
                    movablePlaces.clear();
                    eatablePlaces.clear();

                    if (pointedPiece != null)
                    {
                        ChessPieceManager pManager = pointedPiece.getComponent(ChessPieceManager.class);
                        if (pManager != null && pManager.getSide() == currentSide)
                        {
                            selectedChessPiece = pointedPiece;
                            updateSelectedValidPlaces();
                        }
                    }
                    updateAllPlaces();
                }
                else
                {
                    // 走子
                    if (pointedPlace != null && movablePlaces.contains(pointedPlace))
                    {
                        // 先检查是否会王见王
                        if (wouldFaceGenerals(selectedChessPiece, pointedPlace[0], pointedPlace[1]))
                        {
                            // 会王见王阻止移动
                            if (invalidMoveToast != null)
                            {
                                invalidMoveToast.show();
                            }
                        }
                        // 再检查是否会送将
                        else if (wouldExposeGeneral(selectedChessPiece, pointedPlace[0], pointedPlace[1], false))
                        {
                            // 会送将，阻止移动
                            if (invalidMoveToast != null)
                            {
                                invalidMoveToast.show();
                            }
                        }
                        else
                        {
                            selectedChessPiece.getComponent(PieceMovementManager.class).moveTo(pointedPlace[0], pointedPlace[1]);
                            isJustMoved = true;
                            switchTurn();
                        }
                    }
                    // 吃子逻辑
                    else if (pointedPlace != null && eatablePlaces.contains(pointedPlace))
                    {
                        // 先检查是否会王见王
                        if (wouldFaceGenerals(selectedChessPiece, pointedPlace[0], pointedPlace[1]))
                        {
                            if (invalidMoveToast != null)
                            {
                                invalidMoveToast.show();
                            }
                        }
                        // 再检查是否会送将
                        else if (wouldExposeGeneral(selectedChessPiece, pointedPlace[0], pointedPlace[1], true))
                        {
                            // 会送将，显示提示并阻止移动
                            System.out.println("Invalid eat detected: would expose general");
                            if (invalidMoveToast != null)
                            {
                                invalidMoveToast.show();
                            }
                        }
                        else
                        {
                            selectedChessPiece.getComponent(PieceMovementManager.class).eat(pointedPlace[0], pointedPlace[1]);
                            isJustEaten = true;
                            switchTurn();
                        }
                    }
                    // 切换
                    else if (pointedPiece != null)
                    {
                        ChessPieceManager pManager = pointedPiece.getComponent(ChessPieceManager.class);
                        if (pManager != null && pManager.getSide() == currentSide)
                        {
                            selectedChessPiece = pointedPiece;
                            updateSelectedValidPlaces();
                            updateAllPlaces();
                        }
                    }

                    // 若刚刚完成操作，则取消选中
                    if (isJustEaten||isJustMoved)
                    {
                        selectedChessPiece = null;
                    }
                }
            }
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
        for(ChessPiece[] line : chessPieces)
        {
            for(ChessPiece p : line)
            {
                if(p == null) continue;

                ChessPieceManager pManager = p.getComponent(ChessPieceManager.class);
                if(pManager != null)
                {
                    pManager.updateValidPlaces();
                }
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
        turnNumber++;
        System.out.println(isBlackInCheck);
        System.out.println(isRedInCheck);
        checkIfInCheck();
        evaluateCurrentSideLegalMoves();
    }

    /**
     * 检查是否被将军
     */
    public void checkIfInCheck()
    {
        // 用新的棋盘状态进行检查
        updateAllPlaces();
        checkIfBlackInCheck();
        checkIfRedInCheck();
    }
    private void checkIfBlackInCheck()
    {
        ChessPieceManager blackGeneral = getPieceManagerByName("BLACK_GENERAL_0");
        if (blackGeneral == null)
        {
            isBlackInCheck = false;
            return;
        }
        
        int blackGeneralX = blackGeneral.getCoordX();
        int blackGeneralY = blackGeneral.getCoordY();
        
        ChessPiece[][] currentPieces = deepCopy(chessPieces);
        for(ChessPiece[] lines: currentPieces)
        {
            for (ChessPiece piece : lines)
            {
                if (piece == null) continue;
                ChessPieceManager pieceManager = piece.getComponent(ChessPieceManager.class);
                // 检查红方棋子是否能攻击到黑方将
                if (pieceManager.getSide() != Side.RED) continue;
                
                for (int[] eatablePlace : pieceManager.getEatablePlaces())
                {
                    if (eatablePlace != null && eatablePlace[0] == blackGeneralX && eatablePlace[1] == blackGeneralY)
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
        if (redGeneral == null)
        {
            isRedInCheck = false;
            return;
        }
        
        int redGeneralX = redGeneral.getCoordX();
        int redGeneralY = redGeneral.getCoordY();
        
        // 使用深拷贝避免在遍历时数组被修改
        ChessPiece[][] currentPieces = deepCopy(chessPieces);
        for(ChessPiece[] lines: currentPieces)
        {
            for(ChessPiece piece:lines)
            {
                if(piece==null) continue;
                ChessPieceManager pieceManager = piece.getComponent(ChessPieceManager.class);
                // 检查黑方棋子是否能攻击到红方将
                if (pieceManager.getSide() != Side.BLACK) continue;
                
                for(int[] eatablePlace: pieceManager.getEatablePlaces())
                {
                    if (eatablePlace != null && eatablePlace[0] == redGeneralX && eatablePlace[1] == redGeneralY)
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
     * 检查移动后是否会王见王（两个将面对面）
     * @param piece 要移动的棋子
     * @param targetX 目标X坐标
     * @param targetY 目标Y坐标
     * @return true表示会王见王，false表示不会
     */
    private boolean wouldFaceGenerals(ChessPiece piece, int targetX, int targetY)
    {
        ChessPieceManager pieceManager = piece.getComponent(ChessPieceManager.class);
        if (pieceManager == null) return false;
        
        ChessPieceManager redGeneral = getPieceManagerByName("RED_GENERAL_0");
        ChessPieceManager blackGeneral = getPieceManagerByName("BLACK_GENERAL_0");
        if (redGeneral == null || blackGeneral == null) return false;
        
        int originalX = pieceManager.getCoordX();
        int originalY = pieceManager.getCoordY();
        
        // 保存原始状态
        ChessPiece originalPieceAtTarget = chessPieces[targetX][targetY];
        
        // 模拟移动：临时更新棋盘状态
        chessPieces[originalX][originalY] = null;
        chessPieces[targetX][targetY] = piece;
        pieceManager.setCoord(targetX, targetY);
        
        // 获取移动后的将的位置
        int redGeneralX = redGeneral.getCoordX();
        int redGeneralY = redGeneral.getCoordY();
        int blackGeneralX = blackGeneral.getCoordX();
        int blackGeneralY = blackGeneral.getCoordY();
        
        boolean wouldFace = false;
        
        // 检查两个将是否在同一列（X坐标相同）
        if (redGeneralX == blackGeneralX)
        {
            // 检查两个将之间是否有其他棋子
            int minY = Math.min(redGeneralY, blackGeneralY);
            int maxY = Math.max(redGeneralY, blackGeneralY);
            boolean hasPieceBetween = false;
            
            for (int y = minY + 1; y < maxY; y++)
            {
                if (chessPieces[redGeneralX][y] != null)
                {
                    hasPieceBetween = true;
                    break;
                }
            }
            
            // 如果两个将之间没有其他棋子，则王见王
            if (!hasPieceBetween)
            {
                wouldFace = true;
            }
        }
        
        // 恢复原始状态
        chessPieces[originalX][originalY] = piece;
        chessPieces[targetX][targetY] = originalPieceAtTarget;
        pieceManager.setCoord(originalX, originalY);
        
        return wouldFace;
    }
    
    /**
     * 检查移动后是否会送将（即移动后，对方是否能吃掉我方的将）
     * @param piece 要移动的棋子
     * @param targetX 目标X坐标
     * @param targetY 目标Y坐标
     * @param isEating 是否是吃子
     * @return true表示会送将，false表示不会
     */
    private boolean wouldExposeGeneral(ChessPiece piece, int targetX, int targetY, boolean isEating)
    {
        ChessPieceManager pieceManager = piece.getComponent(ChessPieceManager.class);
        if (pieceManager == null) return false;
        
        Side movingSide = pieceManager.getSide();
        int originalX = pieceManager.getCoordX();
        int originalY = pieceManager.getCoordY();
        
        // 保存原始状态
        ChessPiece originalPieceAtTarget = chessPieces[targetX][targetY];
        
        // 模拟移动：临时更新棋盘状态
        chessPieces[originalX][originalY] = null;
        chessPieces[targetX][targetY] = piece;
        pieceManager.setCoord(targetX, targetY);
        
        // 如果是吃子，被吃的棋子已经在target位置，模拟移动时会覆盖它
        
        // 更新所有棋子的合法位置
        updateAllPlaces();
        
        // 检查移动后，对方是否能吃掉我方的将
        boolean wouldExpose = false;
        if (movingSide == Side.RED)
        {
            // 检查黑方是否能吃掉红方将
            wouldExpose = checkIfRedInCheckAfterMove();
        }
        else
        {
            // 检查红方是否能吃掉黑方将
            wouldExpose = checkIfBlackInCheckAfterMove();
        }
        
        // 恢复原始状态
        chessPieces[originalX][originalY] = piece;
        chessPieces[targetX][targetY] = originalPieceAtTarget;
        pieceManager.setCoord(originalX, originalY);
        
        // 重新更新所有棋子的合法位置
        updateAllPlaces();
        
        return wouldExpose;
    }
    
    /**
     * 检查移动后红方是否会被将
     */
    private boolean checkIfRedInCheckAfterMove()
    {
        ChessPieceManager redGeneral = getPieceManagerByName("RED_GENERAL_0");
        if (redGeneral == null) return false;
        
        int redGeneralX = redGeneral.getCoordX();
        int redGeneralY = redGeneral.getCoordY();
        
        ChessPiece[][] currentPieces = deepCopy(chessPieces);
        for(ChessPiece[] lines: currentPieces)
        {
            for(ChessPiece p: lines)
            {
                if(p == null) continue;
                ChessPieceManager pManager = p.getComponent(ChessPieceManager.class);
                if (pManager == null || pManager.getSide() != Side.BLACK) continue;
                
                for(int[] eatablePlace: pManager.getEatablePlaces())
                {
                    if (eatablePlace != null && eatablePlace[0] == redGeneralX && eatablePlace[1] == redGeneralY)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * 检查移动后黑方是否会被将
     */
    private boolean checkIfBlackInCheckAfterMove()
    {
        ChessPieceManager blackGeneral = getPieceManagerByName("BLACK_GENERAL_0");
        if (blackGeneral == null) return false;
        
        int blackGeneralX = blackGeneral.getCoordX();
        int blackGeneralY = blackGeneral.getCoordY();
        
        ChessPiece[][] currentPieces = deepCopy(chessPieces);
        for(ChessPiece[] lines: currentPieces)
        {
            for(ChessPiece p: lines)
            {
                if(p == null) continue;
                ChessPieceManager pManager = p.getComponent(ChessPieceManager.class);
                if (pManager == null || pManager.getSide() != Side.RED) continue;
                
                for(int[] eatablePlace: pManager.getEatablePlaces())
                {
                    if (eatablePlace != null && eatablePlace[0] == blackGeneralX && eatablePlace[1] == blackGeneralY)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
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
            endReason = "General Captured";
            System.out.printf("Game Over,Winner:%s",winnerSide);
        }
    }

    /**
     * 回合开始时检查当前阵营是否有任何合法步；若无，则根据是否被将判定绝杀或困毙
     */
    private void evaluateCurrentSideLegalMoves()
    {
        if (isGameOver)
        {
            return;
        }

        // 确保各棋子可行步已更新
        updateAllPlaces();
        checkIfInCheck();

        boolean hasLegalMove = false;
        for (ChessPiece[] line : chessPieces)
        {
            for (ChessPiece piece : line)
            {
                if (piece == null) continue;
                ChessPieceManager pm = piece.getComponent(ChessPieceManager.class);
                if (pm == null || pm.getSide() != currentSide) continue;

                List<int[]> movableSnapshot = new LinkedList<>(pm.getMovablePlaces());
                List<int[]> eatableSnapshot = new LinkedList<>(pm.getEatablePlaces());

                // 走子
                for (int[] mv : movableSnapshot)
                {
                    if (mv == null) continue;
                    if (!wouldFaceGenerals(piece, mv[0], mv[1]) && !wouldExposeGeneral(piece, mv[0], mv[1], false))
                    {
                        hasLegalMove = true;
                        break;
                    }
                }
                if (hasLegalMove) break;

                // 吃子
                for (int[] eat : eatableSnapshot)
                {
                    if (eat == null) continue;
                    if (!wouldFaceGenerals(piece, eat[0], eat[1]) && !wouldExposeGeneral(piece, eat[0], eat[1], true))
                    {
                        hasLegalMove = true;
                        break;
                    }
                }
                if (hasLegalMove) break;
            }
            if (hasLegalMove) break;
        }

        if (hasLegalMove)
        {
            return;
        }

        // 无合法步：根据是否被将判定绝杀或困毙
        boolean inCheck = (currentSide == Side.RED) ? isRedInCheck : isBlackInCheck;
        isGameOver = true;
        winnerSide = (currentSide == Side.RED) ? Side.BLACK : Side.RED;
        endReason = inCheck ? "绝杀无解" : "困毙";
        System.out.printf("Game Over (%s), Winner:%s\n", endReason, winnerSide);
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
