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
        if(root != null && gameWorldConstructor != null)
        {
            // 如果场景还没有搭建，则搭建场景
            if(root.getChildren().isEmpty())
            {
                System.out.println("GameWorld.onEnter: Constructing scene (children empty)...");
                gameWorldConstructor.construct(root);
                root.applyPendingRelation();
                System.out.println("GameWorld.onEnter: Scene constructed. Children count: " + root.getChildren().size());
            }
            else
            {
                System.out.println("GameWorld.onEnter: Children not empty, applying pending relations. Children count: " + root.getChildren().size());
                root.applyPendingRelation();
            }
        }
        else
        {
            System.out.println("GameWorld.onEnter: root or gameWorldConstructor is null!");
        }
    }

    public void onExit()
    {
        // 把子对象都清掉，这样下次进来的时候就能重新构造了
        if(root != null)
        {
            root.applyPendingRelation();
            // 注意：root 本身不动，只清它的子对象
            root.getChildren().clear();
        }
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
