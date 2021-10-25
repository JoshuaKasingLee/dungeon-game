package dungeonmania;

public class ExitGoal implements GoalComponent, Observer  {

    private boolean onExit; 
    public ExitGoal() {
        onExit = false;
    }

    public boolean isComplete() {
        return onExit;
    }

    @Override
    public boolean tryToAttach(Subject entity) {
        if (entity instanceof Exit) {
            entity.attach(this);
        }
    }
 
    @Override
    public void update(Subject entity) {
        Exit currExit = (Exit) entity;
        if (currExit.hasPlayer()) {
            onExit = true;
        } else {
            onExit = false;
        }
    }
}
