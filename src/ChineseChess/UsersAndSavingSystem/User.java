package ChineseChess.UsersAndSavingSystem;

import java.time.LocalDateTime;
import java.util.UUID;

public class User 
{
    private UUID userUuid;
    private String userName;
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime lastPlayTime;

    public User(String userName, String password)
    {
        this.userUuid = UUID.randomUUID();
        this.createTime = LocalDateTime.now();
        this.userName = userName;
        this.lastPlayTime = LocalDateTime.now();
        this.password = password;
    }

    public User(UUID userUuid, String userName, String password, LocalDateTime createTime, LocalDateTime lastPlayTime)
    {
        this.userUuid = userUuid;
        this.userName = userName;
        this.password = password;
        this.createTime = createTime;
        this.lastPlayTime = lastPlayTime;
    }

    public UUID getUserUuid()
    {
        return userUuid;
    }
    public String getUserName()
    {
        return userName;
    }
    public LocalDateTime getCreateTime()
    {
        return createTime;
    }
    public LocalDateTime getLastPlayTime()
    {
        return lastPlayTime;
    }
    public void updateLastPlayTime()
    {
        this.lastPlayTime = LocalDateTime.now();
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    public boolean isPasswordValid(String password)
    {
        return this.password.equals(password);
    }
    
    public String getPassword()
    {
        return password;
    }
}
