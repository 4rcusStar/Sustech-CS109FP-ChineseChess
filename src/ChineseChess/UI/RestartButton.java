package ChineseChess.UI;

import ChineseChess.ChessBoard.ChessBoardManager;
import Engine.Components.Button;
import Engine.Core.GameObject;
import Engine.GameBuilding.GameWorldManager;
import javafx.scene.paint.Color;

/**
 * 重新开始游戏按钮
 */
public class RestartButton extends Button
{
    private static final float SIDEBAR_WIDTH = 200f;
    private static final float BUTTON_OFFSET_Y = 150f; // 放在 WinningStatus 下方
    
    private ChessBoardManager chessBoard;
    private boolean chessBoardInitialized = false;
    
    @Override
    public void onAwake()
    {
        super.onAwake();
        
        // 设置按钮样式
        setText("Restart Game");
        setSize(120f, 40f);
        setOffset((SIDEBAR_WIDTH / 2f) - (width / 2f), BUTTON_OFFSET_Y);
        setColors(
            Color.rgb(100, 149, 237), // 正常颜色
            Color.rgb(70, 130, 180)    // 悬停颜色
        );
        setBorderColor(Color.rgb(65, 105, 225));
        setBorderWidth(2f);
        setTextColor(Color.WHITE);
        setFont("Arial", 16, javafx.scene.text.FontWeight.BOLD);
        setCornerRadius(10f);
    }
    
    @Override
    public void update()
    {
        // 延迟初始化 chessBoard
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
        
        // 只在游戏结束时启用按钮
        if (chessBoard != null)
        {
            setButtonEnabled(chessBoard.isGameOver());
        }
        
        super.update();
    }
    
    @Override
    protected boolean shouldRender()
    {
        // 只在游戏结束时渲染
        return chessBoard != null && chessBoard.isGameOver();
    }
    
    @Override
    protected void onClick()
    {
        // 强制重新创建游戏世界，清除缓存并重新构造
        GameWorldManager worldManager = GameWorldManager.getInstance();
        worldManager.recreateGameWorld("Game");
    }
}

