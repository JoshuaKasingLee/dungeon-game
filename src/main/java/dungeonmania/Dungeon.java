package dungeonmania;

import java.util.List;

import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dungeon {

    private String dungeonName;
    private Gamemode gameMode;
    private String dungeonId;
    private List<Entity> entities;
    private List<Entity> inventory;    // subject to change name or class to entity
    private List<Entity> buildables; // subject to change name or class to entity
    private List<GoalComponent> simpleGoals;
    private GoalComponent overallGoal;

    public Dungeon(String dungeonName, String gameMode, String dungeonId) {
        this.dungeonName = dungeonName;
        this.gameMode = gameMode;
        this.dungeonId = dungeonId;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
        conditionalAttach(entity);

    }

    public void conditionalAttach(Entity entity) {
        for (GoalComponent goal : simpleGoals) {
            goal.tryToAttach(entity);
        }
    }
    
    public boolean isEntityInDungeon(Treasure treasure) {
        return entities.stream().anyMatch(entity -> (entity instanceof Treasure));
    }

    public void addSimpleGoals(GoalComponent simpleGoal) {
        simpleGoals.add(simpleGoal);
    }
    
    public List<Entity> getEntities(Position position) {
        List<Entity> entitiesAtPos = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getPosition().equals(position)) {
                entitiesAtPos.add(entity);
            }
        }
        return entitiesAtPos;
    }

    public Door getDoor(Position position) {
        List<Entity> entityList = getEntities(position);
        for (Entity entity : entityList) {
            if (entity instanceof Door) {
                return (Door) entity;
            }
        }
         return null;
    }

    public void addTo(Entity entity, Position position) {
        entity.setPosition(position);
        addEntity(entity);
    }

    public void removeFrom(Entity entity) {
        entities.remove(entity);
    }

    public Player getPlayer() {
        for (Entity entity : entities) {
            if (entity instanceof Player) {
                return entity;
            }
        }
        return null;
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
     * @return Gamemode return the gameMode
     */
    public Gamemode getGameMode() {
        return gameMode;
    }

    /**
     * @param gameMode the gameMode to set
     */
    public void setGameMode(Gamemode gameMode) {
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
     * @return List<GoalComponent> return the simpleGoals
     */
    public List<GoalComponent> getSimpleGoals() {
        return simpleGoals;
    }

    /**
     * @param simpleGoals the simpleGoals to set
     */
    public void setSimpleGoals(List<GoalComponent> simpleGoals) {
        this.simpleGoals = simpleGoals;
    }

    /**
     * @return GoalComponent return the overallGoal
     */
    public GoalComponent getOverallGoal() {
        return overallGoal;
    }

    /**
     * @param overallGoal the overallGoal to set
     */
    public void setOverallGoal(GoalComponent overallGoal) {
        this.overallGoal = overallGoal;
    }

    /**
     * @return List<Entity> return the inventory
     */
    public List<Entity> getInventory() {
        return inventory;
    }

}