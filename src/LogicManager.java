import Engine.*;
import javax.swing.*;

public class LogicManager extends Component
{
    private static LogicManager instance;
    private Chessboard board;
    /**
     * 获取棋盘的唯一入口
     * @return 棋盘
     */
    public Chessboard getBoard()
    {
        return board;
    }

    /**
     * 获取GameManager单例
     * @return GameManager单例
     */
    public static LogicManager getInstance()
    {
        if (instance == null)
        {
            synchronized (LogicManager.class)
            {
                if (instance == null)
                {
                    instance = new LogicManager();
                }
            }
        }
        return instance;
    }

    public void awake()
    {
        System.out.println(getName()+" Awake");
        GameObject boardObj = new GameObject("Chessboard");
        boardObj.addComponent(new Chessboard());
    }

    public void start()
    {
        //System.out.println(getName()+" Start");
    }
    public void update()
    {
        //System.out.println(getName()+" Update");
    }
}
