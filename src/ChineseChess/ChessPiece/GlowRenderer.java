package ChineseChess.ChessPiece;

import ChineseChess.ChessBoard.ChessBoardManager;
import Engine.Components.RendererComponent;
import Engine.Components.Transform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GlowRenderer extends RendererComponent
{
    private ChessPieceManager piece;
    private ChessBoardManager board;
    private Transform transform;

    @Override
    public void onAwake()
    {
        piece = getGameObject().getComponent(ChessPieceManager.class);
        board = getGameObject().getParent().getComponent(ChessBoardManager.class);
        transform = getGameObject().getComponent(Transform.class);

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

        Color glow = new Color(0, 0.867, 1, alpha);
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
}
