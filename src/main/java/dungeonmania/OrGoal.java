package dungeonmania;
import java.util.ArrayList;

public class OrGoal {
    ArrayList<GoalComponent> subgoals;
    public OrGoal() {
        this.subgoals = new ArrayList<GoalComponent>();
    }

    public boolean isComplete() {
        return subgoals.stream().anyMatch(goal -> (goal.isComplete()));
    }

    public void addSubGoal(GoalComponent subgoal) {
        subgoals.add(subgoal);
    }
    
    public void removeSubGoal(GoalComponent subgoal) {
        subgoals.remove(subgoal);
    }
}
