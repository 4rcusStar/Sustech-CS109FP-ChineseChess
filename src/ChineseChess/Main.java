package ChineseChess;

import ChineseChess.Scene.MainMenuWorldConstructor;
import ChineseChess.Scene.ChessWorldConstructor;
import Engine.GameBuilding.GameStarter;
import Engine.GameBuilding.GameWorldManager;
import Engine.GameBuilding.GameWorldConstructor;
import Engine.Core.GameObject;


public class Main
{
    public static void main(String[] args)
    {
        //创建场景管理器：
        GameWorldManager worldManager = GameWorldManager.getInstance();
        //注册场景：
        worldManager.registerGameWorldConstructor("MainMenu", new MainMenuWorldConstructor());
        worldManager.registerGameWorldConstructor("Game", new ChessWorldConstructor());

        //初始化引擎
        GameWorldConstructor initialConstructor = new GameWorldConstructor() 
        {
            @Override
            public void construct(GameObject root)
            {
            }
        };
        GameStarter.setStartSetting(1000,800,"ChineseChess",initialConstructor);
        GameStarter.launchGame(args);
    }
}
