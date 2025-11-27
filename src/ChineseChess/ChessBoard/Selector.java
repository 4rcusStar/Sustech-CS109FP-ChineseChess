package ChineseChess.ChessBoard;

import ChineseChess.ChessPiece.ChessPiece;
import ChineseChess.ChessPiece.ChessPieceManager;
import Engine.Components.RendererComponent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Selector extends RendererComponent
{
    private ChessBoardManager board;

    private int hoverX;
    private int hoverY;

    private int selectedX = -1;
    private int selectedY = -1;

    private ChessPiece lastSelected;
    //每个移动点动画开始的时间
    private Map<String,Long> moveAnimStartTime = new HashMap<>();
    private Map<String,Long> eatAnimStartTime = new HashMap<>();


    @Override
    public void onAwake()
    {
        board = getGameObject().getComponent(ChessBoardManager.class);
        setRenderPriority(999);
    }

    public void onStart()
    {
        lastSelected = null;
    }

    @Override
    public void update()
    {
        hoverX = board.getPointingX();
        hoverY = board.getPointingY();

        //当前被选中的棋子
        ChessPiece currentSelected = board.getSelectedChessPiece();
        //如果选中状态变化，则刷新固定框位置,刷新动画
        if (currentSelected != lastSelected)
        {
            moveAnimStartTime.clear();
            if (currentSelected != null)
            {
                selectedX = currentSelected.getComponent(ChessPieceManager.class).getCoordX();
                selectedY = currentSelected.getComponent(ChessPieceManager.class).getCoordY();
                //设置动画时间
                for(int[]place:board.getMovablePlaces())
                {
                    String moveKey = place[0]+"_"+place[1];
                    moveAnimStartTime.put(moveKey,System.currentTimeMillis());
                }
                for(int[]place:board.getEatablePlaces())
                {
                    String eatKey = place[0]+"_"+place[1];
                    eatAnimStartTime.put(eatKey,System.currentTimeMillis());
                }
            }
            else
            {
                selectedX = -1;
                selectedY = -1;
            }
        }

        lastSelected = currentSelected;
    }

    @Override
    public void render(GraphicsContext gc)
    {
        drawHoverBox(gc);      // 跟随鼠标
        drawSelectedBox(gc);// 固定呼吸框
        drawMovableBoxes(gc);
        drawEatableBoxes(gc);
    }

    private synchronized void drawMovableBoxes(GraphicsContext gc)
    {
        if(selectedX<0)return;

        List<int[]> movablePlaces = new ArrayList<>(board.getMovablePlaces());
        float maxRadius = 22f;//最大半径
        float duration = 180f;//动画时间
        long nowTime = System.currentTimeMillis();

        for(int[] place:movablePlaces)
        {
            float[] transPos = ChessBoardManager.coordToTransformPos(place[0],place[1]);
            float centerX = transPos[0]+40;
            float centerY = transPos[1]+40;
            String key = place[0]+"_"+place[1];
            long startTime = moveAnimStartTime.getOrDefault(key,nowTime);

            float t =Math.min(1,(nowTime-startTime)/duration);
            float scaleWithTime = (float)(1-Math.pow(1-t,2));

            float radius = maxRadius * scaleWithTime;
            float alpha = Math.max(0.4f*scaleWithTime,0);

            gc.setFill(new Color(0.39215687F, 0.58431375F, 0.92941177F,alpha));//CORN FLOW BLUE
            gc.fillOval(centerX-radius,centerY-radius,radius*2,radius*2);
            //System.out.println("t=" + t + ", radius=" + radius + ", alpha=" + alpha);
        }
        //System.out.println("movablePlaces=" + movablePlaces.size());

    }

    private synchronized void drawEatableBoxes(GraphicsContext gc)
    {
        if (selectedX < 0) return;
        List<int[]> eatablePlaces = new ArrayList<>(board.getEatablePlaces());
        float crossLength = 80f;
        float duration = 400f;
        long nowTime = System.currentTimeMillis();

        for(int[] place:eatablePlaces)
        {
            float[] transPos = ChessBoardManager.coordToTransformPos(place[0],place[1]);
            float currentX = transPos[0];
            float currentY = transPos[1];
            String key = place[0]+"_"+place[1];
            long startTime = eatAnimStartTime.getOrDefault(key,nowTime);
            float t =Math.min(1,(nowTime-startTime)/duration);
            float scaleWithTime = (float)(1-Math.pow(1-t,2));

            float alpha = Math.max(0.6f*scaleWithTime,0);
            float dLength = scaleWithTime * crossLength;

            gc.setStroke(new Color(0.75, 0.03, 0.01,alpha));
            gc.strokeLine(currentX,currentY,currentX+dLength,currentY+dLength);
        }


    }

    /**
     * 渲染随鼠标移动的瞄准框
     * @param gc gc
     */
    private void drawHoverBox(GraphicsContext gc)
    {
        float[] pos = ChessBoardManager.coordToTransformPos(hoverX, hoverY);

        gc.setStroke(Color.CORNFLOWERBLUE);
        gc.setLineWidth(3);

        drawCornerBox(gc, pos[0], pos[1], 80, 16);
    }

    /**
     * 渲染选中物体的瞄准框
     * @param gc
     */
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
