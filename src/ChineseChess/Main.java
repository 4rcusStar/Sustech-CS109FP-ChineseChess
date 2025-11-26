package ChineseChess;

import Engine.GameBuilding.GameStarter;


public class Main
{
    public static void main(String[] args)
    {
        ChessWorldConstructor constructor = new ChessWorldConstructor();
        GameStarter.setStartSetting(1000,800,"ChineseChess",constructor);
        GameStarter.launchGame(args);
    }
}
