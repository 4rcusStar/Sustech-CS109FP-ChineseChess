package Engine.Components;

import Engine.Input;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * 通用的按钮组件
 * 提供按钮的基本功能：渲染、鼠标检测、点击回调
 */
public abstract class Button extends RendererComponent
{
    // 按钮位置和大小
    protected float originX;
    protected float originY;
    protected float width = 120f;
    protected float height = 40f;
    protected float offsetX = 0f;
    protected float offsetY = 0f;
    
    // 按钮样式
    protected Color normalColor = Color.rgb(100, 149, 237); // 正常颜色（矢车菊蓝）
    protected Color hoverColor = Color.rgb(70, 130, 180);  // 悬停颜色（钢蓝色）
    protected Color borderColor = Color.rgb(65, 105, 225);  // 边框颜色（深蓝色）
    protected Color textColor = Color.WHITE;                // 文字颜色
    protected float borderWidth = 2f;                       // 边框宽度
    protected float cornerRadius = 10f;                     // 圆角半径
    
    // 文字样式
    protected String text = "";
    protected String fontFamily = "Arial";
    protected int fontSize = 16;
    protected FontWeight fontWeight = FontWeight.BOLD;
    
    // 状态
    protected boolean isHovered = false;
    protected boolean isEnabled = true;
    
    @Override
    public void onAwake()
    {
        Transform transform = getGameObject().getComponent(Transform.class);
        if (transform != null)
        {
            originX = transform.getX();
            originY = transform.getY();
        }
    }
    
    @Override
    public void update()
    {
        if (!isEnabled)
        {
            isHovered = false;
            return;
        }
        
        // 检查鼠标是否在按钮上
        float mouseX = (float) Input.getMouseX();
        float mouseY = (float) Input.getMouseY();
        
        float buttonX = getButtonX();
        float buttonY = getButtonY();
        
        isHovered = (mouseX >= buttonX && mouseX <= buttonX + width &&
                     mouseY >= buttonY && mouseY <= buttonY + height);
        
        // 检查点击
        if (isHovered && Input.isMouseClicked())
        {
            onClick();
        }
    }
    
    @Override
    public void render(GraphicsContext gc)
    {
        if (!isEnabled || !shouldRender())
        {
            return;
        }
        
        float buttonX = getButtonX();
        float buttonY = getButtonY();
        
        // 绘制按钮背景
        Color fillColor = isHovered ? hoverColor : normalColor;
        gc.setFill(fillColor);
        gc.fillRoundRect(buttonX, buttonY, width, height, cornerRadius, cornerRadius);
        
        // 绘制按钮边框
        gc.setStroke(borderColor);
        gc.setLineWidth(borderWidth);
        gc.strokeRoundRect(buttonX, buttonY, width, height, cornerRadius, cornerRadius);
        
        // 绘制按钮文字
        if (text != null && !text.isEmpty())
        {
            gc.setFont(Font.font(fontFamily, fontWeight, fontSize));
            gc.setFill(textColor);
            
            // 计算文字居中位置
            Text textNode = new Text(text);
            textNode.setFont(gc.getFont());
            double textWidth = textNode.getBoundsInLocal().getWidth();
            double textHeight = textNode.getBoundsInLocal().getHeight();
            
            float textX = buttonX + (width / 2f) - (float)(textWidth / 2);
            float textY = buttonY + (height / 2f) + (float)(textHeight / 4);
            
            gc.fillText(text, textX, textY);
        }
    }
    
    /**
     * 获取按钮的X坐标
     */
    protected float getButtonX()
    {
        return originX + offsetX;
    }
    
    /**
     * 获取按钮的Y坐标
     */
    protected float getButtonY()
    {
        return originY + offsetY;
    }
    
    /**
     * 判断是否应该渲染按钮
     * 子类可以重写此方法来实现条件渲染
     */
    protected boolean shouldRender()
    {
        return true;
    }
    
    /**
     * 按钮点击回调
     */
    protected abstract void onClick();
    
    /**
     * 设置按钮大小
     */
    public void setSize(float width, float height)
    {
        this.width = width;
        this.height = height;
    }
    
    /**
     * 设置按钮偏移量
     */
    public void setOffset(float offsetX, float offsetY)
    {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
    
    /**
     * 设置按钮文字
     */
    public void setText(String text)
    {
        this.text = text;
    }
    
    /**
     * 设置按钮颜色
     */
    public void setColors(Color normalColor, Color hoverColor)
    {
        this.normalColor = normalColor;
        this.hoverColor = hoverColor;
    }
    
    /**
     * 设置边框颜色
     */
    public void setBorderColor(Color borderColor)
    {
        this.borderColor = borderColor;
    }
    
    /**
     * 设置边框宽度
     */
    public void setBorderWidth(float borderWidth)
    {
        this.borderWidth = borderWidth;
    }
    
    /**
     * 设置文字颜色
     */
    public void setTextColor(Color textColor)
    {
        this.textColor = textColor;
    }
    
    /**
     * 设置字体
     */
    public void setFont(String fontFamily, int fontSize, FontWeight fontWeight)
    {
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
        this.fontWeight = fontWeight;
    }
    
    /**
     * 设置圆角半径
     */
    public void setCornerRadius(float cornerRadius)
    {
        this.cornerRadius = cornerRadius;
    }
    
    /**
     * 设置按钮是否启用
     */
    public void setButtonEnabled(boolean enabled)
    {
        this.isEnabled = enabled;
    }
    
    /**
     * 获取按钮是否启用
     */
    public boolean isButtonEnabled()
    {
        return isEnabled;
    }
}

