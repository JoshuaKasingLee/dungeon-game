package dungeonmania;

public interface CharacterState {
    public abstract void battleEnemy(Enemy enemy);
    public abstract void updateState();
    public abstract String getType();

}
