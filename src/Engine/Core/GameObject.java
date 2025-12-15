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
    private List<Component> components = new ArrayList<Component>();
    private String name;

    private GameObject parent;
    private List<GameObject> children = new ArrayList<>();

    private GameObject pendingParent;
    private List<GameObject> pendingChildren = new ArrayList<>();

    private boolean isAwaken=false;
    private boolean isStarted=false;
    private boolean isEnabled=true;
    private boolean pendingDestroy=false;

    public GameObject(String name)
    {
        this.name = name;
        Transform transform= new Transform();
        this.addComponent(transform);
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

    public void applyPendingRelation()
    {
        for(GameObject child:pendingChildren)
        {
            if(child==null||children.contains(child))
                return;
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

    public GameObject getChild(String name)
    {
        for(GameObject child:children)
        {
            if(child.getName().equals(name))
                return child;
        }
        return null;
    }


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

    public boolean isEnabled(){return isEnabled;}

    public List<Component> getAllComponents()
    {
        return components;
    }
    public void awake()
    {
        for(Component component : components)
        {
            component.awake();
            component.markAwaken();
        }
        for(GameObject child : new ArrayList<>(children))
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
        for(GameObject child : new ArrayList<>(children))
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
        for(GameObject child : new ArrayList<>(children))
        {
            child.update();
        }
    }

    public void render(GraphicsContext gc)
    {
        for (Component component : components)
        {
            if(component.isEnabled()&&component instanceof RendererComponent)
            {
                ((RendererComponent) component).render(gc);
            }
        }
        for(GameObject child : new ArrayList<>(children))
        {
            child.render(gc);
        }
    }

    public List<RendererComponent> collectAllRenderers()
    {
        List<RendererComponent> allRenderers = new ArrayList<>();
        List<Component> componentsCopy = new ArrayList<>(components);
        for (Component component : componentsCopy)
        {
            if (component.isEnabled() &&component.isStarted() && component instanceof RendererComponent)
            {
                allRenderers.add((RendererComponent) component);
            }
        }
        List<GameObject> childrenCopy = new ArrayList<>(children);
        for (GameObject child : childrenCopy)
        {
            allRenderers.addAll(child.collectAllRenderers());
        }

        return allRenderers;
    }

}
