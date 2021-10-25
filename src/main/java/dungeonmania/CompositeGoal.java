package dungeonmania;

import java.util.List;

public abstract class CompositeGoal implements GoalComponent, Observer {
    private List<GoalComponent> subgoals;

    public void addSubgoal(GoalComponent subgoal) {
        subgoals.add(subgoal);
    }

    public void removeSubGoal(GoalComponent subgoal) {
        subgoals.remove(subgoal);
    }

    

}
