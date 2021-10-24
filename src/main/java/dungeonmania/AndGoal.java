package dungeonmania;
import java.util.ArrayList;

public class AndGoal {
    ArrayList<GoalComponent> subgoals;
    public AndGoal() {
        this.subgoals = new ArrayList<GoalComponent>();
    }

    public boolean isComplete() {
        return subgoals.stream().allMatch(goal -> (goal.isComplete()));
    }

    public void addSubGoal(GoalComponent subgoal) {
        subgoals.add(subgoal);
    }
    
    public void removeSubGoal(GoalComponent subgoal) {
        subgoals.remove(subgoal);
    }
}
