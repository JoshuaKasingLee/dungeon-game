package dungeonmania;

import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Subject {
    private Position position;
    private String id = newEntityId();
    private boolean interactable = false;
    private Gamemode gamemode;
    private List<Observer> goalObservers = new ArrayList<Observer>();
    private String type;
    private Dungeon dungeon;
    private static int entityIdCounter = 0; 
    
    public Entity(Position position, Dungeon dungeon) {
        this.position = position;
        this.dungeon = dungeon;
        this.type = setType();
    }

    
    /** 
     * generate unique id string
     * @return String
     */
    public static synchronized String newEntityId() {
        return String.valueOf(entityIdCounter++);
    }

    /** 
     * removes itself from the dungeon
     */
    public void removeEntity() {
        dungeon.removeEntity(this);
    }

    // observer pattern functions

    /** 
     * @param o
     */
    @Override
    public void attach(Observer o) {
        if (!goalObservers.contains(o)) {
            goalObservers.add(o);
        }
    }
    
    /** 
     * @param o
     */
    @Override
    public void detach(Observer o) {
            goalObservers.remove(o);
    }
    
    @Override
    public void notifyObservers() {
        for (Observer obs: goalObservers) {
            obs.update(this);
        } 
    }

    // basic getters and setters
    
    /** 
     * @return int
     */
    public int getXPosition() {
        return position.getX();
    }
    
    /** 
     * @return int
     */
    public int getYPosition() {
        return position.getY();
    }
    
    /** 
     * @return List<Entity>
     */
    public List<Entity> getEntities() {
        return dungeon.getEntities();
    }

    /**
     * @return Position return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @return String return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return boolean return the interactable
     */
    public boolean isInteractable() {
        return interactable;
    }

    /**
     * @param interactable the interactable to set
     */
    public void setInteractable(boolean interactable) {
        this.interactable = interactable;
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
     * @return List<Observer> return the goalObservers
     */
    public List<Observer> getGoalObservers() {
        return goalObservers;
    }

    /**
     * @param goalObservers the goalObservers to set
     */
    public void setGoalObservers(List<Observer> goalObservers) {
        this.goalObservers = goalObservers;
    }

    /**
     * @return String return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return Dungeon return the dungeon
     */
    public Dungeon getDungeon() {
        return dungeon;
    }

    /**
     * @param dungeon the dungeon to set
     */
    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    // abstract functions
    public abstract String setType();

}

