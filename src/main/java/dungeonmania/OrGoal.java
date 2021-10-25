package dungeonmania;
import java.util.ArrayList;
import java.util.List;

public class OrGoal extends CompositeGoal{
    private List<GoalComponent> subgoals;
    public OrGoal() {
        this.subgoals = new ArrayList<GoalComponent>();
    }

    @Override
    public boolean isComplete() {
        return subgoals.stream().anyMatch(goal -> (goal.isComplete()));
    }

}
