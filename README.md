# Sustech-CS109FP-ChineseChess
# 为了本游戏开发，我们制作了一个可复用的基于JavaFx渲染的轻量级引擎！
以下是游戏引擎的快速入门：
下面是为你的 **Java 轻量级游戏引擎** 编写的一份 **README 入门教程**。
结构清晰、流程明确、示例简洁，并与 **你的代码风格完全一致**（GameObject + Component，生命周期 awake/start/update，Allman 写法）。

---

#  Java Mini Game Engine - 入门教程

欢迎使用这套简单、直观、贴近 Unity 组件系统的 Java 游戏引擎。
本教程的目的是让你 **10 分钟入门引擎开发流程**，知道如何：

* 创建游戏世界
* 编写 GameObject
* 添加组件（Component）
* 加载图片与渲染
* 检测鼠标事件
* 实现移动、点击、交互
* 完成一整个游戏生命周期

---

# 📌 目录

1. 引擎的基本思想
2. 游戏运行流程
3. **创建你的第一个 GameObject**
4. **编写一个组件（Component）**
5. **鼠标检测：PointerDetector**
6. **渲染图片：SpriteRenderer**
7. **移动对象：Transform**
8. **构建游戏世界（GameWorldConstructor）**
9. **启动游戏（Main）**
10. 常见问题 FAQ

---

# 一、引擎的基本思想

本引擎采用类似 **Unity 的组件化架构**：

```
GameObject ——> 由多个 Component 组成
Component ——> 决定对象的行为和功能
生命周期 ——> awake → start → update → onDestroy
```

所有游戏逻辑通过组件实现，而不是继承 GameObject。

---

# 二、游戏运行流程（生命周期）

```
构造函数（new）  
↓  
awake()       —— 框架初始化（获取组件）
↓  
start()       —— 游戏开始（设置初始状态）
↓  
update()      —— 每帧执行
↓  
onDestroy()   —— 对象销毁
```

---

# 三、创建你的第一个 GameObject

下面是一个最基本的游戏对象示例：

```java
public class MyObject extends GameObject
{
    public MyObject(String name)
    {
        super(name);

        addComponent(new SpriteRenderer());
        addComponent(new PointerDetector(100, 100, 0));
        addComponent(new MyObjectManager());
    }
}
```

* `SpriteRenderer`：绘制图片
* `PointerDetector`：鼠标检测（是否指向、点击）
* `MyObjectManager`：你自己的逻辑组件

---

# 四、编写一个组件（Component）

写一个组件，你要继承 Component 类，并重写生命周期方法：

```java
public class MyObjectManager extends Component
{
    Transform transform;
    SpriteRenderer renderer;
    PointerDetector pointer;

    @Override
    public void onAwake()
    {
        transform = getGameObject().getComponent(Transform.class);
        renderer = getGameObject().getComponent(SpriteRenderer.class);
        pointer = getGameObject().getComponent(PointerDetector.class);
    }

    @Override
    public void onStart()
    {
        renderer.setSprite(new Image("file:/my.png"));
    }

    @Override
    public void update()
    {
        if (pointer.isPointed() && Input.isMouseClicked())
        {
            System.out.println("Clicked!");
        }
    }
}
```

---

# 五、鼠标检测：PointerDetector

PointerDetector 自动检测鼠标是否指向此对象：

```java
new PointerDetector(width, height, layer);
```

它提供的常用方法：

| 方法          | 说明                    |
| ----------- | --------------------- |
| isPointed() | 鼠标是否在对象范围内            |
| isClicked() | 鼠标是否点击（一般配合 Input 使用） |

示例：

```java
if (pointerDetector.isPointed())
{
    if (Input.isMouseClicked())
    {
        System.out.println("你点击了我！");
    }
}
```

---

# 六、渲染图片：SpriteRenderer

设置贴图和大小：

```java
renderer.setSprite(new Image("file:/img.png"));
renderer.setSize(100, 100);
```

在 `start()` 阶段设置图像最安全：

```java
@Override
public void onStart()
{
    renderer.setSprite(new Image("file:/player.png"));
    renderer.setSize(100, 100);
}
```

---

# 七、Transform：移动对象

Transform 提供位置数据：

```java
transform.setPosition(x, y);
transform.moveTo(x, y, velocity);
```

示例：点击移动到一个点

```java
if (pointer.isPointed() && Input.isMouseClicked())
{
    transform.moveTo(400, 300, 800);
}
```

---

# 八、构建游戏世界（GameWorldConstructor）

游戏世界通过一个类配置：

```java
public class MyWorld extends GameWorldConstructor
{
    @Override
    public void construct(GameObject root)
    {
        MyObject obj = new MyObject("Box");
        root.addChild(obj);
    }
}
```

其中 `root` 就像 Unity 场景的根节点。

---

# 九、启动游戏（Main）

```java
public class Main
{
    public static void main(String[] args)
    {
        MyWorld world = new MyWorld();
        GameStarter.setStartSetting(800, 800, "MyGame", world);
        GameStarter.launchGame(args);
    }
}
```

GameStarter 会：

* 创建窗口
* 调用你传入的 GameWorldConstructor
* 启动渲染循环

---

# 十、FAQ

### 为什么要把逻辑写在 Component 里？

因为这让你的游戏对象可以“随意组合功能”，只需要 addComponent。

### awake 和 start 有什么区别？

* **awake()**：获取组件引用
* **start()**：可以安全使用其它对象和资源

### update 可以做什么？

* 输入检测
* 移动
* 播放动画
* 逻辑运算

---

# 🎉 完整示例：点击移动的小方块

```java
public class Box extends GameObject
{
    public Box()
    {
        super("Box");
        addComponent(new SpriteRenderer());
        addComponent(new PointerDetector(80, 80, 0));
        addComponent(new BoxManager());
    }
}

public class BoxManager extends Component
{
    Transform transform;
    SpriteRenderer renderer;
    PointerDetector pointer;

    @Override
    public void onAwake()
    {
        transform = getGameObject().getComponent(Transform.class);
        renderer = getGameObject().getComponent(SpriteRenderer.class);
        pointer = getGameObject().getComponent(PointerDetector.class);
    }

    @Override
    public void onStart()
    {
        renderer.setSprite(new Image("file:/box.png"));
        renderer.setSize(80, 80);
    }

    @Override
    public void update()
    {
        if (pointer.isPointed() && Input.isMouseClicked())
        {
            transform.moveTo(400, 300, 500);
        }
    }
}
```

---

# 📌 最后说明

这套引擎的设计风格关键点如下：

* **GameObject 只负责组合组件**
* **组件（Component）负责所有逻辑**
* **统一的生命周期（awake/start/update）**
* **轻量、高度直观、结构清晰**



