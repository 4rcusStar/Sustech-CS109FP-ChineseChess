import Engine.*;
import javax.swing.*;

public class GameManager extends Component
{
    private static GameManager instance;
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
    JLabel title;

    private GameManager()
    {
        if(instance !=null)
            throw new RuntimeException("Please use singleton GameManager.getInstance()");
    }
    public void start()
    {
        //测试
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Chessboard");
        frame.setVisible(true);
        frame.setSize(800, 600);
        title = new JLabel("Chessboard");
        title.setHorizontalTextPosition(JLabel.CENTER);                                    //设置文本相对于图像的水平位置
        title.setVerticalTextPosition(JLabel.BOTTOM);
        frame.add(title);
    }
    public void update()
    {
        //测试
        title.setText(Long.toString(System.currentTimeMillis()));
    }
}
