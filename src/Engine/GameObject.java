package Engine;

import java.util.ArrayList;
import java.util.List;

public class GameObject
{
    private List<Component> components = new ArrayList<Component>();//该Object包含的所有的组件
    private String name;//该Object的名字

    private GameObject parent;//该GameObject的父GameObject
    private List<GameObject> children = new ArrayList<>();//该GameObject的子GameObject

    private boolean isAwaken=false;//是否已经调用过awake()
    private boolean isStarted=false;//是否已经调用过start()
    private boolean isEnabled=true;

    public GameObject(String name)
    {
        this.name = name;
        Transform transform= new Transform();
        this.addComponent(transform);
        //
    }

    /**
     * 在x,y处创建GameObject
     * @param name GameObject's name
     * @param x x
     * @param y y
     */
    public GameObject(String name, int x,int y)
    {
        this.name = name;
        Transform transform= new Transform(x,y);
        this.addComponent(transform);
    }

    /**
     * 获取GameObject的name
     * @return GameObject.name
     */
    public String getName()
    {
        return name;
    }

    public GameObject getParent()
    {
        return parent;
    }

    public List<GameObject> getChildren()
    {
        return children;
    }

    public void addChild(GameObject child)
    {
        //如果child为空或者children中已经有child则不执行代码
        if(child==null||children.contains(child))
            return;
        //如果child已经有了一个parent，则移除原parent，添加本对象为parent
        if(child.parent!=null)
        {
            child.parent.children.remove(child);
        }
        children.add(child);
        child.parent=this;
    }

    /**
     * 通过name获取子一层GameObject
     * @param name 目标的name
     * @return 目标GameObject,若找不到则返回null
     */
    public GameObject getChild(String name)
    {
        for(GameObject child:children)
        {
            if(child.getName().equals(name))
                return child;
        }
        return null;
    }
    public void setParent(GameObject parent)
    {
        if(parent==null||this.parent==parent)
            return;
        parent.addChild(this);
    }


    /**
     * 向GameObject添加组件
     * @param component 要添加的组件
     */
    public void addComponent(Component component)
    {
        component.setGameObject(this);
        components.add(component);
    }

    /**
     * 从GameObject上获取指定组件
     * @param type 组件的class
     * @return 获取的组件，若出错则返回null
     * @param <T> 类名
     */
    public <T extends Component> T getComponent(Class<T> type)
    {
        for(Component component : components)
        {
            if(type.isInstance(component))
            {
                try
                {
                    return (T)component;
                }
                catch (ClassCastException e)
                {
                    System.err.println("The type trying to get" + component.getName() + " is not a " + type.getName());
                }
            }
        }
        System.err.println("cannot find component " + type.getName());
        return null;
    }

    /**
     * 为Object设置enabled状态
     * @param enabled 是否激活
     */
    public void setEnabled(boolean enabled)
    {
        isEnabled = enabled;
    }

    /**
     * 该GameObject是否enabled
     * @return isEnabled
     */
    public boolean isEnabled(){return isEnabled;}

    /**
     * 获得所有组件的列表
     * @return 所有组件的列表
     */
    public List<Component> getAllComponents()
    {
        return components;
    }
    //----------GameObject游戏循环管理------------
    public void awake()
    {
        if(!isEnabled||isAwaken)
            return;
        for(Component component : components)
        {
            component.awake();
            component.markAwaken();
        }
        //让子Object调用awake()
        for(GameObject child : children)
        {
            child.awake();
        }
        isAwaken=true;
    }
    public void start()
    {
        if(!isEnabled||isStarted)
            return;
        for(Component component : components)
        {
            component.start();
            component.markStarted();
        }
        //子O调用
        for(GameObject child : children)
        {
            child.start();
        }
        isStarted=true;
    }

    public void update()
    {
        for (Component component : components)
        {
            if(component.isEnabled()&&component.isStarted())
            {
                component.update();
            }
        }
        for(GameObject child : children)
        {
            child.update();
        }
    }
}
