package dungeonmania;
import java.util.ArrayList;
import java.util.List;

public class OrGoal extends CompositeGoal implements GoalComponent, Observer {
    private List<GoalComponent> subgoals;
    public OrGoal() {
        this.subgoals = new ArrayList<GoalComponent>();
    }

    @Override
    public boolean isComplete() {
        return subgoals.stream().anyMatch(goal -> (goal.isComplete()));
    }

}
