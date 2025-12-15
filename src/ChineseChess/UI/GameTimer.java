package ChineseChess.UI;

import ChineseChess.ChessBoard.ChessBoardManager;
import ChineseChess.ChessPiece.Side;
import ChineseChess.UsersAndSavingSystem.SaveData;
import ChineseChess.UsersAndSavingSystem.SaveService;
import Engine.Components.RendererComponent;
import Engine.Components.Transform;
import Engine.Core.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * 游戏计时器组件
 * 显示总游戏时间、红方时间和黑方时间
 */
public class GameTimer extends RendererComponent
{
    private static final float SIDEBAR_WIDTH = 200f;
    private static final int FONT_SIZE = 16;
    private static final String FONT_FAMILY = "Arial";

    private ChessBoardManager chessBoard;
    private float originX;
    private float originY;

    // 计时器数据（毫秒）
    private long totalMillis = 0;
    private long redMillis = 0;
    private long blackMillis = 0;

    private long lastUpdateTime;

    @Override
    public void onAwake()
    {
        Transform transform = getGameObject().getComponent(Transform.class);
        originX = transform.getX();
        originY = transform.getY();

        GameObject parent = getGameObject().getParent();
        if (parent != null)
        {
            GameObject chessBoardObj = parent.getChild("ChessBoard");
            if (chessBoardObj != null)
            {
                chessBoard = chessBoardObj.getComponent(ChessBoardManager.class);
            }
        }

        lastUpdateTime = System.currentTimeMillis();
    }

    @Override
    public void onStart()
    {
        // 从SaveData加载时间（优先使用缓存，因为ChessWorldConstructor已经加载过了）
        SaveData data = SaveService.getCachedSave();
        if (data == null)
        {
            // 如果缓存不存在，尝试直接加载
            data = SaveService.load();
        }
        
        // 如果存在存档且游戏未结束，恢复计时器时间
        if (data != null && !data.isGameOver)
        {
            setTimes(data.totalMillis, data.redMillis, data.blackMillis);
        }
    }

    @Override
    public void update()
    {
        if (chessBoard == null) return;

        // 如果游戏结束，暂停计时
        if (chessBoard.isGameOver())
        {
            return;
        }

        long currentTime = System.currentTimeMillis();
        long delta = currentTime - lastUpdateTime;
        lastUpdateTime = currentTime;

        // 更新总时间
        totalMillis += delta;

        // 根据当前回合方更新对应的时间
        Side currentSide = chessBoard.getCurrentSide();
        if (currentSide == Side.RED)
        {
            redMillis += delta;
        }
        else
        {
            blackMillis += delta;
        }
    }

    @Override
    public void render(GraphicsContext gc)
    {
        gc.setFont(Font.font(FONT_FAMILY, FontWeight.NORMAL, FONT_SIZE));

        float centerX = originX + (SIDEBAR_WIDTH / 2f);
        
        // Total 在 Surrender 按钮上方（Surrender 在 originY + 400f，Total 在其上方 30f）
        float totalY = originY + 370f;
        
        // Red 在 "Red's turn" 上方（Red's turn 在 originY + 150f，Red 在其上方 25f）
        float redY = originY + 125f;
        
        // Black 在 "Black's turn" 下方（Black's turn 在 originY + 650f，Black 在其下方 25f）
        float blackY = originY + 675f;

        String totalText = "Total: " + formatTime(totalMillis);
        String redText = "Red: " + formatTime(redMillis);
        String blackText = "Black: " + formatTime(blackMillis);

        // 绘制总时间（蓝色，居中在 Surrender 上方）
        float totalWidth = estimateTextWidth(totalText, FONT_SIZE);
        gc.setFill(Color.rgb(0, 100, 200)); // 蓝色
        gc.fillText(totalText, centerX - totalWidth / 2f, totalY);

        // 绘制红方时间（红色，在 Red's turn 上方）
        float redWidth = estimateTextWidth(redText, FONT_SIZE);
        gc.setFill(Color.rgb(200, 32, 32)); // 红色
        gc.fillText(redText, centerX - redWidth / 2f, redY);

        // 绘制黑方时间（黑色，在 Black's turn 下方）
        float blackWidth = estimateTextWidth(blackText, FONT_SIZE);
        gc.setFill(Color.rgb(32, 32, 32)); // 黑色
        gc.fillText(blackText, centerX - blackWidth / 2f, blackY);
    }

    /**
     * 格式化时间为 MM:SS 格式
     */
    private String formatTime(long millis)
    {
        long totalSeconds = millis / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * 估算文本宽度（简单估算，每个字符约 FONT_SIZE * 0.6 宽度）
     */
    private float estimateTextWidth(String text, int fontSize)
    {
        return text.length() * fontSize * 0.6f;
    }

    /**
     * 设置计时器时间（用于加载存档）
     */
    public void setTimes(long total, long red, long black)
    {
        this.totalMillis = total;
        this.redMillis = red;
        this.blackMillis = black;
    }

    /**
     * 获取计时器时间（用于保存存档）
     */
    public long[] getTimes()
    {
        return new long[]{totalMillis, redMillis, blackMillis};
    }
}

