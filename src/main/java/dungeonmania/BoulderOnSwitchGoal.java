package dungeonmania;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class BoulderOnSwitchGoal implements GoalComponent, Observer  {
    private int unpressedSwitches;
    public BoulderOnSwitchGoal() {
        unpressedSwitches = 0;
    }

    @Override
    public boolean isComplete() {
        return (unpressedSwitches == 0);
    }

    @Override
    public void update(Subject entity) {
        Switch currSwitch = (Switch) entity;
        if (currSwitch.hasBoulder()) {
            unpressedSwitches--;
        } else {
            unpressedSwitches++;
        }
    }

    @Override
    public void tryToAttach(Subject entity) {
        if (entity instanceof Switch) {
            entity.attach(this);
            unpressedSwitches++;
        }

    }

    @Override
    public JSONObject toJSON() {
        Map<String, Object> goalData = new HashMap<String, Object>();
        goalData.put("goal", "boulder");
        return new JSONObject(goalData);
    }

    @Override
    public String simpleGoalToString() {
        return ":boulder ";
    }
}
