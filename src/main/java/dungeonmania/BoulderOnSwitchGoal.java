package dungeonmania;

import java.util.ArrayList;
import java.util.List;

public class BoulderOnSwitchGoal implements GoalComponent, Observer  {
    private int unpressedSwitches;
    public BoulderOnSwitchGoal() {
        unpressedSwitches = 0;
    }

    public boolean IsComplete() {
        return (unpressedSwitches == 0);
    }

    public void update(Subject entity) {
        Switch currSwitch = (Switch) entity;
        if (currSwitch.hasBoulder()) {
            unpressedSwitches--;
        } else {
            unpressedSwitches++;
        }
    }

    @Override
    public boolean tryToAttach(Entity entity) {
        if (entity instanceof Switch) {
            entity.attach(this);
            unpressedSwitches++;
        }
    }
}
