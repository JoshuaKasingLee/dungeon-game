package dungeonmania;
import java.util.ArrayList;

public class AndGoal {
    ArrayList<GoalComponent> subgoals;
    public AndGoal() {
        this.subgoals = new ArrayList<GoalComponent>();
    }

    public boolean IsComplete() {
        return subgoals.stream().allMatch(goal -> (goal.IsComplete()));
    }

    public void addSubGoal(GoalComponent subgoal) {
        subgoals.add(subgoal);
    }
    
    public void removeSubGoal(GoalComponent subgoal) {
        subgoals.remove(subgoal);
    }
}
