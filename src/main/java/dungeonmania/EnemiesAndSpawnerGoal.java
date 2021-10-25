package dungeonmania;

public class EnemiesAndSpawnerGoal implements GoalComponent, Observer  {
    private int numLiving;

    public EnemiesAndSpawnerGoal() {
        numLiving = 0;
    }

    public boolean IsComplete() {
        return (numLiving == 0);
    }


    @Override
    public boolean tryToAttach(Subject entity) {
        if (entity instanceof Spider || entity instanceof Mercenary || entity instanceof ZombieToast || entity instanceof ZombieToastSpawner) {
            entity.attach(this);
            numLiving++;
        }
    }

    @Override
    public void update(Subject entity) {
        numLiving--;
    } 
}
