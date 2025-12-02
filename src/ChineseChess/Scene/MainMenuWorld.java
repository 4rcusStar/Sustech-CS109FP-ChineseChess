package ChineseChess.Scene;

import Engine.GameBuilding.GameWorld;
import Engine.Components.RendererComponent;
import Engine.GameBuilding.GameWorldManager;
import Engine.Input;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


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
                
            gc.setFill(Color.LIGHTGRAY);       
            gc.fillRect(0, 0, 1000, 800);

             // 绘制标题
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));
            gc.setFill(Color.BLACK);
            gc.fillText("中国象棋", 400, 200);
        
            // 绘制提示文字
            gc.setFont(Font.font("Arial", FontWeight.NORMAL, 24));
            gc.fillText("点击开始游戏", 420, 400);
    }
    
}