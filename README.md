# FP-ChineseChess - 中国象棋游戏

一个基于 JavaFX 开发的完整中国象棋游戏，包含自定义游戏引擎和音频系统。

## 🚀 快速开始

### VSCode 用户
1. 打开项目文件夹
2. 按 `F5` 或点击调试面板中的"运行 ChineseChess"
3. 游戏将自动启动

### IntelliJ IDEA 用户
1. 打开项目
2. 运行配置中选择 "ChineseChess"
3. 点击运行按钮

### 命令行运行
```bash
java --module-path "src/javafx-sdk-21.0.9/lib" --add-modules javafx.controls,javafx.fxml,javafx.media,javafx.swing,javafx.web --enable-native-access=javafx.graphics,javafx.media -cp "out/production/FP-ChineseChess" ChineseChess.Main
```

## 🎮 游戏特性

- ✅ 完整的象棋规则实现
- ✅ 用户注册/登录系统
- ✅ 游客模式（无需注册即可游玩）
- ✅ 背景音乐和音效系统
- ✅ 游戏进度自动保存（注册用户）
- ✅ 实时将军和绝杀检测

## 🎵 音频系统

### 背景音乐
- **主菜单**: `bgm_MainMenu.MP3`
- **游戏中**: `bgm_InGame.MP3`

### 音效
- **落子**: `落子.MP3` - 移动棋子时播放
- **吃子**: `吃.MP3` - 吃掉对方棋子时播放
- **将军**: `将军.MP3` - 被将军时播放
- **绝杀**: `绝杀.MP3` - 游戏结束时播放

## 🛠️ 开发环境配置

### JavaFX SDK
项目已包含 JavaFX 21.0.9 SDK，位于 `src/javafx-sdk-21.0.9/`

### VSCode 配置
- `.vscode/launch.json` - 运行和调试配置
- `.vscode/settings.json` - Java 项目设置
- `.vscode/tasks.json` - 编译任务

### IntelliJ IDEA 配置
- `.idea/runConfigurations/ChineseChess.xml` - 运行配置
- `.idea/misc.xml` - 项目设置

## 📁 项目结构

```
src/
├── ChineseChess/          # 游戏主逻辑
│   ├── Main.java         # 程序入口
│   ├── Scene/            # 场景管理
│   ├── ChessBoard/       # 棋盘逻辑
│   ├── ChessPiece/       # 棋子逻辑
│   ├── UI/               # 用户界面
│   └── UsersAndSavingSystem/ # 用户和存档系统
├── Engine/               # 游戏引擎
│   ├── Components/       # 组件系统
│   ├── Core/            # 核心系统
│   └── GameBuilding/    # 游戏构建
└── resources/           # 资源文件
    └── audio/           # 音频文件
```

## 🎯 游戏玩法

1. **注册/登录**: 创建账户保存游戏进度，或选择游客模式
2. **选择棋子**: 点击要移动的棋子
3. **移动棋子**: 点击目标位置移动棋子
4. **特殊音效**: 吃子、将军、绝杀时会播放相应音效
5. **自动保存**: 注册用户游戏进度会自动保存

## 🔧 技术栈

- **Java 21**
- **JavaFX 21.0.9** - GUI 和媒体播放
- **自定义游戏引擎** - 组件化架构
- **JSON 序列化** - 游戏存档

## 📝 许可证

本项目仅用于学习和演示目的。