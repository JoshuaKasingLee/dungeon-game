package dungeonmania.goals;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class OrGoal extends CompositeGoal{

    
    /** 
     * check if goal is complete by looking at OR condition for subgoals
     * @return boolean
     */
    @Override
    public boolean isComplete() {
        return getSubgoals().stream().anyMatch(goal -> (goal.isComplete()));
    }

    
    /** 
     * convert goal to JSON
     * @return JSONObject
     */
    @Override
    public JSONObject toJSON() {
        Map<String, Object> goalData = new HashMap<String, Object>();
        goalData.put("goal", "OR");

        JSONArray subgoalsJSON = new JSONArray();
        List<GoalComponent> subgoals = getSubgoals();
        for (GoalComponent subgoal : subgoals) {
            subgoalsJSON.put(subgoal.toJSON());
        }
        goalData.put("subgoals", subgoalsJSON);
        return new JSONObject(goalData);
    }
}
