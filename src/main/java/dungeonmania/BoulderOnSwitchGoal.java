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
     * check if goal is complete if all switches are triggered
     * @return boolean
     */
    @Override
    public boolean isComplete() {
        return (unpressedSwitches == 0);
    }

    
    /** 
     * update the unpressed switches amount
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
     * attempt to attach an entity to this goal
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
     * convert goal to JSON
     * @return JSONObject
     */
    @Override
    public JSONObject toJSON() {
        Map<String, Object> goalData = new HashMap<String, Object>();
        goalData.put("goal", "boulders");
        return new JSONObject(goalData);
    }

    
    /** 
     * goal convert to string
     * @return String
     */
    @Override
    public String simpleGoalToString() {
        return ":boulders ";
    }
}
