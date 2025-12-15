package ChineseChess.Scene;

import ChineseChess.ChessBoard.ChessBoard;
import ChineseChess.ChessBoard.ChessBoardManager;
import ChineseChess.ChessPiece.ChessPiece;
import ChineseChess.ChessPiece.PieceType;
import ChineseChess.ChessPiece.Side;
import ChineseChess.UI.SideBarUI;
import ChineseChess.UI.TurnIndicator;
import ChineseChess.UI.GameTimer;
import ChineseChess.UsersAndSavingSystem.SaveData;
import ChineseChess.UsersAndSavingSystem.SaveService;
import Engine.Core.GameObject;
import Engine.GameBuilding.GameWorldConstructor;
import Engine.Audio.AudioManager;

public class ChessWorldConstructor extends GameWorldConstructor
{
    @Override
    public void construct(GameObject root)
    {
        System.out.println("ChessWorldConstructor construct");
        SideBarUI sideBarUI = new SideBarUI();
        root.addChild(sideBarUI);

        ChessBoard chessBoard = new ChessBoard("ChessBoard");
        root.addChild(chessBoard);

        // 添加回合指示器
        GameObject indicatorObj = new GameObject("TurnIndicator", 0, 0);
        indicatorObj.addComponent(new TurnIndicator());
        root.addChild(indicatorObj);

        // 播放游戏背景音乐
        AudioManager.getInstance().playBGM(AudioManager.BGMType.IN_GAME);

        System.out.println("ChessWorldConstructor: Construction completed for root: " + root.getName());

        // 红方棋子
        ChessPiece redGeneral = new ChessPiece(Side.RED, PieceType.GENERAL, 0);
        chessBoard.addChild(redGeneral);

        ChessPiece redAdvisor_1 = new ChessPiece(Side.RED, PieceType.ADVISOR, 1);
        chessBoard.addChild(redAdvisor_1);
        ChessPiece redAdvisor_2 = new ChessPiece(Side.RED, PieceType.ADVISOR, 2);
        chessBoard.addChild(redAdvisor_2);

        ChessPiece redElephant_1 = new ChessPiece(Side.RED, PieceType.ELEPHANT, 1);
        chessBoard.addChild(redElephant_1);
        ChessPiece redElephant_2 = new ChessPiece(Side.RED, PieceType.ELEPHANT, 2);
        chessBoard.addChild(redElephant_2);

        ChessPiece redHorse_1 = new ChessPiece(Side.RED, PieceType.HORSE, 1);
        chessBoard.addChild(redHorse_1);
        ChessPiece redHorse_2 = new ChessPiece(Side.RED, PieceType.HORSE, 2);
        chessBoard.addChild(redHorse_2);

        ChessPiece redRook_1 = new ChessPiece(Side.RED, PieceType.ROOK, 1);
        chessBoard.addChild(redRook_1);
        ChessPiece redRook_2 = new ChessPiece(Side.RED, PieceType.ROOK, 2);
        chessBoard.addChild(redRook_2);

        ChessPiece redCannon_1 = new ChessPiece(Side.RED, PieceType.CANNON, 1);
        chessBoard.addChild(redCannon_1);
        ChessPiece redCannon_2 = new ChessPiece(Side.RED, PieceType.CANNON, 2);
        chessBoard.addChild(redCannon_2);

        ChessPiece redSoldier_1 = new ChessPiece(Side.RED, PieceType.SOLDIER, 1);
        chessBoard.addChild(redSoldier_1);
        ChessPiece redSoldier_2 = new ChessPiece(Side.RED, PieceType.SOLDIER, 2);
        chessBoard.addChild(redSoldier_2);
        ChessPiece redSoldier_3 = new ChessPiece(Side.RED, PieceType.SOLDIER, 3);
        chessBoard.addChild(redSoldier_3);
        ChessPiece redSoldier_4 = new ChessPiece(Side.RED, PieceType.SOLDIER, 4);
        chessBoard.addChild(redSoldier_4);
        ChessPiece redSoldier_5 = new ChessPiece(Side.RED, PieceType.SOLDIER, 5);
        chessBoard.addChild(redSoldier_5);

        // 黑方棋子
        ChessPiece blackGeneral = new ChessPiece(Side.BLACK, PieceType.GENERAL, 0);
        chessBoard.addChild(blackGeneral);

        ChessPiece blackAdvisor_1 = new ChessPiece(Side.BLACK, PieceType.ADVISOR, 1);
        chessBoard.addChild(blackAdvisor_1);
        ChessPiece blackAdvisor_2 = new ChessPiece(Side.BLACK, PieceType.ADVISOR, 2);
        chessBoard.addChild(blackAdvisor_2);

        ChessPiece blackElephant_1 = new ChessPiece(Side.BLACK, PieceType.ELEPHANT, 1);
        chessBoard.addChild(blackElephant_1);
        ChessPiece blackElephant_2 = new ChessPiece(Side.BLACK, PieceType.ELEPHANT, 2);
        chessBoard.addChild(blackElephant_2);

        ChessPiece blackHorse_1 = new ChessPiece(Side.BLACK, PieceType.HORSE, 1);
        chessBoard.addChild(blackHorse_1);
        ChessPiece blackHorse_2 = new ChessPiece(Side.BLACK, PieceType.HORSE, 2);
        chessBoard.addChild(blackHorse_2);

        ChessPiece blackRook_1 = new ChessPiece(Side.BLACK, PieceType.ROOK, 1);
        chessBoard.addChild(blackRook_1);
        ChessPiece blackRook_2 = new ChessPiece(Side.BLACK, PieceType.ROOK, 2);
        chessBoard.addChild(blackRook_2);

        ChessPiece blackCannon_1 = new ChessPiece(Side.BLACK, PieceType.CANNON, 1);
        chessBoard.addChild(blackCannon_1);
        ChessPiece blackCannon_2 = new ChessPiece(Side.BLACK, PieceType.CANNON, 2);
        chessBoard.addChild(blackCannon_2);

        ChessPiece blackSoldier_1 = new ChessPiece(Side.BLACK, PieceType.SOLDIER, 1);
        chessBoard.addChild(blackSoldier_1);
        ChessPiece blackSoldier_2 = new ChessPiece(Side.BLACK, PieceType.SOLDIER, 2);
        chessBoard.addChild(blackSoldier_2);
        ChessPiece blackSoldier_3 = new ChessPiece(Side.BLACK, PieceType.SOLDIER, 3);
        chessBoard.addChild(blackSoldier_3);
        ChessPiece blackSoldier_4 = new ChessPiece(Side.BLACK, PieceType.SOLDIER, 4);
        chessBoard.addChild(blackSoldier_4);
        ChessPiece blackSoldier_5 = new ChessPiece(Side.BLACK, PieceType.SOLDIER, 5);
        chessBoard.addChild(blackSoldier_5);
        //

        // 尝试加载存档（如果游戏已结束则不加载，避免重新开始时恢复游戏结束状态）
        SaveData data = SaveService.load();
        if (data != null && !data.isGameOver)
        {
            SaveService.setCachedSave(data);
            ChessBoardManager board = chessBoard.getComponent(ChessBoardManager.class);
            if (board != null)
            {
                // 这里仅恢复全局状态
                board.applySave(data);
            }
            
            // 恢复计时器时间
            GameObject sideBarObj = root.getChild("SideBarUI");
            if (sideBarObj != null)
            {
                GameTimer timer = sideBarObj.getComponent(GameTimer.class);
                if (timer != null)
                {
                    timer.setTimes(data.totalMillis, data.redMillis, data.blackMillis);
                }
            }
        }
        else if (data != null && data.isGameOver)
        {
            // 如果存档是游戏结束状态，清除缓存，不加载
            SaveService.setCachedSave(null);
        }
    }

}
