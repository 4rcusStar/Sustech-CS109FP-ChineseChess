package ChineseChess.UsersAndSavingSystem;

import ChineseChess.ChessPiece.Side;
import ChineseChess.ChessPiece.PieceType;
import java.util.ArrayList;
import java.util.List;

public class SaveData
{
    public int version = 1;
    public String userId;
    public String savedAt; // ISO-8601 string

    public Side currentSide;
    public int turnNumber;
    public boolean isGameOver;
    public Side winnerSide;
    public String endReason;

    public long totalMillis;
    public long redMillis;
    public long blackMillis;

    public List<PieceState> pieces = new ArrayList<>();

    public static class PieceState
    {
        public String name;
        public PieceType type;
        public Side side;
        public int coordX;
        public int coordY;
        public boolean alive;
    }
}











