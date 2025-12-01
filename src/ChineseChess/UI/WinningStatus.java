package ChineseChess.UI;

import ChineseChess.ChessBoard.ChessBoardManager;
import ChineseChess.ChessPiece.Side;
import Engine.Components.RendererComponent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class WinningStatus extends RendererComponent
{
    private float originX = 800;
    private float originY = 0;
    private static final float TEXT_OFFSET_X = 50f;
    private static final float TEXT_OFFSET_Y = 50f;
    private static final int NORMAL_FONT_SIZE = 20;
    private static final String FONT_FAMILY = "Arial";

    private ChessBoardManager chessBoard;
    @Override
    public void onAwake()
    {
        chessBoard = getGameObject().getParent().getChild("ChessBoard").getComponent(ChessBoardManager.class);
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
            gc.setFill(Color.BLACK);
            gc.setFont(Font.font(FONT_FAMILY, NORMAL_FONT_SIZE));
            gc.fillText("Game Over", originX+TEXT_OFFSET_X, originY+TEXT_OFFSET_Y);
            gc.fillText("Winner:"+winnerSide, originX+TEXT_OFFSET_X, originY+TEXT_OFFSET_Y+20);
        }
    }
}
