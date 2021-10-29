package dungeonmania;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeGoal implements GoalComponent {
    private List<GoalComponent> subgoals;

    public CompositeGoal() {
        this.subgoals = new ArrayList<GoalComponent>();
    }

    public void addSubgoal(GoalComponent subgoal) {
        subgoals.add(subgoal);
    }

    public void removeSubGoal(GoalComponent subgoal) {
        subgoals.remove(subgoal);
    }

    @Override
    public String simpleGoalToString() {
        return "";
    }

    @Override
    public void tryToAttach(Subject entity) {
    }



    /**
     * @return List<GoalComponent> return the subgoals
     */
    public List<GoalComponent> getSubgoals() {
        return subgoals;
    }



}
