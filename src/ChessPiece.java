import Engine.*;
import javax.swing.*;

public abstract class ChessPiece extends Component
{
    protected LogicManager manager = LogicManager.getInstance();
    protected Chessboard chessboard;
    protected JLabel chessLabel;
    //棋子阵营：默认红色
    protected ChessPieceSide side=ChessPieceSide.RED;
    protected int x;
    protected int y;

    public void awake()
    {
        chessboard = manager.getBoard();
    }

    protected void moveTo(int destinationX, int destinationY)
    {
        if(isValidMove(destinationX, destinationY))
            chessboard.moveChessPiece(this, destinationX, destinationY);
    }

    abstract boolean isValidMove(int destinationX, int destinationY);

}
