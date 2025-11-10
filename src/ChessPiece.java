import Engine.Component;

import javax.swing.*;

public abstract class ChessPiece extends Component
{
    protected GameManager manager = GameManager.getInstance();
    protected Chessboard chessboard;
    protected JLabel chessLabel;
    //棋子阵营：默认红色
    protected ChessPieceSide side=ChessPieceSide.RED;
    protected int x;
    protected int y;

    public ChessPiece()
    {
        register();
    }
    public void awake()
    {
        chessboard = manager.getBoard();
    }

    protected void moveTo(int destinationX, int destinationY)
    {
        chessboard.moveChessPiece(this, destinationX, destinationY);
    }


}
