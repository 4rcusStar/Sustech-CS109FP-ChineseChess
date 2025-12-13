package ChineseChess.Scene;

import ChineseChess.ChessBoard.ChessBoardManager;
import ChineseChess.UI.GameTimer;
import ChineseChess.UsersAndSavingSystem.SaveService;
import Engine.GameBuilding.GameWorld;
import Engine.Core.GameObject;

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
        // 退出前自动保存当前用户存档
        GameObject boardObj = find("ChessBoard");
        GameObject sideBarObj = find("SideBarUI");
        
        if (boardObj != null)
        {
            ChessBoardManager board = boardObj.getComponent(ChessBoardManager.class);
            if (board != null)
            {
                // 从 GameTimer 获取时间数据
                long totalMillis = 0;
                long redMillis = 0;
                long blackMillis = 0;
                
                if (sideBarObj != null)
                {
                    GameTimer timer = sideBarObj.getComponent(GameTimer.class);
                    if (timer != null)
                    {
                        long[] times = timer.getTimes();
                        totalMillis = times[0];
                        redMillis = times[1];
                        blackMillis = times[2];
                    }
                }
                
                SaveService.save(board, totalMillis, redMillis, blackMillis);
            }
        }
        super.onExit();
    }
}