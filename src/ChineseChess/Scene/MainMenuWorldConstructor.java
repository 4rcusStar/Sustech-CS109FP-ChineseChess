package ChineseChess.Scene;

import Engine.Core.GameObject;
import Engine.GameBuilding.GameWorldConstructor;

public class MainMenuWorldConstructor extends GameWorldConstructor
{
    @Override
    public void construct(GameObject root)
    {
        root.addComponent(new MainMenuRenderer());
    }
}
