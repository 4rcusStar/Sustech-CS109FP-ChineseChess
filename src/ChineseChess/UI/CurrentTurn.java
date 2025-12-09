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
    private static final float SIDEBAR_WIDTH = 200f; 
    private static final float SIDEBAR_HEIGHT = 800f; 
    private static final float TEXT_OFFSET_Y = 150f;
    private static final float CHECKMATE_TEXT_OFFSET_Y = 100f;
    private static final float TURN_COUNT_OFFSET_Y = 260f; // 回合数显示位置，位于投降按钮下方
    private static final int NORMAL_FONT_SIZE = 20;
    private static final int WARNING_FONT_SIZE = 18;
    private static final String FONT_FAMILY = "Arial";
    private ChessBoardManager chessBoard;
    private SurrenderButton surrenderButton;
    private Side lastSide;
    private float turnTextY;
    private float animStartY;
    private float animTargetY;
    private float animElapsed = 1f;
    private static final float ANIM_DURATION = 1f; // 归一化时长，进度=elapsed/duration

    // 颜色渐变（文本和按钮同步）
    private Color textCurrentColor;
    private Color textStartColor;
    private Color textTargetColor;
    private Color btnNormalStart;
    private Color btnNormalTarget;
    private Color btnHoverStart;
    private Color btnHoverTarget;
    private Color btnBorderStart;
    private Color btnBorderTarget;

    private static final Color RED_TEXT = Color.rgb(200, 32, 32);
    private static final Color BLACK_TEXT = Color.rgb(32, 32, 32);
    private static final Color RED_NORMAL = Color.rgb(220, 20, 60);
    private static final Color RED_HOVER = Color.rgb(178, 34, 34);
    private static final Color BLACK_NORMAL = Color.rgb(48, 48, 48);
    private static final Color BLACK_HOVER = Color.rgb(28, 28, 28);
    private static final Color RED_BORDER = Color.rgb(255, 0, 0);
    private static final Color BLACK_BORDER = Color.rgb(0, 0, 0);
    public void onAwake()
    {
        Transform transform = getGameObject().getComponent(Transform.class);
        originX = transform.getX();
        originY = transform.getY();
        chessBoard = getGameObject().getParent().getChild("ChessBoard").getComponent(ChessBoardManager.class);
        surrenderButton = getGameObject().getComponent(SurrenderButton.class);
        lastSide = chessBoard.getCurrentSide();
        animTargetY = getTargetYForSide(lastSide);
        animStartY = animTargetY;
        turnTextY = animTargetY;

        // 初始化颜色
        textCurrentColor = (lastSide == Side.RED) ? RED_TEXT : BLACK_TEXT;
        textTargetColor = textCurrentColor;
        textStartColor = textCurrentColor;
        btnNormalStart = btnNormalTarget = (lastSide == Side.RED) ? RED_NORMAL : BLACK_NORMAL;
        btnHoverStart = btnHoverTarget = (lastSide == Side.RED) ? RED_HOVER : BLACK_HOVER;
        btnBorderStart = btnBorderTarget = (lastSide == Side.RED) ? RED_BORDER : BLACK_BORDER;

        // 允许外部控制按钮颜色
        if (surrenderButton != null)
        {
            surrenderButton.setExternalColorControl(true);
            applyButtonColors(btnNormalTarget, btnHoverTarget, btnBorderTarget);
        }
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
            return;
        }

        Side current = chessBoard.getCurrentSide();
        if(current != lastSide)
        {
            // 触发动画：从当前Y缓动到目标Y
            animStartY = turnTextY;
            animTargetY = getTargetYForSide(current);
            animElapsed = 0f;
            lastSide = current;

            // 颜色渐变起止
            textStartColor = textCurrentColor;
            textTargetColor = (current == Side.RED) ? RED_TEXT : BLACK_TEXT;
            btnNormalStart = btnNormalTarget;
            btnHoverStart = btnHoverTarget;
            btnBorderStart = btnBorderTarget;
            btnNormalTarget = (current == Side.RED) ? RED_NORMAL : BLACK_NORMAL;
            btnHoverTarget = (current == Side.RED) ? RED_HOVER : BLACK_HOVER;
            btnBorderTarget = (current == Side.RED) ? RED_BORDER : BLACK_BORDER;
        }

        if (animElapsed < ANIM_DURATION)
        {
            animElapsed = Math.min(ANIM_DURATION, animElapsed + ANIM_DURATION * 0.05f); // 0.05 为每帧归一化增量
            float progress = animElapsed / ANIM_DURATION;
            float eased = progress * progress; // easeIn
            turnTextY = animStartY + (animTargetY - animStartY) * eased;

            textCurrentColor = lerpColor(textStartColor, textTargetColor, eased);
            Color normal = lerpColor(btnNormalStart, btnNormalTarget, eased);
            Color hover = lerpColor(btnHoverStart, btnHoverTarget, eased);
            Color border = lerpColor(btnBorderStart, btnBorderTarget, eased);
            applyButtonColors(normal, hover, border);
        }
        else
        {
            turnTextY = animTargetY;
            textCurrentColor = textTargetColor;
            applyButtonColors(btnNormalTarget, btnHoverTarget, btnBorderTarget);
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
        gc.setFill(textCurrentColor);
        gc.fillText(turnText, centeredX, turnTextY);

        // 显示当前回合数
        String turnCountText = "Turn: " + chessBoard.getTurnNumber();
        float turnCountX = getCenteredX(gc, turnCountText);
        gc.fillText(turnCountText, turnCountX, originY + TURN_COUNT_OFFSET_Y);
    
        // 只在当前回合方被将时显示警告
        Side currentSide = chessBoard.getCurrentSide();
        
        // 显示黑方被将警告
        if(currentSide == Side.BLACK && chessBoard.isBlackInCheck())
        {
            setFont(gc, WARNING_FONT_SIZE);
            String checkText = "Check!";
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
            String checkText = "Check!";
            String firstLine = checkText.split("\n")[0];
            float checkCenteredX = getCenteredX(gc, firstLine);
            gc.setFill(Color.RED);
            gc.fillText(checkText, checkCenteredX, originY + CHECKMATE_TEXT_OFFSET_Y);
            setFont(gc, NORMAL_FONT_SIZE);
        }
    }

    /**
     * 根据阵营返回动画目标Y坐标
     */
    private float getTargetYForSide(Side side)
    {
        if(side == Side.RED)
        {
            return originY + TEXT_OFFSET_Y;
        }
        else
        {
            return originY + (SIDEBAR_HEIGHT - TEXT_OFFSET_Y);
        }
    }

    private Color lerpColor(Color a, Color b, double t)
    {
        double clamped = Math.min(1.0, Math.max(0.0, t));
        double r = a.getRed() + (b.getRed() - a.getRed()) * clamped;
        double g = a.getGreen() + (b.getGreen() - a.getGreen()) * clamped;
        double bl = a.getBlue() + (b.getBlue() - a.getBlue()) * clamped;
        double al = a.getOpacity() + (b.getOpacity() - a.getOpacity()) * clamped;
        return new Color(r, g, bl, al);
    }

    private void applyButtonColors(Color normal, Color hover, Color border)
    {
        if (surrenderButton != null)
        {
            surrenderButton.setColors(normal, hover);
            surrenderButton.setBorderColor(border);
        }
    }

    
}
