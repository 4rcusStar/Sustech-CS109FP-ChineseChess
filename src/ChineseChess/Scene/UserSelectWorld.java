package ChineseChess.Scene;

import ChineseChess.UsersAndSavingSystem.UserManager;
import Engine.GameBuilding.GameStarter;
import Engine.GameBuilding.GameWorld;
import Engine.GameBuilding.GameWorldManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class UserSelectWorld extends GameWorld
{
    private VBox container; //容器
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registerButton;
    private Label errorLabel; //错误提示标签
    private UserManager userManager = UserManager.getInstance();

    public UserSelectWorld()
    {
        super(new UserSelectWorldConstructor());
    }

    @Override
    public void onEnter()
    {
        super.onEnter();
        StackPane rootPane = GameStarter.getRootPane();
        if(rootPane == null)
        {
            System.err.println("Root pane not found");
            return;
        }
        
        //创建容器
        container = new VBox();
        container.setSpacing(10);
        container.setPadding(new Insets(20));
        container.setAlignment(Pos.CENTER);
        container.setLayoutX(400);
        container.setLayoutY(300);
        
        //创建用户名输入框
        usernameField = new TextField();
        usernameField.setPromptText("用户名");
        usernameField.setPrefWidth(200);
        
        //创建密码输入
        passwordField = new PasswordField();
        passwordField.setPromptText("密码");
        passwordField.setPrefWidth(200);
        
        //创建登录按钮
        loginButton = new Button("登录");
        loginButton.setPrefWidth(200);
        loginButton.setOnAction(event ->
        {
            clearError();
            String username = usernameField.getText().trim();
            String password = passwordField.getText();
            
            if(username.isEmpty() || password.isEmpty())
            {
                showError("请输入用户名和密码");
                return;
            }
            
            if(!userManager.login(username, password))
            {
                showError("登录失败，请检查用户名和密码...");
            }
            else
            {
                GameWorldManager.getInstance().switchGameWorldTo("MainMenu");
            }
        });
        
        //创建注册按钮
        registerButton = new Button("新用户？注册并登录！");
        registerButton.setPrefWidth(200);
        registerButton.setOnAction(event ->
        {
            clearError();
            String username = usernameField.getText().trim();
            String password = passwordField.getText();
            
            if(username.isEmpty() || password.isEmpty())
            {
                showError("请输入用户名和密码");
                return;
            }
            
            if(userManager.registerUser(username, password))
            {
                userManager.login(username, password);
                GameWorldManager.getInstance().switchGameWorldTo("MainMenu");
            }
            else
            {
                showError("注册失败，用户名已存在");
            }
        });
        
        //创建错误提示标签
        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");
        
        //添加所有控件到容器
        container.getChildren().addAll(usernameField, passwordField, loginButton, registerButton, errorLabel);
        
        //将容器添加到rootPane
        rootPane.getChildren().add(container);
    }
    
    @Override
    public void onExit()
    {
        StackPane rootPane = GameStarter.getRootPane();
        if(rootPane != null && container != null)
        {
            rootPane.getChildren().remove(container);
        }
        container = null;
        usernameField = null;
        passwordField = null;
        loginButton = null;
        registerButton = null;
        errorLabel = null;
        super.onExit();
    }
    
    private void showError(String message)
    {
        if(errorLabel != null)
        {
            errorLabel.setText(message);
        }
    }
    
    private void clearError()
    {
        if(errorLabel != null)
        {
            errorLabel.setText("");
        }
    }
}
