package Engine;
//TODO:完善Transform的层级功能和线性变换
public class Transform extends Component
{
    private float x;
    private float y;

    //TODO:未来扩展层级位置关系
    private Transform parent;

    public Transform()
    {
        this(0, 0);
    }

    public Transform(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return x坐标
     */
    public float getX()
    {
        return x;
    }

    /**
     *
     * @return y坐标
     */
    public float getY()
    {
        return y;
    }

    /**
     * 直接设置Transform坐标
     * @param x 目标X
     * @param y 目标Y
     */
    public void setPosition(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * 移动（dx,dy）
     * @param dx dx
     * @param dy dy
     */
    public void translate(float dx, float dy)
    {
        this.x += dx;
        this.y += dy;
    }

    /**
     *
     * @return 该Transform的父Transform
     */
    public Transform getParent()
    {
        return parent;
    }

    /**
     * 设置该Transform的父Transform
     * @param parent 要设置的父Transform
     */
    public void setParent(Transform parent)
    {
        this.parent = parent;
    }
}

