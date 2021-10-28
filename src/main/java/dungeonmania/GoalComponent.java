package dungeonmania;

import org.json.JSONObject;

public interface GoalComponent {

    public boolean isComplete();

    public abstract boolean tryToAttach(Subject entity);

    public abstract JSONObject toJSON();

}

