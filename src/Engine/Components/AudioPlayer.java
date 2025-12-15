package Engine.Components;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

/**
 * 音频播放器组件，用于播放游戏音效和背景音乐
 */
public class AudioPlayer extends Component
{
    // 音频文件路径常量
    private static final String AUDIO_PATH = "/resources/audio/";

    // 单例实例
    private static AudioPlayer instance;

    // 背景音乐播放器
    private MediaPlayer bgmPlayer;
    private boolean bgmEnabled = true;
    private double bgmVolume = 0.5;

    // 当前播放的背景音乐类型
    public enum BGMType
    {
        MAIN_MENU,
        IN_GAME
    }

    private BGMType currentBGM;

    /**
     * 获取单例实例
     */
    public static AudioPlayer getInstance()
    {
        if (instance == null)
        {
            instance = new AudioPlayer();
        }
        return instance;
    }

    @Override
    public void onAwake()
    {
        System.out.println("AudioPlayer: onAwake called for " + this.getClass().getSimpleName());
        // 初始化时不播放任何音频，等待外部调用
    }

    @Override
    public void onStart()
    {
        System.out.println("AudioPlayer: onStart called for " + this.getClass().getSimpleName());
        // 组件启动时的初始化
    }

    /**
     * 播放背景音乐
     * @param bgmType 背景音乐类型
     */
    public void playBGM(BGMType bgmType)
    {
        System.out.println("AudioPlayer.playBGM called with: " + bgmType);

        if (!bgmEnabled)
        {
            System.out.println("AudioPlayer: BGM disabled, returning");
            return;
        }

        // 如果正在播放相同的背景音乐，不需要重新开始
        if (currentBGM == bgmType && bgmPlayer != null &&
            bgmPlayer.getStatus() == MediaPlayer.Status.PLAYING)
        {
            System.out.println("AudioPlayer: Same BGM already playing, returning");
            return;
        }

        // 停止当前背景音乐
        stopBGM();

        String audioFile;
        switch (bgmType)
        {
            case MAIN_MENU:
                audioFile = "bgm_MainMenu.MP3";
                break;
            case IN_GAME:
                audioFile = "bgm_InGame.MP3";
                break;
            default:
                System.out.println("AudioPlayer: Unknown BGM type");
                return;
        }

        try
        {
            URL resource = getClass().getResource(AUDIO_PATH + audioFile);
            System.out.println("AudioPlayer: Looking for resource: " + AUDIO_PATH + audioFile);
            System.out.println("AudioPlayer: Resource URL: " + (resource != null ? resource.toString() : "null"));

            if (resource == null)
            {
                System.err.println("AudioPlayer: Audio file not found: " + AUDIO_PATH + audioFile);
                return;
            }

            Media media = new Media(resource.toString());
            bgmPlayer = new MediaPlayer(media);
            bgmPlayer.setVolume(bgmVolume);
            bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE); // 循环播放

            // 添加状态监听器
            bgmPlayer.setOnReady(() -> System.out.println("AudioPlayer: MediaPlayer ready for " + audioFile));
            bgmPlayer.setOnError(() -> System.out.println("AudioPlayer: MediaPlayer error for " + audioFile + ": " + bgmPlayer.getError()));

            bgmPlayer.play();

            currentBGM = bgmType;
            System.out.println("AudioPlayer: Started playing BGM: " + audioFile);
        }
        catch (Exception e)
        {
            System.err.println("AudioPlayer: Failed to play BGM: " + audioFile + " - " + e.getMessage());
        }
    }

    /**
     * 停止背景音乐
     */
    public void stopBGM()
    {
        if (bgmPlayer != null)
        {
            bgmPlayer.stop();
            bgmPlayer.dispose();
            bgmPlayer = null;
            currentBGM = null;
        }
    }

    /**
     * 暂停背景音乐
     */
    public void pauseBGM()
    {
        if (bgmPlayer != null)
        {
            bgmPlayer.pause();
        }
    }

    /**
     * 恢复背景音乐
     */
    public void resumeBGM()
    {
        if (bgmPlayer != null)
        {
            bgmPlayer.play();
        }
    }

    /**
     * 设置背景音乐音量
     * @param volume 音量 (0.0 - 1.0)
     */
    public void setBGMVolume(double volume)
    {
        bgmVolume = Math.max(0.0, Math.min(1.0, volume));
        if (bgmPlayer != null)
        {
            bgmPlayer.setVolume(bgmVolume);
        }
    }

    /**
     * 播放音效
     * @param soundEffect 音效类型
     */
    public void playSoundEffect(SoundEffect soundEffect)
    {
        String audioFile;
        switch (soundEffect)
        {
            case MOVE_PIECE:
                audioFile = "落子.MP3";
                break;
            case CAPTURE_PIECE:
                audioFile = "吃.MP3";
                break;
            case CHECK:
                audioFile = "将军.MP3";
                break;
            case CHECKMATE:
                audioFile = "绝杀.MP3";
                break;
            default:
                return;
        }

        playSoundOnce(audioFile);
    }

    /**
     * 播放单个音效文件（一次性播放）
     * @param audioFile 音频文件名
     */
    private void playSoundOnce(String audioFile)
    {
        System.out.println("AudioPlayer: Attempting to play sound effect: " + audioFile);

        try
        {
            URL resource = getClass().getResource(AUDIO_PATH + audioFile);
            System.out.println("AudioPlayer: Sound effect resource URL: " + (resource != null ? resource.toString() : "null"));

            if (resource == null)
            {
                System.err.println("AudioPlayer: Sound effect file not found: " + AUDIO_PATH + audioFile);
                return;
            }

            Media media = new Media(resource.toString());
            MediaPlayer player = new MediaPlayer(media);
            player.setVolume(0.7); // 音效音量稍微大一点

            // 添加状态监听器
            player.setOnReady(() -> System.out.println("AudioPlayer: Sound effect MediaPlayer ready for " + audioFile));
            player.setOnError(() -> System.out.println("AudioPlayer: Sound effect MediaPlayer error for " + audioFile + ": " + player.getError()));

            player.setOnEndOfMedia(() -> {
                System.out.println("AudioPlayer: Sound effect finished playing: " + audioFile);
                player.dispose(); // 播放结束后清理资源
            });

            player.play();
            System.out.println("AudioPlayer: Started playing sound effect: " + audioFile);
        }
        catch (Exception e)
        {
            System.err.println("AudioPlayer: Failed to play sound effect: " + audioFile + " - " + e.getMessage());
        }
    }

    /**
     * 播放落子音效（移动棋子）
     */
    public void playMovePieceSound()
    {
        playSoundEffect(SoundEffect.MOVE_PIECE);
    }

    /**
     * 播放吃子音效
     */
    public void playCapturePieceSound()
    {
        playSoundEffect(SoundEffect.CAPTURE_PIECE);
    }

    /**
     * 播放将军音效
     */
    public void playCheckSound()
    {
        playSoundEffect(SoundEffect.CHECK);
    }

    /**
     * 播放绝杀音效
     */
    public void playCheckmateSound()
    {
        playSoundEffect(SoundEffect.CHECKMATE);
    }

    /**
     * 设置背景音乐启用状态
     * @param enabled 是否启用背景音乐
     */
    public void setBGMEnabled(boolean enabled)
    {
        bgmEnabled = enabled;
        if (!enabled)
        {
            stopBGM();
        }
        else if (currentBGM != null)
        {
            // 如果重新启用，恢复播放当前背景音乐
            playBGM(currentBGM);
        }
    }

    /**
     * 获取当前背景音乐状态
     * @return 是否正在播放背景音乐
     */
    public boolean isBGMPlaying()
    {
        return bgmPlayer != null && bgmPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    /**
     * 获取当前背景音乐类型
     * @return 当前背景音乐类型
     */
    public BGMType getCurrentBGM()
    {
        return currentBGM;
    }

    /**
     * 音效类型枚举
     */
    public enum SoundEffect
    {
        MOVE_PIECE,     // 落子/移动
        CAPTURE_PIECE,  // 吃子
        CHECK,          // 将军
        CHECKMATE       // 绝杀
    }
}
