package dungeonmania;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class ExitGoal implements GoalComponent, Observer  {

    private boolean onExit; 
    public ExitGoal() {
        onExit = false;
    }
    
    /** 
     * check if goal is complete
     * @return boolean
     */
    @Override
    public boolean isComplete() {
        return onExit;
    }

    
    /** 
     * try to attach entity to goal
     * @param entity
     */
    @Override
    public void tryToAttach(Subject entity) {

        if (entity instanceof Exit) {
            
            entity.attach(this);
        }
    }
 
    
    /** 
     * update whether player is on exit
     * @param entity
     */
    @Override
    public void update(Subject entity) {
        Exit currExit = (Exit) entity;
        if (currExit.hasPlayer()) {
            onExit = true;
        } else {
            onExit = false;
        }
    }

    
    /** 
     * convert goal to JSON
     * @return JSONObject
     */
    @Override
    public JSONObject toJSON() {
        Map<String, Object> goalData = new HashMap<String, Object>();
        goalData.put("goal", "exit");
        return new JSONObject(goalData);
    }

    
    /** 
     * convert goal to string
     * @return String
     */
    @Override
    public String simpleGoalToString() {
        return ":exit ";
    }

}
