package ChineseChess.UI;

import Engine.Components.RendererComponent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * 无效移动提示浮框
 * 显示"无法送将，请重新考虑"的提示信息
 */
public class InvalidMoveToast extends RendererComponent
{
    private float displayTime = 0f; // 显示时间（秒）
    private static final float DISPLAY_DURATION = 2.0f; // 显示持续时间
    private static final float TOAST_WIDTH = 300f;
    private static final float TOAST_HEIGHT = 80f;
    private static final float TOAST_X = 250f; // 棋盘中心位置
    private static final float TOAST_Y = 350f;
    private static final int FONT_SIZE = 18;
    private static final String FONT_FAMILY = "Arial";
    private static final String MESSAGE = "无法送将，请重新考虑";
    
    private boolean isVisible = false;
    
    @Override
    public void onAwake()
    {
        // 设置较高的渲染优先级，确保浮框显示在最上层
        setRenderPriority(1000);
    }
    
    @Override
    public void update()
    {
        if (isVisible)
        {
            displayTime += Engine.Core.GameEngine.getDeltaTime() / 1000.0f;
            if (displayTime >= DISPLAY_DURATION)
            {
                isVisible = false;
                displayTime = 0f;
            }
        }
    }
    
    /**
     * 显示提示
     */
    public void show()
    {
        isVisible = true;
        displayTime = 0f;
    }
    
    @Override
    public void render(GraphicsContext gc)
    {
        if (!isVisible) 
        {
            return;
        }
        
        // 计算透明度
        float alpha = 1.0f;
        if (displayTime < 0.2f)
        {
            // 淡入
            alpha = displayTime / 0.2f;
        }
        else if (displayTime > DISPLAY_DURATION - 0.3f)
        {
            // 淡出
            alpha = (DISPLAY_DURATION - displayTime) / 0.3f;
        }
        
        // 绘制背景框
        gc.setFill(Color.rgb(50, 50, 50, alpha * 0.9));
        gc.fillRoundRect(TOAST_X, TOAST_Y, TOAST_WIDTH, TOAST_HEIGHT, 10, 10);
        
        // 绘制边框
        gc.setStroke(Color.rgb(255, 100, 100, alpha));
        gc.setLineWidth(2);
        gc.strokeRoundRect(TOAST_X, TOAST_Y, TOAST_WIDTH, TOAST_HEIGHT, 10, 10);
        
        // 绘制文字
        gc.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, FONT_SIZE));
        gc.setFill(Color.rgb(255, 255, 255, alpha));
        
        // 计算文字居中位置
        Text textNode = new Text(MESSAGE);
        textNode.setFont(gc.getFont());
        double textWidth = textNode.getBoundsInLocal().getWidth();
        double textHeight = textNode.getBoundsInLocal().getHeight();
        
        float textX = TOAST_X + (TOAST_WIDTH / 2f) - (float)(textWidth / 2);
        float textY = TOAST_Y + (TOAST_HEIGHT / 2f) + (float)(textHeight / 4);
        
        gc.fillText(MESSAGE, textX, textY);
    }
}

