package dungeonmania;

import java.util.List;

public abstract class CompositeGoal {
    private List<GoalComponent> subgoals;

    public abstract boolean isComplete();

    public void addSubgoal(GoalComponent subgoal) {
        subgoals.add(subgoal);
    }

    public void removeSubGoal(GoalComponent subgoal) {
        subgoals.remove(subgoal);
    }

    

}
