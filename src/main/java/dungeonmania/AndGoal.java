package dungeonmania;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class AndGoal extends CompositeGoal {

    @Override
    public boolean isComplete() {
        return subgoals.stream().allMatch(goal -> (goal.isComplete()));
    }

    @Override
    public JSONObject toJSON() {
        Map<String, Object> goalData = new HashMap<String, Object>();
        goalData.put("goal", "AND");

        JSONArray subgoalsJSON = new JSONArray();
        for (GoalComponent subgoal : subgoals) {
            subgoalsJSON.put(subgoal.toJSON());
            
        }
        goalData.put("subgoals", subgoalsJSON);
        return new JSONObject(goalData);
    }

}
