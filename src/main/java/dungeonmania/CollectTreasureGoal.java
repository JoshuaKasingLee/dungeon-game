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
     * check if goal is complete by checking uncollected treasure
     * @return boolean
     */
    @Override
    public boolean isComplete() {
        return (numUncollected == 0);
    }

    
    /** 
     * try to attach entity to this goal
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
     * subtract one treasure from uncollected
     * @param entity
     */
    @Override
    public void update(Subject entity) {
        numUncollected--;
    }

    
    /** 
     * convert goal to JSON
     * @return JSONObject
     */
    @Override
    public JSONObject toJSON() {
        Map<String, Object> goalData = new HashMap<String, Object>();
        goalData.put("goal", "treasure");
        return new JSONObject(goalData);
    }

    
    /** 
     * goal convert to string
     * @return String
     */
    @Override
    public String simpleGoalToString() {
        return ":treasure ";
    }


}
