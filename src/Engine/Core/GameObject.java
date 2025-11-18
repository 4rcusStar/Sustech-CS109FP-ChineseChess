package Engine.Core;

import Engine.Components.Component;
import Engine.Components.RendererComponent;
import Engine.Components.Transform;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameObject
{
    private List<Component> components = new ArrayList<Component>();//该Object包含的所有的组件
    private String name;//该Object的名字

    private GameObject parent;//该GameObject的父GameObject
    private List<GameObject> children = new ArrayList<>();//该GameObject的子GameObject

    private GameObject pendingParent;//待加入/变更的父OBJ
    private List<GameObject> pendingChildren = new ArrayList<>();//待加入的子OBJ

    private boolean isAwaken=false;//是否已经调用过awake()
    private boolean isStarted=false;//是否已经调用过start()
    private boolean isEnabled=true;
    private boolean pendingDestroy=false;

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
        pendingChildren.add(child);
    }

    public void setParent(GameObject parent)
    {
        pendingParent = parent;
    }

    public void destroy()
    {
        pendingDestroy=true;
    }

    /**
     * 在每帧的开始应用父子设置关系
     */
     public void applyPendingRelation()
    {
        for(GameObject child:pendingChildren)
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
        pendingChildren.clear();
        if(pendingParent!=null)
        {
            if(this.parent==null)
                this.parent=pendingParent;
            if(this.parent==pendingParent)
                return;
            if (this.parent.children.contains(this))
                return;
            this.parent.children.remove(this);
            parent.children.add(this);
            this.parent = pendingParent;
        }
        pendingParent=null;
        if(pendingDestroy)
        {
            parent.children.remove(this);
            parent=null;
            return;
        }
        for(GameObject child:new LinkedList<>(children))
        {
            child.applyPendingRelation();
        }
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
    }
    public void start()
    {
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

    /**
     * 渲染引擎调用渲染
     * @param gc
     */
    public void render(GraphicsContext gc)
    {
        for (Component component : components)
        {
            if(component.isEnabled()&&component instanceof RendererComponent)
            {
                ((RendererComponent) component).render(gc);
            }
        }
        for(GameObject child : children)
        {
            child.render(gc);
        }
    }

    // 新增方法：收集所有RendererComponent（包括子对象）
    public List<RendererComponent> collectAllRenderers()
    {
        List<RendererComponent> allRenderers = new ArrayList<>();

        // 先收集当前对象的渲染组件
        for (Component component : components)
        {
            if (component.isEnabled() && component instanceof RendererComponent)
            {
                allRenderers.add((RendererComponent) component);
            }
        }
        // 递归收集子对象的渲染组件
        for (GameObject child : children)
        {
            allRenderers.addAll(child.collectAllRenderers());
        }

        return allRenderers;
    }

}
