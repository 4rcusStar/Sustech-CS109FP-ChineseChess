package ChineseChess.Scene;

import Engine.GameBuilding.GameWorld;
import Engine.Components.RendererComponent;
import Engine.GameBuilding.GameWorldManager;
import Engine.Input;
import Engine.Audio.AudioManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import ChineseChess.UsersAndSavingSystem.UserManager;


public class MainMenuWorld extends GameWorld
{
    public MainMenuWorld()
    {
        super(new MainMenuWorldConstructor());
    }

    @Override
    public void onEnter()
    {
        super.onEnter();
        // 确保播放主菜单背景音乐
        AudioManager.getInstance().playBGM(AudioManager.BGMType.MAIN_MENU);
    }

    @Override
    public void onExit()
    {
        super.onExit();
    }
}

/**
 * 主菜单渲染
 */
class MainMenuRenderer extends RendererComponent
{
    
    @Override
    public void onAwake()
    {
        setRenderPriority(0);
    }
    @Override
    public void update()
    {
        if(Input.isMouseClicked())
        {
            GameWorldManager worldManager = GameWorldManager.getInstance();
            worldManager.switchGameWorldTo("Game");
        }
        
    }

        
    @Override

    public void render(GraphicsContext gc)
    {
        UserManager userManager = UserManager.getInstance();

        // 绘制提示文字（下调50像素：从400改为450）
        String startText = "点击开始游戏";
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 24));
        
        // 估算文字宽度和高度
        float textWidth = estimateTextWidth(startText, 24);
        float textHeight = 24;
        float textX = 420;
        float textY = 450;
        
        gc.setFill(Color.rgb(0, 0, 0, 0.6)); // 黑色，60%透明度
        float padding = 10f;
        float boxWidth = (textWidth + padding * 2) * 2; 

        float boxX = textX - (boxWidth - textWidth) / 2;
        float boxY = textY - textHeight - padding;
        gc.fillRoundRect(boxX+25, boxY, boxWidth, textHeight + padding * 2, 5, 5);
        
        // 绘制文字
        gc.setFill(Color.WHITE);
        gc.fillText(startText, textX, textY);

        // 如果是游客模式，显示提示
        if(userManager.isGuestMode())
        {
            String guestText = "游客模式 - 游戏进度不会保存";
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 18));
            
            // 估算游客模式文字宽度和高度
            float guestTextWidth = estimateTextWidth(guestText, 18);
            float guestTextHeight = 18;
            float guestTextX = 350;
            float guestTextY = 100;
            
            // 
            gc.setFill(Color.rgb(0, 0, 0, 0.6));
            float guestPadding = 8f;
            float guestBoxWidth = (guestTextWidth + guestPadding * 2) * 2; 
            float guestBoxX = guestTextX - (guestBoxWidth - guestTextWidth) / 2;
            float guestBoxY = guestTextY - guestTextHeight - guestPadding;
            gc.fillRoundRect(guestBoxX+25, guestBoxY, guestBoxWidth, guestTextHeight + guestPadding * 2, 5, 5);
            
            // 绘制文字
            gc.setFill(Color.ORANGE);
            gc.fillText(guestText, guestTextX, guestTextY);
        }
    }
    
    /**
     * 估算文本宽度
     */
    private float estimateTextWidth(String text, int fontSize)
    {
        return text.length() * fontSize * 0.6f;
    }
    
}