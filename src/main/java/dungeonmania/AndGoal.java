package dungeonmania;
import java.util.ArrayList;
import java.util.List;

public class AndGoal extends CompositeGoal {
    private List<GoalComponent> subgoals;
    public AndGoal() {
        this.subgoals = new ArrayList<GoalComponent>();
    }

    @Override
    public boolean isComplete() {
        return subgoals.stream().allMatch(goal -> (goal.isComplete()));
    }

}
