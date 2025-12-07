package ChineseChess.UI;

import ChineseChess.ChessBoard.ChessBoardManager;
import ChineseChess.ChessPiece.Side;
import Engine.Components.Button;
import Engine.Core.GameObject;
import javafx.scene.paint.Color;

/**
 * 投降按钮
 * 根据当前回合方显示不同颜色的边框，点击后立即结束游戏
 */
public class SurrenderButton extends Button
{
    private static final float SIDEBAR_WIDTH = 200f;
    private static final float BUTTON_OFFSET_Y = 200f; // 放在 RestartButton 下方
    
    private ChessBoardManager chessBoard;
    private boolean chessBoardInitialized = false;
    
    @Override
    public void onAwake()
    {
        super.onAwake();
        
        // 设置按钮样式
        setText("Surrender");
        setSize(120f, 40f);
        setOffset((SIDEBAR_WIDTH / 2f) - (width / 2f), BUTTON_OFFSET_Y);
        setColors(
            Color.rgb(220, 20, 60), // 正常颜色（深红色）
            Color.rgb(178, 34, 34)   // 悬停颜色（火砖红）
        );
        setBorderWidth(3f); // 稍粗的边框以便显示发光效果
        setTextColor(Color.WHITE);
        setFont("Arial", 16, javafx.scene.text.FontWeight.BOLD);
        setCornerRadius(10f);
    }
    
    @Override
    public void update()
    {
        if (!chessBoardInitialized)
        {
            GameObject parent = getGameObject().getParent();
            if (parent != null)
            {
                GameObject chessBoardObj = parent.getChild("ChessBoard");
                if (chessBoardObj != null)
                {
                    chessBoard = chessBoardObj.getComponent(ChessBoardManager.class);
                    chessBoardInitialized = true;
                }
            }
        }
        
        // 只在游戏未结束时启用按钮
        if (chessBoard != null)
        {
            setButtonEnabled(!chessBoard.isGameOver());
            
            // 根据当前回合方动态改变边框颜色
            Side currentSide = chessBoard.getCurrentSide();
            if (currentSide != null)
            {
                if (currentSide == Side.RED)
                {
                    // 红方回合：红色边框（发光效果）
                    setBorderColor(Color.rgb(255, 0, 0)); // 纯红色
                }
                else
                {
                    // 黑方回合：黑色边框（发光效果）
                    setBorderColor(Color.rgb(0, 0, 0)); // 纯黑色
                }
            }
        }
        
        super.update();
    }
    
    @Override
    protected boolean shouldRender()
    {
        // 只在游戏未结束时渲染
        return chessBoard != null && !chessBoard.isGameOver();
    }
    
    @Override
    protected void onClick()
    {
        if (chessBoard != null && !chessBoard.isGameOver())
        {
            // 获取当前回合方
            Side currentSide = chessBoard.getCurrentSide();
            if (currentSide != null)
            {
                // 当前回合方投降，对方获胜
                Side winnerSide = (currentSide == Side.RED) ? Side.BLACK : Side.RED;
                chessBoard.surrender(winnerSide);
            }
        }
    }
}

