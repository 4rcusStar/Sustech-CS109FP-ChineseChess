package ChineseChess;

import Engine.*;

public class ChessBoard extends GameObject
{
    public ChessBoard(String name)
    {
        super(name);
        addComponent(new ChessBoardManager());
        addComponent(new SpriteRenderer());
        addComponent(new PointerDetector(800,800,0));
    }
}
