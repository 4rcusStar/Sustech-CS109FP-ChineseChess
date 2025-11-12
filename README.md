# Sustech-CS109FP-ChineseChess
A Simple ChineseChess Project
11.11：经过数天的开发，现在已经具备了可以完整使用的游戏引擎框架！游戏开发将因此变得模块化！
# 引擎说明：（以下说明由AI生成，我实在没有精力写说明了）
# 🎮 游戏框架使用说明

一个基于组件模式的轻量级Java游戏框架，专为2D游戏开发设计。

## 🚀 快速开始

### 环境要求
- Java 8+
- 任何Java IDE

### 第一步：创建移动组件

```java
public class MoveComponent extends Component {
    private float speed = 2.0f;
    
    @Override
    public void start() {
        System.out.println("移动组件初始化完成！");
    }
    
    @Override
    public void update() {
        // 每帧向右移动
        Transform transform = getGameObject().getComponent(Transform.class);
        transform.translate(speed * 0.016f, 0);
    }
}
```

### 第二步：创建旋转组件

```java
public class RotateComponent extends Component {
    private float rotationSpeed = 45.0f; // 度/秒
    
    @Override
    public void update() {
        // 这里可以添加旋转逻辑
        System.out.println("旋转更新中...");
    }
}
```

### 第三步：创建玩家对象

```java
public class Player extends GameObject {
    public Player() {
        super("Player");
        
        // 设置初始位置（每个GameObject自动包含Transform组件）
        Transform transform = getComponent(Transform.class);
        transform.setPosition(10, 20);
        
        // 添加组件
        addComponent(new MoveComponent());
        addComponent(new RotateComponent());
    }
}
```

### 第四步：启动游戏

```java
import Engine.GameObject;

public class Main
{
    public static void main(String[] args)
    {
        // 获取引擎实例
        GameEngine engine = GameEngine.getInstance();

        // 创建场景
        Scene gameScene = new Scene();

        // 创建玩家对象并添加到场景
        Player player = new Player();
        gameScene.getRoot().addChild(player);

        // 注册到引擎并启动
        engine.registerGameObject(gameScene.getRoot());
        engine.runEngine();

        // 运行10秒后停止（示例）
        try
        {
            Thread.sleep(10000);
            engine.stopEngine();
            System.out.println("游戏结束");
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
```

## 🏗️ 核心概念

### 四大核心类

| 类名 | 职责 | 重要方法 |
|------|------|----------|
| `GameEngine` | 游戏引擎总管 | `getInstance()`, `runEngine()`, `stopEngine()` |
| `Scene` | 场景管理 | `find()`, `getRoot()` |
| `GameObject` | 游戏对象 | `addComponent()`, `getComponent()`, `addChild()` |
| `Component` | 功能组件 | `awake()`, `start()`, `update()` |

## ⚙️ 组件开发

### 组件生命周期

```java
public class MyComponent extends Component {
    @Override
    public void awake() {
        // 框架初始化时调用一次
        System.out.println("Awake: 组件被创建");
    }
    
    @Override
    public void start() {
        // 游戏开始时调用一次
        System.out.println("Start: 游戏开始");
    }
    
    @Override
    public void update() {
        // 每帧调用（约60次/秒）
        System.out.println("Update: 游戏更新");
    }
}
```

### 组件间通信

```java
public class HealthComponent extends Component {
    private int health = 100;
    
    public void takeDamage(int damage) {
        health -= damage;
        System.out.println("受到伤害，当前生命值: " + health);
    }
}

public class CombatComponent extends Component {
    @Override
    public void update() {
        // 获取同一对象上的其他组件
        HealthComponent health = getGameObject().getComponent(HealthComponent.class);
        Transform transform = getGameObject().getComponent(Transform.class);
        
        if (health != null) {
            // 调用其他组件的方法
            health.takeDamage(10);
        }
    }
}
```

## 🎯 对象管理

### 创建和查找对象

```java
// 创建对象
GameObject enemy = new GameObject("Enemy");
GameObject bullet = new GameObject("Bullet");

// 设置父子关系
player.addChild(bullet);

// 在场景中查找对象
GameObject found = gameWorld.find("Enemy");
if (found != null) {
    System.out.println("找到敌人对象");
}
```

### 添加和获取组件

```java
// 添加组件
enemy.addComponent(new HealthComponent());

// 获取组件（记得检查null）
Transform transform = enemy.getComponent(Transform.class);
if (transform != null) {
    transform.setPosition(50, 30);
}
```

## 🔄 完整示例

### 简单游戏示例

```java
// 定义组件
public class SimpleMover extends Component {
    private float timer = 0;
    
    @Override
    public void start() {
        System.out.println("简单移动器启动");
    }
    
    @Override
    public void update() {
        timer += 0.016f;
        Transform transform = getGameObject().getComponent(Transform.class);
        transform.setPosition((float)Math.sin(timer) * 50, 0);
    }
}

// 主程序
public class SimpleGame {
    public static void main(String[] args) {
        GameEngine engine = GameEngine.getInstance();
        Scene gameWorld = new Scene();
        
        // 创建移动对象
        GameObject movingObj = new GameObject("MovingObject");
        movingObj.addComponent(new SimpleMover());
        gameWorld.getRoot().addChild(movingObj);
        
        engine.registerGameObject(gameWorld.getRoot());
        engine.runEngine();
    }
}
```

## ⚠️ 重要注意事项

1. **每个GameObject自动包含Transform组件**
2. **组件必须通过`addComponent()`添加**
3. **获取组件使用`getComponent(Class)`，记得判空**
4. **awake()和start()每个组件只调用一次**
5. **游戏循环在独立线程运行**

## 📁 项目结构

```
src/
  ├── Engine/
  │   ├── GameEngine.java
  │   ├── Scene.java
  │   ├── GameObject.java
  │   ├── Component.java
  │   ├── GameBehavior.java
  │   └── Transform.java
  └── your-game-package/
      ├── Main.java
      ├── Player.java
      └── components/
          ├── MoveComponent.java
          └── RotateComponent.java
```

按照这个指南，你可以快速开始使用这个游戏框架开发你的项目！
