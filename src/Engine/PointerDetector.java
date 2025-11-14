package Engine;

import java.util.HashSet;
import java.util.Set;

public class PointerDetector extends Component
{
    Transform t;
    private boolean isPointed = false;

    //鼠标指中的图层最高的PointerDetector会被标记为isPointed;
    private static PointerDetector currentPointed = null;
    private int layer = 0;
    private float detectHeight = 50;
    private float detectWidth = 50;
    private static Set<PointerDetector> allDetectors = new HashSet<>();

    public PointerDetector(int layer)
    {
        this.layer = layer;
        allDetectors.add(this);
    }
    public PointerDetector(int width, int height)
    {
        allDetectors.add(this);
        detectWidth = width;
        detectHeight = height;
    }

    /**
     *
     * @param width  检测范围高度
     * @param height 宽度
     * @param layer  图层
     */
    public PointerDetector(int width, int height, int layer)
    {
        allDetectors.add(this);
        detectWidth = width;
        detectHeight = height;
        this.layer = layer;
    }

    public boolean isPointed()
    {
        return isPointed;
    }

    public void onAwake()
    {
        t = getGameObject().getComponent(Transform.class);
        System.out.println(getGameObject()+"'s PointerIsAwaken");
    }

    static int readyComponents = 0;
    public void update()
    {
        //检测被鼠标指中的所有物体
        float leftEdge = t.getX();
        float rightEdge = t.getX()+detectWidth;
        float topEdge = t.getY();
        float bottomEdge = t.getY()+detectHeight;
        double mouseX = Input.getMouseX();
        double mouseY = Input.getMouseY();
        boolean isInRange = mouseX >= leftEdge && mouseX <= rightEdge&&mouseY >= topEdge && mouseY <= bottomEdge;
        if(isInRange)
        {
            if(currentPointed == null||currentPointed.layer<layer)
            {
                currentPointed = this;
            }
        }
        readyComponents++;
        //System.out.printf("%s at Layer %d is in range:%s,is pointed:%s,currentPointed:%s\n)",getGameObject().getName(),layer,isInRange,isPointed,currentPointed);
        //当最后一个组件也处理完上逻辑后，执行一次resolvePointed
        if(readyComponents == allDetectors.size())
            resolvePointed();
    }
    static void resolvePointed()
    {
        for (PointerDetector detector : allDetectors)
        {
            detector.isPointed = (detector == currentPointed);
        }
        currentPointed = null;
        readyComponents = 0;
    }

    public void setLayer(int layer)
    {
        this.layer = layer;
    }

    public void setDetectSize(float detectWidth, float detectHeight)
    {
        this.detectWidth = detectWidth;
        this.detectHeight = detectHeight;
    }
}
