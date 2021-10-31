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
     * @return boolean
     */
    @Override
    public boolean isComplete() {
        return (numLiving == 0);
    }


    
    /** 
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
     * @param entity
     */
    @Override
    public void update(Subject entity) {
        numLiving--;
    } 

    
    /** 
     * @return JSONObject
     */
    @Override
    public JSONObject toJSON() {
        Map<String, Object> goalData = new HashMap<String, Object>();
        goalData.put("goal", "enemies");
        return new JSONObject(goalData);
    }

    
    /** 
     * @return String
     */
    @Override
    public String simpleGoalToString() {
        return ":enemies ";
    }
}
