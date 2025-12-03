package ChineseChess;

import ChineseChess.Scene.MainMenuWorld;
import ChineseChess.Scene.UserSelectWorld;
import ChineseChess.Scene.ChessGameWorld;
import ChineseChess.UsersAndSavingSystem.UserManager;
import Engine.GameBuilding.GameStarter;
import Engine.GameBuilding.GameWorldManager;
import Engine.GameBuilding.GameWorldConstructor;
import Engine.Core.GameObject;


public class Main
{
    public static void main(String[] args)
    {
        //创建用户管理
        UserManager userManager  = UserManager.getInstance();
        userManager.initialize(); 
        //创建场景管理器：
        GameWorldManager worldManager = GameWorldManager.getInstance();
        //注册场景
        worldManager.registerGameWorld("UserSelect", () -> new UserSelectWorld());
        worldManager.registerGameWorld("MainMenu", () -> new MainMenuWorld());
        worldManager.registerGameWorld("Game", () -> new ChessGameWorld());
        
        //初始化引擎
        GameWorldConstructor initialConstructor = new GameWorldConstructor() 
        {
            @Override
            public void construct(GameObject root)
            {
            }
        };
        GameStarter.setStartSetting(1000,800,"ChineseChess",initialConstructor);
        
        //设置引擎就绪回调，在引擎初始化完成后切换到初始场景
        GameStarter.setOnEngineReady(() -> {
            worldManager.switchGameWorldTo("UserSelect");
        });
        
        GameStarter.launchGame(args);
    }
}
