package ChineseChess.ChessPiece;

import ChineseChess.ChessBoard.ChessBoardManager;
import Engine.Components.RendererComponent;
import Engine.Components.Transform;
import Engine.Components.SpriteRenderer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public class GlowRenderer extends RendererComponent
{
    private ChessPieceManager piece;
    private ChessBoardManager board;
    private Transform transform;
    private SpriteRenderer spriteRenderer;
    private double r = 0;
    private double g = 0.867;
    private double b = 1;

    /**
     * 设置发光的颜色
     * @param r
     * @param g
     * @param b
     */
    public void setColor(double r, double g, double b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    @Override
    public void onAwake()
    {
        piece = getGameObject().getComponent(ChessPieceManager.class);
        board = getGameObject().getParent().getComponent(ChessBoardManager.class);
        transform = getGameObject().getComponent(Transform.class);
        spriteRenderer = getGameObject().getComponent(SpriteRenderer.class);

        // 在棋子下层渲染
        setRenderPriority(-10);
    }

    @Override
    public void render(GraphicsContext gc)
    {
       
        if (piece.getSide() != board.getCurrentSide())
            return;


        float x = transform.getX();
        float y = transform.getY();

        float radius = 78;

        double t = (Math.sin(System.currentTimeMillis() / 300.0) + 1) * 0.5;
        double alpha = 0.6 * t;

        Color glow = new Color(r, g, b, alpha);
        gc.setFill(glow);
        gc.fillOval(x, y, radius, radius);

        for (int i = 0; i < 4; i++)
        {
            double a = (0.20 - i * 0.045) * (0.2+0.6 * t);  // 逐层透明
            if (a <= 0) break;

            gc.setStroke(new Color(0, 0.867, 1, a));
            gc.setLineWidth(3 + i * 2);
            gc.strokeOval(x, y, radius, radius);
        }
    }

    /**
     * 采样棋子贴图底部像素颜色，用作阴影基色
     */
    private Color sampleBottomPixelColor()
    {
        if (spriteRenderer == null)
        {
            return Color.color(r, g, b);
        }
        Image img = spriteRenderer.getSprite();
        if (img == null || img.getPixelReader() == null)
        {
            return Color.color(r, g, b);
        }
        int px = Math.max(0, (int)(img.getWidth() / 2));
        int py = Math.max(0, (int)(img.getHeight() - 1));
        try
        {
            return img.getPixelReader().getColor(px, py);
        }
        catch (Exception e)
        {
            return Color.color(r, g, b);
        }
    }
}
