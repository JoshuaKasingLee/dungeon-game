package dungeonmania;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class EnemiesAndSpawnerGoal implements GoalComponent, Observer  {
    private int numLiving;

    public EnemiesAndSpawnerGoal() {
        numLiving = 0;
    }
    @Override
    public boolean IsComplete() {
        return (numLiving == 0);
    }


    @Override
    public boolean tryToAttach(Subject entity) {
        if (entity instanceof Spider || entity instanceof Mercenary || entity instanceof ZombieToast || entity instanceof ZombieToastSpawner) {
            entity.attach(this);
            numLiving++;
        }
    }

    @Override
    public void update(Subject entity) {
        numLiving--;
    } 

    @Override
    public JSONObject toJSON() {
        Map<String, Object> goalData = new HashMap<String, Object>();
        goalData.put("goal", "enemies");
        return new JSONObject(goalData);
    }

    @Override
    public String simpleGoalToString() {
        return ":enemies ";
    }
}
