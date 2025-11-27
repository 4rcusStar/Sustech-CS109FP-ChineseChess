package ChineseChess.UI;

import ChineseChess.ChessBoard.ChessBoardManager;
import Engine.Components.RendererComponent;
import Engine.Components.Transform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CurrentTurn extends RendererComponent
{
    private float originX;
    private float originY;
    private static final float TEXT_OFFSET_X = 50f;
    private static final float TEXT_OFFSET_Y = 50f;
    private static final float CHECKMATE_TEXT_OFFSET_X = 50f;
    private static final float CHECKMATE_TEXT_OFFSET_Y = 100f;
    private static final int NORMAL_FONT_SIZE = 20;
    private static final int WARNING_FONT_SIZE = 18;
    private static final String FONT_FAMILY = "Arial";
    private ChessBoardManager chessBoard;
    public void onAwake()
    {
        Transform transform = getGameObject().getComponent(Transform.class);
        originX = transform.getX();
        originY = transform.getY();
        chessBoard = getGameObject().getParent().getChild("ChessBoard").getComponent(ChessBoardManager.class);
    }
    /**
     * 设置字体样式
     * @param gc GraphicsContext
     * @param size 字体大小
     */
    private void setFont(GraphicsContext gc, int size)
    {
        gc.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, size));
    }
    @Override
    public void update()
    {
        if(chessBoard.isGameOver())
        {
            this.setEnabled(false);
        }
    }

    @Override
    public void render(GraphicsContext gc)
    {
        // 显示当前回合
        setFont(gc, NORMAL_FONT_SIZE);
        gc.setFill(Color.BLACK);
        gc.fillText(chessBoard.getCurrentSide().toString()+"'s turn!",originX+TEXT_OFFSET_X,originY+TEXT_OFFSET_Y);
    
        // 显示黑方被将警告
        if(chessBoard.isBlackInCheck())
        {
            setFont(gc, WARNING_FONT_SIZE);
            gc.setFill(Color.RED);
            gc.fillText("CheckMate!\n Think Carefully black",originX+TEXT_OFFSET_X,originY+CHECKMATE_TEXT_OFFSET_Y);
            setFont(gc, NORMAL_FONT_SIZE);
        }
        // 显示红方被将警告
        if(chessBoard.isRedInCheck())
        {
            setFont(gc, WARNING_FONT_SIZE);
            gc.setFill(Color.RED);
            gc.fillText("CheckMate!\n Think Carefully red",originX+CHECKMATE_TEXT_OFFSET_X,originY+CHECKMATE_TEXT_OFFSET_Y);
            setFont(gc, NORMAL_FONT_SIZE);
        }
    }
}
