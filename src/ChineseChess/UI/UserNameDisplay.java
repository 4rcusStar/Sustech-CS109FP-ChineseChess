package ChineseChess.UI;

import ChineseChess.UsersAndSavingSystem.User;
import ChineseChess.UsersAndSavingSystem.UserManager;
import Engine.Components.RendererComponent;
import Engine.Components.Transform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * 显示当前用户名的组件
 * 显示在SideBarUI底部
 */
public class UserNameDisplay extends RendererComponent
{
    private static final int FONT_SIZE = 12;
    private static final String FONT_FAMILY = "Arial";
    private float originX;
    private float originY;

    @Override
    public void onAwake()
    {
        Transform transform = getGameObject().getComponent(Transform.class);
        originX = transform.getX();
        originY = transform.getY();
        setRenderPriority(10); // 较高的优先级，确保显示在其他元素之上
    }

    @Override
    public void render(GraphicsContext gc)
    {
        UserManager userManager = UserManager.getInstance();
        String displayText;

        if (userManager.isGuestMode())
        {
            displayText = "User: 游客";
        }
        else
        {
            User currentUser = userManager.getCurrentUser();
            if (currentUser != null)
            {
                displayText = "User: " + currentUser.getUserName();
            }
            else
            {
                displayText = "User: 未登录";
            }
        }

        // 设置小字体
        gc.setFont(Font.font(FONT_FAMILY, FontWeight.NORMAL, FONT_SIZE));
        
        // 计算文字位置（底部）
        float textY = originY + 790f; // 底部位置（canvas高度800，减去一些边距）
        
        // 绘制文字（左对齐，因为SideBarUI在右侧）
        gc.setFill(Color.BLACK);
        gc.fillText(displayText, originX + 10f, textY);
    }
}

