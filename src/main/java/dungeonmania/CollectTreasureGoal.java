package dungeonmania;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class CollectTreasureGoal implements GoalComponent, Observer {
    private int numUncollected;

    public CollectTreasureGoal() {
        numUncollected = 0;
    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isComplete() {
        return (numUncollected == 0);
    }

    
    /** 
     * @param entity
     */
    @Override
    public void tryToAttach(Subject entity) {
        if (entity instanceof Treasure) {
            entity.attach(this);
            numUncollected++;
        }
    }

    
    /** 
     * @param entity
     */
    @Override
    public void update(Subject entity) {
        numUncollected--;
    }

    
    /** 
     * @return JSONObject
     */
    @Override
    public JSONObject toJSON() {
        Map<String, Object> goalData = new HashMap<String, Object>();
        goalData.put("goal", "treasure");
        return new JSONObject(goalData);
    }

    
    /** 
     * @return String
     */
    @Override
    public String simpleGoalToString() {
        return ":treasure ";
    }


}
