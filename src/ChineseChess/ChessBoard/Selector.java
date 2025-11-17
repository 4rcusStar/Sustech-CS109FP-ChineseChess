package ChineseChess.ChessBoard;

import ChineseChess.ChessPiece.ChessPiece;
import ChineseChess.ChessPiece.ChessPieceManager;
import Engine.Components.RendererComponent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Selector extends RendererComponent
{
    private ChessBoardManager board;

    private int hoverX;
    private int hoverY;

    private int selectedX = -1;
    private int selectedY = -1;

    private ChessPiece lastSelected;

    @Override
    public void onAwake()
    {
        board = getGameObject().getComponent(ChessBoardManager.class);
    }

    @Override
    public void update()
    {
        hoverX = board.getPointingX();
        hoverY = board.getPointingY();

        //当前被选中的棋子
        ChessPiece curr = board.getSelectedChessPiece();

        //如果选中状态变化，则刷新固定框位置
        if (curr != lastSelected)
        {
            if (curr != null)
            {
                selectedX = curr.getComponent(ChessPieceManager.class).getCoordX();
                selectedY = curr.getComponent(ChessPieceManager.class).getCoordY();
            }
            else
            {
                selectedX = -1;
                selectedY = -1;
            }
        }

        lastSelected = curr;
    }

    @Override
    public void render(GraphicsContext gc)
    {
        drawHoverBox(gc);      // 跟随鼠标
        drawSelectedBox(gc);   // 固定呼吸框
    }

    private void drawHoverBox(GraphicsContext gc)
    {
        float[] pos = ChessBoardManager.coordToTransformPos(hoverX, hoverY);

        gc.setStroke(Color.CORNFLOWERBLUE);
        gc.setLineWidth(3);

        drawCornerBox(gc, pos[0], pos[1], 80, 16);
    }

    private void drawSelectedBox(GraphicsContext gc)
    {
        if (selectedX < 0)
        {
            return;
        }

        float[] pos = ChessBoardManager.coordToTransformPos(selectedX, selectedY);

        // 呼吸值
        double t = System.currentTimeMillis() / 400.0;
        double k = (Math.sin(t) + 1.0) / 2.0;

        //渐变色
        Color gradient = new Color(
                0.3 + 0.5 * k,
                0.5 + 0.5 * k,
                1.0,
                1.0
        );

        gc.setStroke(gradient);
        gc.setLineWidth(5);

        // 呼吸框稍微大一点，看起来更亮
        drawCornerBox(gc, pos[0], pos[1], 80, 16);
    }

    /**
     * 绘制方框
     * @param gc gc
     * @param x 锚点x
     * @param y 锚点y
     * @param size 方框大小
     * @param corner 方框长度
     */
    private void drawCornerBox(GraphicsContext gc, float x, float y, float size, float corner)
    {
        float cx = x + size / 2f;
        float cy = y + size / 2f;
        float half = size / 2f;

        // 左上
        gc.strokeLine(cx - half, cy - half, cx - half + corner, cy - half);
        gc.strokeLine(cx - half, cy - half, cx - half, cy - half + corner);

        // 右上
        gc.strokeLine(cx + half, cy - half, cx + half - corner, cy - half);
        gc.strokeLine(cx + half, cy - half, cx + half, cy - half + corner);

        // 左下
        gc.strokeLine(cx - half, cy + half, cx - half + corner, cy + half);
        gc.strokeLine(cx - half, cy + half, cx - half, cy + half - corner);

        // 右下
        gc.strokeLine(cx + half, cy + half, cx + half - corner, cy + half);
        gc.strokeLine(cx + half, cy + half, cx + half, cy + half - corner);
    }
}
