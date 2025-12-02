package ChineseChess.UsersAndSavingSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserManager 
{
    private static UserManager instance;
    private List<User> users = new ArrayList<>();
    private User currentUser;
    private UseDataManager dataManager;

    private UserManager()
    {
        dataManager = new UseDataManager();
    }

    public static UserManager getInstance()
    {
        if(instance == null)
        {
            instance = new UserManager();
        }
        return instance;
    }

    /**
     * ,从文件加载所有用户
     * 
     */
    public void initialize()
    {
        users = dataManager.loadUsers();
    }

    /**
     * 获取所有用户列表
     *  * @return 用户
     */
    public List<User> getUsers()
    {
        return users;
    }

    public User getCurrentUser()
    {
        return currentUser;
    }


    public void setCurrentUser(User user)
    {
        currentUser = user;
        if(user != null)
        {
            user.updateLastPlayTime();
            saveUsers();
        }
    }

    /**
     * 注册新用户
     */
    public boolean registerUser(String userName, String password)
    {
        if(isUserNameExists(userName))
        {
            return false;
        }
        
        User newUser = new User(userName, password);
        users.add(newUser);
        
        try
        {
            dataManager.createUserDirectory(newUser.getUserUuid());
            saveUsers();
            return true;
        }
        catch(Exception e)
        {
            System.err.println("Failed to create user directory: " + e.getMessage());
            users.remove(newUser);
            return false;
        }
    }

    /**
     * 添加用户
     */
    public void addUser(User user)
    {
        users.add(user);
    }

    /**
     * 删除用户并自动保存
     */
    public void removeUser(User user)
    {
        users.remove(user);
        if(currentUser == user)
        {
            currentUser = null;
        }
        saveUsers();
    }

    public User getUserByUuid(UUID uuid)
    {
        for(User user : users)
        {
            if(user.getUserUuid().equals(uuid))
            {
                return user;
            }
        }
        return null;
    }

    public User getUserByUserName(String userName)
    {
        for(User user : users)
        {
            if(user.getUserName().equals(userName))
            {
                return user;
            }
        }
        return null;
    }

    public boolean isUserNameExists(String userName)
    {
        return getUserByUserName(userName) != null;
    }

    /**
     * !!用户登录!!
     * @param userName 用户名
     * @param password 密码
     * @return 登录成功返回true，失败返回false
     */
    public boolean login(String userName, String password)
    {
        User user = getUserByUserName(userName);
        if(user != null && user.isPasswordValid(password))
        {
            setCurrentUser(user);
            return true;
        }
        return false;
    }

    /**
     * 保存所有用户到文件
     */
    public void saveUsers()
    {
        dataManager.saveUsers(users);
    }

    /**
     * 获取当前用户的存档目录
     */
    public String getCurrentUserSaveDirectory()
    {
        if(currentUser == null)
        {
            return null;
        }
        return dataManager.getUserDirectory(currentUser.getUserUuid()) + "/saves";
    }
}