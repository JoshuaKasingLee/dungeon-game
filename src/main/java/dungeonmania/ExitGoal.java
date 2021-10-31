package dungeonmania;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class ExitGoal implements GoalComponent, Observer  {

    private boolean onExit; 
    public ExitGoal() {
        onExit = false;
    }
    @Override
    public boolean isComplete() {
        return onExit;
    }

    @Override
    public void tryToAttach(Subject entity) {

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

    @Override
    public JSONObject toJSON() {
        Map<String, Object> goalData = new HashMap<String, Object>();
        goalData.put("goal", "exit");
        return new JSONObject(goalData);
    }

    @Override
    public String simpleGoalToString() {
        return ":exit ";
    }

}
