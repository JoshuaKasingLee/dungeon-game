package dungeonmania;

import dungeonmania.util.Position;

public abstract class Entity {
    private Position position;
    private String id;
    private String type;
    private Dungeon dungeon;
    private boolean isInteractable;

    public Entity(Position position, Dungeon dungeon) {
        this.position = position;
        this.type = setType();
        this.dungeon = dungeon;
        this.isInteractable = false;
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

    /**
     * @return boolean return the isInteractable
     */
    public boolean isIsInteractable() {
        return isInteractable;
    }

    /**
     * @param isInteractable the isInteractable to set
     */
    public void setIsInteractable(boolean isInteractable) {
        this.isInteractable = isInteractable;
    }

}
