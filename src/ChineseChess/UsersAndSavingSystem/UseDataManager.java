package ChineseChess.UsersAndSavingSystem;


import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 负责用户数据的保存/加载
 */
public class UseDataManager 
{
    private static final String SAVES_DIRECTORY = "saves";
    private static final String USER_DATA_FILE = "saves/users.txt";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private void checkDirectory()
    {
        try
        {
            Files.createDirectories(Paths.get(SAVES_DIRECTORY));
        }
        catch(IOException e)
        {
            System.err.println("Error creating saves directory: " + e.getMessage());
        }
    }

    /**
     * 加载所有用户
     * 文件格式：UUID||username||password||registerTime||lastLoginTime
     */
    public List<User> loadUsers()
    {
        List<User> users = new ArrayList<>();
        File file = new File(USER_DATA_FILE);
        if(!file.exists())
        {
            return users;
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            while((line = reader.readLine()) != null)
            {
                line = line.trim();
                if(line.isEmpty() || line.startsWith("#"))
                {
                    continue;
                }
                User user = parseUserLine(line);
                if(user != null)
                {
                    users.add(user);
                }
            }
        }
        catch(IOException e)
        {
            System.err.println("Error loading users: " + e.getMessage());
        }
        return users;
    }
    private User parseUserLine(String line)
    {
        String[] parts = line.split("\\|\\|");
        if(parts.length != 5)
        {
            return null;
        UUID uuid = UUID.fromString(parts[0]);
        String userName = parts[1];
        String password = parts[2];
        LocalDateTime registerTime = LocalDateTime.parse(parts[3], DATE_TIME_FORMATTER);
        LocalDateTime lastLoginTime = LocalDateTime.parse(parts[4], DATE_TIME_FORMATTER);

        User user = new User(uuid, userName, password, registerTime, lastLoginTime);

    }

    /**
     * 根据uuid获取用户目录
     * @param uuid UUID
     * @return 用户目录
     */
    public String getUserDirectory(UUID uuid)
    {
        String userDirectory = SAVES_DIRECTORY + "/user_" + uuid.toString();
        return userDirectory;
    }

    /**
     * 保存所有用户到文件
     */
    public void saveUsers(List<User> users)
    {
        checkDirectory();
        
        try(PrintWriter writer = new PrintWriter(new FileWriter(USER_DATA_FILE)))
        {
            writer.println("# User data file");
            writer.println("# Format: UUID||userName||password||createTime||lastPlayTime");
            
            for(User user : users)
            {
                writer.println(formatUserLine(user));
            }
        }
        catch(IOException e)
        {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
    
    /**
     * 将用户对象格式化为文本行
     */
    private String formatUserLine(User user)
    {
        return user.getUserUuid().toString() + "||" +
               user.getUserName() + "||" +
               user.getPassword() + "||" +
               user.getCreateTime().format(DATE_TIME_FORMATTER) + "||" +
               user.getLastPlayTime().format(DATE_TIME_FORMATTER);
    }

    /**
     * 创建用户目录
     * @param uuid UUID
     * @throws IOException 
     */
    public void createUserDirectory(UUID uuid) throws IOException
    {
        String userDir = getUserDirectory(uuid);
        Files.createDirectories(Paths.get(userDir));
        Files.createDirectories(Paths.get(userDir + "/saves"));
    }
}

