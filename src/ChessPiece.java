import Engine.*;
import javax.swing.*;

public abstract class ChessPiece extends Component
{

    //棋子阵营：默认红色
    protected ChessPieceSide side=ChessPieceSide.RED;
    protected int x;
    protected int y;

    public void awake()
    {
    }

    protected void moveTo(int destinationX, int destinationY)
    {

    }

    abstract boolean isValidMove(int destinationX, int destinationY);

}
