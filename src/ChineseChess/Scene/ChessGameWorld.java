package ChineseChess.Scene;

import Engine.GameBuilding.GameWorld;

/**
 * 游戏本体场景
 */
public class ChessGameWorld extends GameWorld
{
    public ChessGameWorld()
    {
        super(new ChessWorldConstructor());
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