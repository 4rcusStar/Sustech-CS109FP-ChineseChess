package Engine;

public abstract class Component implements GameBehavior
{
    private String name;
    private boolean isEnabled = true;
    private boolean isStarted = false;

    public Component()
    {
        GameEngine.getInstance().registerComponent(this);
        this.name = this.getClass().getSimpleName();
        this.awake();
    }

    @Override
    public void start()
    {
    }

    @Override
    public void update()
    {
    }

    @Override
    public void awake()
    {
    }

    public boolean isEnabled()
    {
        return isEnabled;
    }
    public void setEnabled(boolean enabled)
    {
        isEnabled = enabled;
    }

    protected boolean isStarted() {return isStarted;}
    protected void markStarted(){isStarted = true;}
}
