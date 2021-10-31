package dungeonmania;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeGoal implements GoalComponent {
    private List<GoalComponent> subgoals;

    public CompositeGoal() {
        this.subgoals = new ArrayList<GoalComponent>();
    }

    
    /** 
     * add a subgoal to the composite
     * @param subgoal
     */
    public void addSubgoal(GoalComponent subgoal) {
        subgoals.add(subgoal);
    }

    
    /** 
     * remove a subgoal from the composite
     * @param subgoal
     */
    public void removeSubGoal(GoalComponent subgoal) {
        subgoals.remove(subgoal);
    }

    
    /** 
     * convert goal to string
     * @return String
     */
    @Override
    public String simpleGoalToString() {
        return "";
    }

    
    /** 
     * try to attach entity to goal
     * @param entity
     */
    @Override
    public void tryToAttach(Subject entity) {
    }



    /**
     * get the list of subgoals
     * @return List<GoalComponent> return the subgoals
     */
    public List<GoalComponent> getSubgoals() {
        return subgoals;
    }



}
