package ChineseChess.ChessBoard;

import Engine.Components.PointerDetector;
import Engine.Components.SpriteRenderer;
import Engine.Core.GameObject;

public class ChessBoard extends GameObject
{
    public ChessBoard(String name)
    {
        super(name);
        addComponent(new ChessBoardManager());
        addComponent(new SpriteRenderer());
        addComponent(new PointerDetector(800,800,0));
        addComponent(new Selector());
    }
}
