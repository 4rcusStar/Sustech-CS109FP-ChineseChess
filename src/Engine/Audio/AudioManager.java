package Engine.Audio;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 全局音频管理器
 * 负责管理游戏中的所有音频播放
 * 改进版本：解决音效中断问题
 */
public class AudioManager
{
    private static AudioManager instance;
    private MediaPlayer bgmPlayer;
    private static final String AUDIO_PATH = "/resources/audio/";
    
    // 音效播放队列
    private final ConcurrentLinkedQueue<MediaPlayer> activeSoundPlayers = new ConcurrentLinkedQueue<>();
    
    private long lastMoveSoundTime = 0;
    private long lastCaptureSoundTime = 0;
    private long lastCheckSoundTime = 0;
    private long lastCheckmateSoundTime = 0;
    private static final long SOUND_COOLDOWN_MS = 100; // 100毫秒冷却时间

    // 背景音乐类型
    public enum BGMType
    {
        MAIN_MENU("bgm_MainMenu.MP3"),
        IN_GAME("bgm_InGame.MP3");

        private final String fileName;
        BGMType(String fileName) { this.fileName = fileName; }
        public String getFileName() { return fileName; }
    }

    private BGMType currentBGM;

    private AudioManager() {}

    /**
     * 获取单例实例
     */
    public static AudioManager getInstance()
    {
        if (instance == null)
        {
            synchronized (AudioManager.class)
            {
                if (instance == null)
                {
                    instance = new AudioManager();
                }
            }
        }
        return instance;
    }

    /**
     * 播放背景音乐
     */
    public void playBGM(BGMType bgmType)
    {
        // 在JavaFX线程
        if (!Platform.isFxApplicationThread())
        {
            Platform.runLater(() -> playBGM(bgmType));
            return;
        }

        if (currentBGM == bgmType && bgmPlayer != null &&
            bgmPlayer.getStatus() == MediaPlayer.Status.PLAYING)
        {
            return;
        }

        // 停止当前背景音乐
        stopBGM();

        try
        {
            URL resource = AudioManager.class.getResource(AUDIO_PATH + bgmType.getFileName());
            if (resource == null)
            {
                System.err.println("[AudioManager] BGM文件未找到: " + AUDIO_PATH + bgmType.getFileName());
                return;
            }

            Media media = new Media(resource.toString());
            bgmPlayer = new MediaPlayer(media);
            bgmPlayer.setVolume(0.1);
            bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE); 


            bgmPlayer.play();
            currentBGM = bgmType;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 停止背景音乐
     */
    public void stopBGM()
    {
        if (!Platform.isFxApplicationThread())
        {
            Platform.runLater(() -> stopBGM());
            return;
        }

        if (bgmPlayer != null)
        {
            try
            {
                bgmPlayer.stop();
                bgmPlayer.dispose();
            }
            catch (Exception e)
            {
                // 忽略dispose时的异常
            }
            bgmPlayer = null;
            currentBGM = null;
        }
    }

    /**
     * 播放音效（使用默认音量0.8）
     * @param soundFile 音效文件名
     */
    public void playSound(String soundFile)
    {
        playSound(soundFile, 0.8);
    }

    /**
     * 播放音效（指定音量）
     * @param soundFile 音效文件名
     * @param volume 音量（0.0 - 1.0）
     */
    public void playSound(String soundFile, double volume)
    {
        // 确保在JavaFX线程上执行
        if (!Platform.isFxApplicationThread())
        {
            Platform.runLater(() -> playSound(soundFile, volume));
            return;
        }

        try
        {
            URL resource = AudioManager.class.getResource(AUDIO_PATH + soundFile);

            Media media = new Media(resource.toString());
            MediaPlayer player = new MediaPlayer(media);
            double clampedVolume = Math.max(0.0, Math.min(1.0, volume));
            player.setVolume(clampedVolume);

            activeSoundPlayers.offer(player);

            // 设置状态监听器
            player.setOnReady(() -> {
                // 确保MediaPlayer已准备好再播放
                if (player.getStatus() == MediaPlayer.Status.READY)
                {
                    player.play();
                }
            });

            // 播放完成后延迟清理，确保音效完整播放
            player.setOnEndOfMedia(() -> {
                // 延迟清理，确保音效完整播放
                Platform.runLater(() -> {
                    try
                    {
                        if (player.getStatus() != MediaPlayer.Status.DISPOSED)
                        {
                            player.stop();
                            player.dispose();
                        }
                    }
                    catch (Exception e)
                    {
                        // 忽略清理时的异常
                    }
                    finally
                    {
                        activeSoundPlayers.remove(player);
                    }
                });
            });

            player.setOnError(() -> {
                Platform.runLater(() -> {
                    try
                    {
                        if (player.getStatus() != MediaPlayer.Status.DISPOSED)
                        {
                            player.dispose();
                        }
                    }
                    catch (Exception e)
                    {
                        // 忽略清理时的异常
                    }
                    finally
                    {
                        activeSoundPlayers.remove(player);
                    }
                });
            });

            // 如果MediaPlayer已经准备好，立即播放
            if (player.getStatus() == MediaPlayer.Status.READY)
            {
                player.play();
            }
            // 否则等待onReady回调

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void playMoveSound()
    {
        long now = System.currentTimeMillis();
        if (now - lastMoveSoundTime < SOUND_COOLDOWN_MS)
        {
            return; // 冷却时间内，忽略重复播放
        }
        lastMoveSoundTime = now;
        playSound("落子.MP3");
    }

    /**
     * 播放吃子音效
     */
    public void playCaptureSound()
    {
        long now = System.currentTimeMillis();
        if (now - lastCaptureSoundTime < SOUND_COOLDOWN_MS)
        {
            return; 
        }
        lastCaptureSoundTime = now;
        playSound("吃.MP3");
    }

    public void playCheckSound()
    {
        long now = System.currentTimeMillis();
        if (now - lastCheckSoundTime < SOUND_COOLDOWN_MS)
        {
            return;
        }
        lastCheckSoundTime = now;
        
        // 延迟播放，确保移动音效先播放完成
        Platform.runLater(() -> {
            // 延迟后再次检查，确保不会重复播放
            long delayedNow = System.currentTimeMillis();
            if (delayedNow - lastCheckSoundTime < 50)
            {
                // 更新时间为当前时间，但继续播放
                lastCheckSoundTime = delayedNow;
            }
            playSound("将军.MP3", 0.64);
        });
    }

    public void playCheckmateSound()
    {
        long now = System.currentTimeMillis();
        if (now - lastCheckmateSoundTime < SOUND_COOLDOWN_MS)
        {
            return; // 冷却时间内，忽略重复播放
        }
        lastCheckmateSoundTime = now;
        playSound("绝杀.MP3", 0.96); 
    }

    /**
     * 设置背景音乐音量
     */
    public void setBGMVolume(double volume)
    {
        double clampedVolume = Math.max(0.0, Math.min(1.0, volume));
        if (bgmPlayer != null)
        {
            bgmPlayer.setVolume(clampedVolume);
        }
    }

    /**
     * 清理
     */
    public void dispose()
    {
        if (!Platform.isFxApplicationThread())
        {
            Platform.runLater(() -> dispose());
            return;
        }

        stopBGM();

        MediaPlayer player;
        while ((player = activeSoundPlayers.poll()) != null)
        {
            try
            {
                if (player.getStatus() != MediaPlayer.Status.DISPOSED)
                {
                    player.stop();
                    player.dispose();
                }
            }
            catch (Exception e)
            {
            }
        }

        instance = null;
    }
}

