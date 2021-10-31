package dungeonmania;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class BoulderOnSwitchGoal implements GoalComponent, Observer  {
    private int unpressedSwitches;
    public BoulderOnSwitchGoal() {
        unpressedSwitches = 0;
    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isComplete() {
        return (unpressedSwitches == 0);
    }

    
    /** 
     * @param entity
     */
    @Override
    public void update(Subject entity) {
        Switch currSwitch = (Switch) entity;
        if (currSwitch.hasBoulder()) {
            unpressedSwitches--;
        } else {
            unpressedSwitches++;
        }
    }

    
    /** 
     * @param entity
     */
    @Override
    public void tryToAttach(Subject entity) {
        if (entity instanceof Switch) {
            entity.attach(this);
            unpressedSwitches++;
        }

    }

    
    /** 
     * @return JSONObject
     */
    @Override
    public JSONObject toJSON() {
        Map<String, Object> goalData = new HashMap<String, Object>();
        goalData.put("goal", "boulders");
        return new JSONObject(goalData);
    }

    
    /** 
     * @return String
     */
    @Override
    public String simpleGoalToString() {
        return ":boulders ";
    }
}
