package ChineseChess.UI;

import ChineseChess.ChessBoard.ChessBoardManager;
import ChineseChess.ChessPiece.Side;
import Engine.Components.RendererComponent;
import Engine.Input;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TurnIndicator extends RendererComponent
{
    private ChessBoardManager chessBoard;
    private static final int SQUARE_SIZE = 8;
    private static final int OFFSET_X = 10; // 距离鼠标光标的X偏移（右下角）
    private static final int OFFSET_Y = 10; // 距离鼠标光标的Y偏移（右下角）

    @Override
    public void onAwake()
    {
        // 获取棋盘管理器
        Engine.Core.GameObject parent = getGameObject().getParent();
        if (parent != null)
        {
            Engine.Core.GameObject chessBoardObj = parent.getChild("ChessBoard");
            if (chessBoardObj != null)
            {
                chessBoard = chessBoardObj.getComponent(ChessBoardManager.class);
            }
        }
        // 设置高渲染优先级
        setRenderPriority(1000);
    }


    @Override
    public void render(GraphicsContext gc)
    {
        if (chessBoard == null)
        {
            return;
        }

        // 获取当前回合方
        Side currentSide = chessBoard.getCurrentSide();
        if (currentSide == null)
        {
            return;
        }

        // 获取鼠标位置
        double mouseX = Input.getMouseX();
        double mouseY = Input.getMouseY();

        // 计算色块位置（鼠标右下角）
        double squareX = mouseX + OFFSET_X;
        double squareY = mouseY + OFFSET_Y;

        // 根据当前回合方设置颜色
        Color indicatorColor;
        if (currentSide == Side.RED)
        {
            indicatorColor = Color.RED;
        }
        else
        {
            indicatorColor = Color.BLACK;
        }

        // 绘制色块
        gc.setFill(indicatorColor);
        gc.fillRect(squareX, squareY, SQUARE_SIZE, SQUARE_SIZE);
    }
}

