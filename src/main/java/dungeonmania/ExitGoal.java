package dungeonmania;

public class ExitGoal implements GoalComponent, Observer  {


    public ExitGoal() {

    }

    public boolean isComplete() {
        return true;
    }

    @Override
    public boolean tryToAttach(Entity entity) {
        if (entity instanceof Exit) {
            entity.attach(this);
        }
    }

    public void update(Subject obj) {
    }
}
