package dungeonmania;

public interface GoalComponent {

    public boolean isComplete();

    public abstract boolean tryToAttach(Subject entity);

}

