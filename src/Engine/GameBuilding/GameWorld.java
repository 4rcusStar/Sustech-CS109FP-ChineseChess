package Engine.GameBuilding;

import Engine.Core.GameObject;

import java.util.List;

public class GameWorld
{
    //场景入口节点
    private GameObject root;
    //场景搭建者：
    private GameWorldConstructor gameWorldConstructor;

    public GameWorld(GameWorldConstructor gameWorldConstructor)
    {
        this.gameWorldConstructor = gameWorldConstructor;
        root = new GameObject("SceneRoot");
    }

    /**
     * 场景进入时调用
     */
    public void onEnter()
    {
        //如果此时场景还没有搭建，则搭建场景
        if(root.getChildren().isEmpty()&&gameWorldConstructor!=null)
            gameWorldConstructor.construct(root);
    }

    public void onExit()
    {
        
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
