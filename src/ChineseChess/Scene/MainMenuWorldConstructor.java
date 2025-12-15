package ChineseChess.Scene;

import Engine.Components.SpriteRenderer;
import Engine.Core.GameObject;
import Engine.GameBuilding.GameWorldConstructor;
import Engine.Audio.AudioManager;
import javafx.scene.image.Image;

public class MainMenuWorldConstructor extends GameWorldConstructor
{
    @Override
    public void construct(GameObject root)
    {
        // 添加背景图片（填充整个canvas：1000x800）
        GameObject backgroundObj = new GameObject("MainMenuBackground", 0, 0);
        Image bgImage = new Image("file:src/resources/images/mainmenubg.png");
        SpriteRenderer bgRenderer = new SpriteRenderer(bgImage, 1000f, 800f);
        backgroundObj.addComponent(bgRenderer);
        // 设置背景渲染优先级为最低，确保在其他元素之后渲染
        bgRenderer.setRenderPriority(-1);
        root.addChild(backgroundObj);

        // 添加主菜单渲染器
        root.addComponent(new MainMenuRenderer());

        // 播放主菜单背景音乐
        AudioManager.getInstance().playBGM(AudioManager.BGMType.MAIN_MENU);
    }
}
