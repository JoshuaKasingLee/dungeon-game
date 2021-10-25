package dungeonmania;

import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Subject {
    private Position position;
    private String id = newEntityId();
    private boolean isInteractable = false;
    private Gamemode gamemode;
    private List<Observer> goalObservers = new ArrayList<Observer>();

    private static int entityIdCounter = 0; 
    public static synchronized String newEntityId() {
        return String.valueOf(entityIdCounter++);
    }

    
    public Entity(Position position, Gamemode gamemode) {
        this.position = position;
        this.gamemode = gamemode;
        
    }

    @Override
    public void attach(Observer o) {
        if (!goalObservers.contains(o)) {
            goalObservers.add(o);
        }
    }

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

    /*
     * @return Position return the position
     */
    public Position getPosition() {
        return position;
    }

    /*
    * @param position the position to set
    */
    public void setPosition(Position position) {
        this.position = position;
    }

    /*
    * @return String return the id
    */
    public String getId() {
        return id;
    }

    /*
    * @param id the id to set
    */
    public void setId(String id) {
        this.id = id;
    }

    /*
    * @return String return the type
    */
    public String getType() {
        return type;
    }

    /*
    * @param type the type to set
    */
    public void setType(String type) {
        this.type = type;
    }

    /*
     * @return boolean return the isInteractable
     */
    public boolean isIsInteractable() {
        return isInteractable;
    }

    /*
     * @param isInteractable the isInteractable to set
     */
    public void setIsInteractable(boolean isInteractable) {
        this.isInteractable = isInteractable;
    }

    /*
     * @return Gamemode return the gamemode
     */
    public Gamemode getGamemode() {
        return gamemode;
    }

    /*
     * @param gamemode the gamemode to set
     */
    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
    }

}

