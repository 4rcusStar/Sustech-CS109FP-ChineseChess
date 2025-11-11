import Engine.*;
import javax.swing.*;

public class Chessboard extends Component
{
    static final int BOARD_WIDTH = 9;
    static final int BOARD_HEIGHT = 9;
    private ChessPiece[][] coord = new ChessPiece[BOARD_WIDTH][BOARD_HEIGHT];



    public void awake()
    {
        System.out.println(getName()+" Awake");
        createChessPieces();
    }

    /**
     * 初始化创建并添加各类棋子
     */
    void createChessPieces()
    {
        //TODO:添加完整的一套棋子以完成游戏初始化
    }

    /**
     * 在棋盘x,y处添加指定棋子
     * @param chessPiece 要添加的棋子
     * @param x
     * @param y
     */
    public void addChessPiece(ChessPiece chessPiece, int x, int y)
    {
        if(!isOccupied(x,y))
        {
            coord[x][y] = chessPiece;
        }
        else
        {
            System.err.printf("ChessPiece already exists at(%d,%d)\n",x,y);
        }
    }

    /**
     * 获取在x,y上的棋子
     * @param x
     * @param y
     * @return 在(x,y)上的棋子，如果棋子不存在，则返回null;
     */
    public ChessPiece getChessPiece(int x, int y)
    {
        if(x<BOARD_WIDTH && y<BOARD_HEIGHT)
            return coord[x][y];
        else
            System.err.println("Invalid coordinate input");
        return null;
    }

    /**
     * 将任意棋子移动至任意位置。提醒：这是无视规则的顶层操作
     * @param chessPiece 要移动的棋子
     * @param x 目标x
     * @param y 目标Y
     */
    public void moveChessPiece(ChessPiece chessPiece, int x, int y)
    {
        if(getChessPiece(x,y)!=null)
        {
            System.err.println("ChessPiece already exists at destination");
            return;
        }
        int postX = chessPiece.x;
        int postY = chessPiece.y;
        coord[x][y] = chessPiece;
        coord[postX][postY] = null;
    }

    /**
     * 打印所有棋子在棋盘上的坐标
     */
    public void printBoard()
    {
        for(int i=0;i<BOARD_WIDTH;i++)
        {
            for (int j=0;j<BOARD_HEIGHT;j++)
            {
                if(coord[i][j]!=null)
                {
                    ChessPiece chessPiece=coord[i][j];
                    System.out.printf(chessPiece.getName()+"At(%d,%d)\n",i,j);
                }
            }
        }
    }

    /**
     *x,y位置是否已经被占
     * @param x
     * @param y
     * @return x,y位置是否已经被占
     */
    public boolean isOccupied(int x, int y)
    {
        return coord[x][y]!=null;
    }

    public void start()
    {
    }

    public void update()
    {

    }
}
