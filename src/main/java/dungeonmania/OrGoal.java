package dungeonmania;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class OrGoal extends CompositeGoal{
    private List<GoalComponent> subgoals;
    public OrGoal() {
        this.subgoals = new ArrayList<GoalComponent>();
    }

    @Override
    public boolean isComplete() {
        return subgoals.stream().anyMatch(goal -> (goal.isComplete()));
    }

    @Override
    public JSONObject toJSON() {
        Map<String, Object> goalData = new HashMap<String, Object>();
        goalData.put("goal", "OR");

        JSONArray subgoalsJSON = new JSONArray();
        for (GoalComponent subgoal : subgoals) {
            subgoalsJSON.put(subgoal.toJSON());
        }
        goalData.put("subgoals", subgoalsJSON);
        return new JSONObject(goalData);
    }
}
