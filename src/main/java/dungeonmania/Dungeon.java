package dungeonmania;

import java.util.List;

import dungeonmania.util.Position;

import java.util.ArrayList;

import java.util.Random;

public class Dungeon {

    private String dungeonName;
    private Gamemode gamemode;
    private String dungeonId;
    private List<Entity> entities = new ArrayList<Entity>();
    private Inventory inventory = new Inventory();    // subject to change name or class to entity
    private List<GoalComponent> simpleGoals = new ArrayList<GoalComponent>();
    private GoalComponent overallGoal;
    private int counter;

    public Dungeon(String dungeonName, String gamemodeString, String dungeonId) {
        this.dungeonName = dungeonName;
        initialiseGameMode(gamemodeString); 
        this.dungeonId = dungeonId;
        this.counter = 0;
    }

    

    public void tickCounter() {
        counter++;
        spiderSpawn();
    }

    public void spiderSpawn() {
        if (getSpawnTimer() == 0) {
            return;
        } 

        if (countSpiders() >= 5) {
            return;
        }
                
        if (counter % getSpawnTimer() == 0) {
            Position randPos = randomSpiderPosition();
            while (getEntities(randPos).stream().anyMatch(x -> x.getType().equals("boulder"))) {
                randPos = randomSpiderPosition();
            }
            new Spider(randPos, this);
        }
    }

    public Position randomSpiderPosition() {
        Random number = new Random();
        int bound = 20;
        int xRandom = number.nextInt(bound);
        int yRandom = number.nextInt(bound);
        Position randPos = new Position(xRandom, yRandom);
        return randPos;
    }

    public long countSpiders() {
        return entities.stream().filter(x -> x instanceof Spider).count();
    }

    public int getSpawnTimer() {
        return getGamemode().getSpawnTimer();
    }
    
    /** 
     * @param gamemodeString
     */
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

    
    /** 
     * @param entity
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
        conditionalAttach(entity);
    }

    
    /** 
     * @param entity
     */
    public void conditionalAttach(Entity entity) {
        for (GoalComponent goal : entity.getDungeon().getSimpleGoals()) {
            goal.tryToAttach(entity);
        }
    }
    
    
    /** 
     * @param treasure
     * @return boolean
     */
    public boolean isEntityInDungeon(Treasure treasure) {
        return entities.stream().anyMatch(entity -> (entity instanceof Treasure));
    }

    
    /** 
     * @param simpleGoal
     */
    public void addSimpleGoals(GoalComponent simpleGoal) {
        simpleGoals.add(simpleGoal);
    }
    
    
    /** 
     * @param position
     * @return List<Entity>
     */
    public List<Entity> getEntities(Position position) {
        List<Entity> entitiesAtPos = new ArrayList<Entity>();
        for (Entity entity : entities) {
            if (entity.getPosition().equals(position)) {
                entitiesAtPos.add(entity);
            }
        }
        return entitiesAtPos;
    }

    
    /** 
     * @param position
     * @return Door
     */
    public Door getDoor(Position position) {
        List<Entity> entityList = getEntities(position);
        for (Entity entity : entityList) {
            if (entity instanceof Door) {
                return (Door) entity;
            }
        }
         return null;
    }

    
    /** 
     * @param entity
     * @param position
     */
    public void addTo(Entity entity, Position position) {
        entity.setPosition(position);
        addEntity(entity);
    }

    
    /** 
     * @param entity
     */
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    
    /** 
     * @param position
     */
    public void removeFrom(Position position) {
        List<Entity> entitiesCopy = new ArrayList<Entity>(entities);
        for (Entity entity : entitiesCopy) {
            if (entity.getPosition().equals(position)) {
                removeEntity(entity);
            }
        }



    }

    
    /** 
     * @return Player
     */
    public Player getPlayer() {
        for (Entity entity : entities) {
            if (entity instanceof Player) {
                return (Player) entity;
            }
        }
        return null;
    }

    
    /** 
     * @param item
     */
    public void moveToInventory(Item item) {
        inventory.add(item);
        entities.remove(item);
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


    /**
     * @return int return the counter
     */
    public int getCounter() {
        return counter;
    }

    /**
     * @param counter the counter to set
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }

}
