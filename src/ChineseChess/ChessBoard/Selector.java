package ChineseChess.ChessBoard;

import Engine.Components.RendererComponent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Selector extends RendererComponent
{
    private ChessBoardManager chessBoardManager;
    private int currentX;
    private int currentY;
    private boolean isSelected=false;

    public void onAwake()
    {
        chessBoardManager = getGameObject().getComponent(ChessBoardManager.class);
    }

    public void onStart()
    {
        isSelected = true;
    }


    public void update()
    {

    }
    @Override
    public void render(GraphicsContext gc)
    {
        drawSelector(gc,isSelected);
    }
    private void drawSelector(GraphicsContext gc,boolean isSelected)
    {
        // 获取当前选中的格子
        currentX = chessBoardManager.getPointingX();
        currentY = chessBoardManager.getPointingY();
        float[] pos = ChessBoardManager.coordToTransformPos(currentX, currentY);
        float cx = pos[0] + 40; // 棋子大小 80x80，取中心
        float cy = pos[1] + 40;
        float size = 80;          // 方框大小
        float half = size / 2f;
        float corner = 14f;       // 折角长度
        float lineWidth = 5f;
        // 画笔设置
        gc.setStroke(Color.CORNFLOWERBLUE);
        //如果进入选中状态,变为呼吸光
        if(isSelected)
        {
            double t = System.currentTimeMillis() / 400.0;
            double k = (Math.sin(t) + 1) / 2.0;            // 映射到 0~1
            Color c = new Color(
                    0.2 + 0.6 * k,     // R
                    0.4 + 0.4 * k,     // G
                    1.0,  // B
                    1.0
            );
            gc.setStroke(c);
        }
        gc.setLineWidth(lineWidth);
        gc.setLineDashes(); // 取消虚线
        // 左上角
        gc.strokeLine(cx - half, cy - half, cx - half + corner, cy - half);
        gc.strokeLine(cx - half, cy - half, cx - half, cy - half + corner);
        // 右上角
        gc.strokeLine(cx + half, cy - half, cx + half - corner, cy - half);
        gc.strokeLine(cx + half, cy - half, cx + half, cy - half + corner);
        // 左下角
        gc.strokeLine(cx - half, cy + half, cx - half + corner, cy + half);
        gc.strokeLine(cx - half, cy + half, cx - half, cy + half - corner);
        // 右下角
        gc.strokeLine(cx + half, cy + half, cx + half - corner, cy + half);
        gc.strokeLine(cx + half, cy + half, cx + half, cy + half - corner);
        // 中心小点
        gc.setFill(Color.CORNFLOWERBLUE);
        gc.fillOval(cx - 3, cy - 3, 6, 6);
    }
}
