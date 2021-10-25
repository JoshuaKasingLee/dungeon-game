package dungeonmania;

public class EnemiesAndSpawnerGoal implements GoalComponent, Observer  {
    public EnemiesAndSpawnerGoal() {

    }

    public boolean IsComplete() {
        return false;
    }

    public void update(Subject obj) {

    }

    @Override
    public boolean tryToAttach(Entity entity) {
        if (entity instanceof Spider || entity instanceof Mercenary || entity instanceof ZombieToast || entity instanceof ZombieToastSpawner) {
            entity.attach(this);
        }
    }
}
