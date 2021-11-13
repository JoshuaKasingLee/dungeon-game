package dungeonmania.goals;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import dungeonmania.static_entities.Switch;

public class BoulderOnSwitchGoal implements GoalComponent, Observer  {
    private HashMap<String,Boolean> arePressed;
    public BoulderOnSwitchGoal() {
        arePressed = new HashMap<String, Boolean>();
    }

    
    /** 
     * check if goal is complete if all switches are triggered
     * @return boolean
     */
    @Override
    public boolean isComplete() {
        for (Boolean isPressed: arePressed.values()) {
            if (!isPressed) {
                return false;
            }
        }
        return true;
    }

    
    /** 
     * update the unpressed switches amount
     * @param entity
     */
    @Override
    public void update(Subject entity) {
        Switch currSwitch = (Switch) entity;
        String switchId = currSwitch.getId();
        if (currSwitch.hasBoulder()) {
            arePressed.put(switchId, true);
        } else {
            arePressed.put(switchId, false);
        }
    }

    
    /** 
     * attempt to attach an entity to this goal
     * @param entity
     */
    @Override
    public void tryToAttach(Subject entity) {
        if (entity instanceof Switch) {
            Switch currSwitch = (Switch) entity;
            arePressed.put(currSwitch.getId(), false);
            entity.attach(this);
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
