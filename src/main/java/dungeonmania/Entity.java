package dungeonmania;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import dungeonmania.util.Position;

public abstract class Entity {
    private Position position;
    private String id;
    private String type;
    private boolean isInteractable;
    private Gamemode gamemode;
    private Dungeon dungeon;

    public Entity(Position position, Dungeon dungeon) {
        this.position = position;
        this.type = setType();
        this.isInteractable = setIsInteractable();
    };

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
     * @return String return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public abstract String setType();

    /**
     * @return boolean return the isInteractable
     */
    public boolean isIsInteractable() {
        return isInteractable;
    }

    /**
     * @param isInteractable the isInteractable to set
     */
    public boolean setIsInteractable() {
        return false;
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

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }
    
    public List<Entity> getEntities() {
        return dungeon.getEntityList();
    }

    public void removeEntity() {
        dungeon.removeEntity(this);
    }
}

