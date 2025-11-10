package Engine;

import java.util.ArrayList;
import java.util.List;

public class GameObject
{
    //该Object包含的所有的组件
    private List<Component> components = new ArrayList<Component>();
    private String name;

    private boolean isAwaken=false;//是否已经调用过awake()
    private boolean isStarted=false;//是否已经调用过start()
    private boolean isEnabled=true;

    public GameObject(String name)
    {
        this.name = name;
    }

    /**
     * 获取GameObject的name
     * @return
     */
    public String getName()
    {
        return name;
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
    }
}
