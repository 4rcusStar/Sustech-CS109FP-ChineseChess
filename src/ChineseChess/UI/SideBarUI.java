package ChineseChess.UI;

import Engine.Core.GameObject;

public class SideBarUI extends GameObject
{
    public SideBarUI()
    {
        super("SideBarUI",800,0);
        addComponent(new CurrentTurn());
        addComponent(new WinningStatus());
        addComponent(new RestartButton());
    }
}
