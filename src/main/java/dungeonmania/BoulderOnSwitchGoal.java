package dungeonmania;

public class BoulderOnSwitchGoal implements GoalComponent, Observer  {
    public BoulderOnSwitchGoal() {

    }

    public boolean IsComplete() {
        return false;
    }

    public void update(Subject obj) {

    }

    @Override
    public boolean tryToAttach(Entity entity) {
        if (entity instanceof Boulder) {
            entity.attach(this);
        }
    }
}
