package ChineseChess.UI;

import ChineseChess.ChessBoard.ChessBoardManager;
import ChineseChess.ChessPiece.Side;
import Engine.Components.RendererComponent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class WinningStatus extends RendererComponent
{
    private static final int NORMAL_FONT_SIZE = 28;
    private static final String FONT_FAMILY = "Arial";
    private static final double OVERLAY_ALPHA = 0.5;
    private static final double LINE_SPACING = 12;

    private ChessBoardManager chessBoard;
    @Override
    public void onAwake()
    {
        chessBoard = getGameObject().getParent().getChild("ChessBoard").getComponent(ChessBoardManager.class);
        this.setRenderPriority(1000);
    }
    @Override
    public void update()
    {

    }
    @Override
    public void render(GraphicsContext gc)
    {
        if(chessBoard.isGameOver())
        {
            Side winnerSide = chessBoard.getWinnerSide();
            String reason = chessBoard.getEndReason();

            double width = gc.getCanvas().getWidth();
            double height = gc.getCanvas().getHeight();

            // 半透明幕布
            gc.setFill(new Color(0, 0, 0, OVERLAY_ALPHA));
            double overlayHeight = height * 0.18;
            double overlayY = height * 0.41;
            gc.fillRect(0, overlayY, width, overlayHeight);

            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(FONT_FAMILY, NORMAL_FONT_SIZE));
            String line1 = "Game Over";
            String line2 = "Winner: " + winnerSide;
            String line3 = (reason != null) ? reason : "";

            double centerX = width / 2.0;
            double baseY = overlayY + overlayHeight / 2.0 - NORMAL_FONT_SIZE;
            drawCenteredLine(gc, line1, centerX, baseY);
            drawCenteredLine(gc, line2, centerX, baseY + NORMAL_FONT_SIZE + LINE_SPACING);
            if (!line3.isEmpty())
            {
                drawCenteredLine(gc, line3, centerX, baseY + 2 * (NORMAL_FONT_SIZE + LINE_SPACING));
            }
        }
    }

    private void drawCenteredLine(GraphicsContext gc, String text, double centerX, double y)
    {
        if (text == null || text.isEmpty()) return;
        javafx.scene.text.Text measure = new javafx.scene.text.Text(text);
        measure.setFont(gc.getFont());
        double textWidth = measure.getBoundsInLocal().getWidth();
        gc.fillText(text, centerX - textWidth / 2.0, y);
    }
}
