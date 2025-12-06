package ChineseChess.UI;

import ChineseChess.ChessBoard.ChessBoardManager;
import ChineseChess.ChessPiece.Side;
import Engine.Components.RendererComponent;
import Engine.Components.Transform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CurrentTurn extends RendererComponent
{
    private float originX;
    private float originY;
    private static final float SIDEBAR_WIDTH = 200f; // SideBar宽度 (1000 - 800)
    private static final float TEXT_OFFSET_Y = 50f;
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

    /**
     * 计算文字居中位置的X坐标
     * @param gc GraphicsContext
     * @param text 要显示的文字
     * @return 居中后的X坐标
     */
    private float getCenteredX(GraphicsContext gc, String text)
    {
        // 使用Text对象来测量文字宽度
        Text textNode = new Text(text);
        textNode.setFont(gc.getFont());
        double textWidth = textNode.getBoundsInLocal().getWidth();
        // 计算居中位置
        return originX + (SIDEBAR_WIDTH / 2f) - (float)(textWidth / 2);
    }
    
    @Override
    public void render(GraphicsContext gc)
    {
        // 显示当前回合
        setFont(gc, NORMAL_FONT_SIZE);
        String turnText = chessBoard.getCurrentSide().toString() + "'s turn!";
        float centeredX = getCenteredX(gc, turnText);
        gc.setFill(Color.BLACK);
        gc.fillText(turnText, centeredX, originY + TEXT_OFFSET_Y);
    
        // 只在当前回合方被将时显示警告
        Side currentSide = chessBoard.getCurrentSide();
        
        // 显示黑方被将警告
        if(currentSide == Side.BLACK && chessBoard.isBlackInCheck())
        {
            setFont(gc, WARNING_FONT_SIZE);
            String checkText = "Check!\n Think Carefully";
            String firstLine = checkText.split("\n")[0];
            float checkCenteredX = getCenteredX(gc, firstLine);
            gc.setFill(Color.RED);
            gc.fillText(checkText, checkCenteredX, originY + CHECKMATE_TEXT_OFFSET_Y);
            setFont(gc, NORMAL_FONT_SIZE);
        }
        // 显示红方被将警告
        else if(currentSide == Side.RED && chessBoard.isRedInCheck())
        {
            setFont(gc, WARNING_FONT_SIZE);
            String checkText = "Check!\n Think Carefully";
            String firstLine = checkText.split("\n")[0];
            float checkCenteredX = getCenteredX(gc, firstLine);
            gc.setFill(Color.RED);
            gc.fillText(checkText, checkCenteredX, originY + CHECKMATE_TEXT_OFFSET_Y);
            setFont(gc, NORMAL_FONT_SIZE);
        }
    }
}
