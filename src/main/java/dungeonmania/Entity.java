package dungeonmania;

import dungeonmania.util.Position;



public abstract class Entity {
    private Position position;
    private String id;
    private String type;
    // private boolean isInteractable = false;
    // private Gamemode gamemode;

    public Entity(Position position, String id, String type) {
        this.position = position;
        this.id = id;
        this.type = type;
        // this.isInteractable = false;
        // this.gamemode = gamemode;
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
    public void setType(String type) {
        this.type = type;
    }

    // /**
    //  * @return boolean return the isInteractable
    //  */
    // public boolean isIsInteractable() {
    //     return isInteractable;
    // }

    // /**
    //  * @param isInteractable the isInteractable to set
    //  */
    // public void setIsInteractable(boolean isInteractable) {
    //     this.isInteractable = isInteractable;
    // }

    // /**
    //  * @return Gamemode return the gamemode
    //  */
    // public Gamemode getGamemode() {
    //     return gamemode;
    // }

    // /**
    //  * @param gamemode the gamemode to set
    //  */
    // public void setGamemode(Gamemode gamemode) {
    //     this.gamemode = gamemode;
    // }

}
