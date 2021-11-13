package dungeonmania;

public interface PlayerState {
    public abstract void battleEnemy(Enemy enemy);
    public abstract void updateState();
    public abstract String getType();

}
