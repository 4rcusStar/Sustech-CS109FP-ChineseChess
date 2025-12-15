package Engine.Components;

import Engine.Core.GameEngine;

//TODO:完善Transform的层级功能和线性变换
public class Transform extends Component
{
    GameEngine gameEngine = GameEngine.getInstance();
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

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    /**
     * 直接设置Transform坐标
     *
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
     *
     * @param dx dx
     * @param dy dy
     */
    public void translate(float dx, float dy)
    {
        this.x += dx;
        this.y += dy;
    }

    float startX, startY;
    float targetX;
    float targetY;
    float movingVelocity;
    private boolean isMoving = false;
    private float totalMovingDistance;
    private double duration;
    private double elapsedTime = 0;
    private float xDistance;
    private float yDistance;

    /**
     * 以匀速从原位置运动到指定位置
     *
     * @param x        目标x
     * @param y        目标y
     * @param velocity 速度：像素/秒
     */
    public void moveTo(float x, float y, float velocity)
    {
        startX = this.x;
        startY = this.y;
        this.targetX = x;
        this.targetY = y;
        movingVelocity = velocity;
        xDistance = x-this.x;
        yDistance = y-this.y;
        totalMovingDistance = (float) Math.sqrt((this.x - x) * (this.x - x) + (this.y - y) * (this.y - y));
        duration = totalMovingDistance / movingVelocity;
        isMoving = true;
    }

    public boolean isMoving()
    {
        return isMoving;
    }

    public Transform getParent()
    {
        return parent;
    }

    /**
     * 设置该Transform的父Transform
     *
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
            elapsedTime=0;
            return;
        }
        long deltaMs = gameEngine.getDeltaTime();

        double deltaSeconds = deltaMs / 1000.0;
        elapsedTime += deltaSeconds;
        float scaleWithTime = Math.min((float) (elapsedTime / duration), 1);

        if (scaleWithTime > 0.999)
        {
            x = targetX;
            y = targetY;
            isMoving = false;
            return;
        }
        float easeOutScale = 1-(1-scaleWithTime)*(1-scaleWithTime);
        x = startX + easeOutScale * xDistance;
        y = startY + easeOutScale * yDistance;
    }
}

