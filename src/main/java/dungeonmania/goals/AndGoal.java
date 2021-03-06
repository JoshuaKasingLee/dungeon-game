package dungeonmania.goals;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class AndGoal extends CompositeGoal {

    
    /** 
     * check if goal is complete by looking at AND condition for subgoals
     * @return boolean
     */
    @Override
    public boolean isComplete() {
    
        return getSubgoals().stream().allMatch(goal -> (goal.isComplete()));
    }

    
    /** 
     * convert goal to JSON
     * @return JSONObject
     */
    @Override
    public JSONObject toJSON() {
        Map<String, Object> goalData = new HashMap<String, Object>();
        goalData.put("goal", "AND");

        JSONArray subgoalsJSON = new JSONArray();

        List<GoalComponent> subgoals = getSubgoals();
        for (GoalComponent subgoal : subgoals) {
            subgoalsJSON.put(subgoal.toJSON());
            
        }
        goalData.put("subgoals", subgoalsJSON);
        return new JSONObject(goalData);
    }


}
