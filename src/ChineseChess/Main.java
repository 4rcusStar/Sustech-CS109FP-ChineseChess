package ChineseChess;

import Engine.*;


public class Main
{
    public static void main(String[] args)
    {
        ChessWorldConstructor constructor = new ChessWorldConstructor();
        GameStarter.setStartSetting(800,800,"ChineseChess",constructor);
        GameStarter.launchGame(args);
    }
}
