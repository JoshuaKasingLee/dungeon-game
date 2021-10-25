package dungeonmania;

public class CollectTreasureGoal implements GoalComponent, Observer {
    private int numUncollected;

    public CollectTreasureGoal() {
        numUncollected = 0;
    }

    @Override
    public boolean isComplete() {
        return (numUncollected == 0);
    }

    @Override
    public boolean tryToAttach(Subject entity) {
        if (entity instanceof Treasure) {
            entity.attach(this);
            numUncollected++;
        }
    }

    @Override
    public void update(Subject entity) {
        numUncollected--;
    }


}
