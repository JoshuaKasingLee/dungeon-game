package dungeonmania;

public class Dungeon {

    private String dungeonName;
    private Gamemode gameMode;
    private String dungeonId;
    private List<Entity> entities;
    private List<Items> inventory;    // subject to change name or class to entity
    private List<Items> buildables; // subject to change name or class to entity
    private GoalCondition goals;

    public Dungeon(String dungeonName, String gameMode, String dungeonId) {
        this.dungeonName = dungeonName;
        this.gameMode = gameMode;
        this.dungeonId = dungeonId;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    

    /**
     * @return String return the dungeonName
     */
    public String getDungeonName() {
        return dungeonName;
    }

    /**
     * @param dungeonName the dungeonName to set
     */
    public void setDungeonName(String dungeonName) {
        this.dungeonName = dungeonName;
    }

    /**
     * @return String return the gameMode
     */
    public String getGameMode() {
        return gameMode;
    }

    /**
     * @param gameMode the gameMode to set
     */
    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * @return String return the dungeonId
     */
    public String getDungeonId() {
        return dungeonId;
    }

    /**
     * @param dungeonId the dungeonId to set
     */
    public void setDungeonId(String dungeonId) {
        this.dungeonId = dungeonId;
    }


    /**
     * @return List<Entity> return the entities
     */
    public List<Entity> getEntities() {
        return entities;
    }

    /**
     * @param entities the entities to set
     */
    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    /**
     * @return GoalCondition return the goals
     */
    public GoalCondition getGoals() {
        return goals;
    }

    /**
     * @param goals the goals to set
     */
    public void setGoals(GoalCondition goals) {
        this.goals = goals;
    }

}