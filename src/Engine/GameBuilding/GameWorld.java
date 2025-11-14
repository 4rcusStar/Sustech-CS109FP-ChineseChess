package Engine.GameBuilding;

import Engine.Core.GameObject;

import java.util.List;

public class GameWorld
{
    //场景入口节点
    private GameObject root;

    public GameWorld(GameWorldConstructor gameWorldConstructor)
    {
        root = new GameObject("SceneRoot");
        gameWorldConstructor.construct(root);
    }

    public GameObject getRoot()
    {
        return root;
    }

    /**
     * 从场景中根据GameObject名字查找gameObject，若未查找到目标，则返回null
     * @param name 目标GameObject名字
     * @return 目标GameObject,若未找到则null
     */
    public GameObject find(String name)
    {
        if(root == null)
            return null;
        return findRecurrence(name,root.getChildren());
    }

    private GameObject findRecurrence(String name, List<GameObject> children)
    {
        for(GameObject child : children)
        {
            if(child.getName().equals(name))
                return child;
            GameObject result = findRecurrence(name,child.getChildren());
            if(result != null)
                return result;
        }
        return null;
    }
}
