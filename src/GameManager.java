import Engine.*;
import javax.swing.*;

public class GameManager extends Component
{
    private static GameManager instance;
    private JFrame frame;
    private Chessboard board;

    /**
     * GameManager负责管理游戏的唯一JFrame,获取JFrame的唯一入口
     * @return 游戏界面的JFrame
     */
    public JFrame getFrame()
    {
        return frame;
    }

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
     * @return
     */
    public static GameManager getInstance()
    {
        if (instance == null)
        {
            synchronized (GameManager.class)
            {
                if (instance == null)
                {
                    instance = new GameManager();
                }
            }
        }
        return instance;
    }

    private GameManager()
    {
        if(instance !=null)
            throw new RuntimeException("Please use singleton GameManager.getInstance()");
        register();
    }

    public void awake()
    {
        System.out.println("This is GameManager's awake");
        frame = new JFrame("Chessboard");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        board = new Chessboard();
    }

    public void start()
    {
        //测试
        //System.out.println("This is GameManager's start");
    }
    public void update()
    {
        //System.out.println("This is GameManager's update");
    }
}
