package ChineseChess;

import Engine.GameObject;
import Engine.SpriteRenderer;
import Engine.Transform;

public class ChessPiece extends GameObject
{
    private String type;
    private String side;
    Transform transform;
    public ChessPiece(String type, String side,int x,int y)
    {
        super(side+"_"+type);
        //设置位置
        transform = getComponent(Transform.class);
        transform.setPosition(x,y);
        this.type = type;
        this.side = side;
        //添加移动逻辑组件
        addComponent(new PieceMovementManager());
        addComponent(new SpriteRenderer());
    }

}
