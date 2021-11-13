package dungeonmania.player;

import dungeonmania.moving_entities.Enemy;

public interface PlayerState {
    public abstract void battleEnemy(Enemy enemy);
    public abstract void updateState();
    public abstract String getType();

}
