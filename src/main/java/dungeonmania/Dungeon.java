package dungeonmania;

import java.util.List;

import dungeonmania.util.Position;

import java.util.ArrayList;

public class Dungeon {

    private String dungeonName;
    private Gamemode gamemode;
    private String dungeonId;
    private List<Entity> entities;
    private Inventory inventory;    // subject to change name or class to entity
    private List<Entity> buildables; // subject to change name or class to entity
    private List<GoalComponent> simpleGoals;
    private GoalComponent overallGoal;

    public Dungeon(String dungeonName, String gamemodeString, String dungeonId) {
        this.dungeonName = dungeonName;
        initialiseGameMode(gamemodeString); 
        this.dungeonId = dungeonId;
    }

    public void initialiseGameMode(String gamemodeString) {
        if (gamemodeString.equals("Peaceful")) {
            gamemode = new Peaceful();
        }
        else if (gamemodeString.equals("Standard")) {
            gamemode = new Standard();
        }
        else if (gamemodeString.equals("Hard")) {
            gamemode = new Hard();
        }
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

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public void removeFrom(Position position) {

        // TODO:!!!!
    }

    public Player getPlayer() {
        for (Entity entity : entities) {
            if (entity instanceof Player) {
                return (Player) entity;
            }
        }
        return null;
    }

    /**
     * @return Inventory return the inventory
     */
    public Inventory getInventory() {
        return inventory;
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
     * @return Gamemode return the gamemode
     */
    public Gamemode getGamemode() {
        return gamemode;
    }

    /**
     * @param gamemode the gamemode to set
     */
    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
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
     * @return entities return the entities
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

}
