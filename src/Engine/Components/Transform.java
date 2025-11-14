package Engine.Components;

import Engine.Core.GameEngine;

//TODO:完善Transform的层级功能和线性变换
public class Transform extends Component
{
    GameEngine gameEngine =GameEngine.getInstance();
    private float x;
    private float y;
    private boolean isMoving=false;

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


    float targetX;
    float targetY;
    float movingVelocity;
    /**
     * 以匀速从原位置运动到指定位置
     * @param x 目标x
     * @param y 目标y
     * @param velocity 速度：像素/秒
     */
    public void moveTo(float x,float y,float velocity)
    {
        this.targetX = x;
        this.targetY = y;
        movingVelocity = velocity;
        isMoving=true;
    }

    /**
     *
     * @return 该物体是否正在执行moveTo()
     */
    public boolean isMoving()
    {
        return isMoving;
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

    public void update()
    {
        if (!isMoving)
        {
            return;
        }
        long deltaMs = gameEngine.getDeltaTime();

        // 将毫秒转换为秒
        double deltaSeconds = deltaMs / 1000.0;

        float dx = targetX - x;
        float dy = targetY - y;
        double distanceRemain = Math.sqrt(dx * dx + dy * dy);
        if (distanceRemain <= 0.0001)
        {
            x = targetX;
            y = targetY;
            isMoving = false;
            return;
        }
        // 计算本帧应移动的距离（像素）
        double moveDist = movingVelocity * deltaSeconds;

        // 如果帧移动距离大于剩余距离，直接到达终点
        if (moveDist >= distanceRemain)
        {
            x = targetX;
            y = targetY;
            isMoving = false;
            return;
        }

        // 规范化方向向量并移动
        double nx = dx / distanceRemain;
        double ny = dy / distanceRemain;

        x += (float) (nx * moveDist);
        y += (float) (ny * moveDist);
    }
}

