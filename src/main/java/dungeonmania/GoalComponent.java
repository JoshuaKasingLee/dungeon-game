package dungeonmania;

import org.json.JSONObject;

public interface GoalComponent {

    public boolean isComplete();

    public abstract void tryToAttach(Subject entity);

    public abstract JSONObject toJSON();

    public abstract String simpleGoalToString();

}

