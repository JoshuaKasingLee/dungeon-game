package dungeonmania;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class EnemiesAndSpawnerGoal implements GoalComponent, Observer  {
    private int numLiving;

    public EnemiesAndSpawnerGoal() {
        numLiving = 0;
    }
    
    /** 
     * check if goal is complete but counting number of enemies alive
     * @return boolean
     */
    @Override
    public boolean isComplete() {
        return (numLiving == 0);
    }


    
    /** 
     * try to attach entity to this goal
     * @param entity
     */
    @Override
    public void tryToAttach(Subject entity) {
        if (entity instanceof Enemy && !((Enemy) entity).isAlly()) {
            entity.attach(this);
            numLiving++;
        }
    }

    
    /** 
     * decrease the counter for enemies living
     * @param entity
     */
    @Override
    public void update(Subject entity) {
        numLiving--;
    } 

    
    /** 
     * convert goal to JSON
     * @return JSONObject
     */
    @Override
    public JSONObject toJSON() {
        Map<String, Object> goalData = new HashMap<String, Object>();
        goalData.put("goal", "enemies");
        return new JSONObject(goalData);
    }

    
    /** 
     * convert goal to string
     * @return String
     */
    @Override
    public String simpleGoalToString() {
        return ":enemies ";
    }
}
