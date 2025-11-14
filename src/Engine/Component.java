package Engine;

public abstract class Component implements GameBehavior
{
    private String name;
    private GameObject attachedGameObject;
    private boolean isEnabled = true;
    private boolean isStarted = false;
    private boolean isAwaken = false;

    public Component()
    {
        this.name = this.getClass().getSimpleName();
    }

    public void setGameObject(GameObject gameObject)
    {
        attachedGameObject = gameObject;
    }

    public GameObject getGameObject()
    {
        return attachedGameObject;
    }

    @Override
    public void start()
    {
        if (isStarted)
        {
            return;
        }

        isStarted = true;
        onStart();
    }
    public void onStart()
    {

    }

    @Override
    public void update()
    {
    }

    @Override
    public void awake()
    {
        if (isAwaken)
        {
            return;
        }

        isAwaken = true;
        onAwake();
    }

    public void onAwake()
    {

    }
    public boolean isEnabled()
    {
        return isEnabled;
    }
    protected boolean isAwaken(){return isAwaken;}
    public void setEnabled(boolean enabled)
    {
        isEnabled = enabled;
    }

    public void markAwaken()
    {
        isAwaken = true;
    }
    protected boolean isStarted() {return isStarted;}
    protected void markStarted(){isStarted = true;}
}
