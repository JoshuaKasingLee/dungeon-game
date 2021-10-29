package dungeonmania;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

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

    @Override
    public JSONObject toJSON() {
        Map<String, Object> goalData = new HashMap<String, Object>();
        goalData.put("goal", "treasure");
        return new JSONObject(goalData);
    }

    @Override
    public String simpleGoalToString() {
        return ":treasure ";
    }


}
