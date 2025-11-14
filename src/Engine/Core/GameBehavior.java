package Engine.Core;

public interface GameBehavior
{
    /**
     * 比start()更先调用，用于框架初始化
     */
    default void awake(){};
    /**
     * 游戏开始时调用，用于逻辑初始化
     */
    default void start(){};

    /**
     * 逻辑更新
     */
    default void update(){};

    /*
     * 被摧毁时调用
     * */

    default void onDestroy()
    {
    }


    /**
     * 获取实例名称
     * @return 实例名称
     */
    default String getName()
    {
        return this.getClass().getSimpleName();
    }
}
