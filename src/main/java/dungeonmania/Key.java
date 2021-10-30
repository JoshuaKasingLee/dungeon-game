package dungeonmania;

import dungeonmania.util.Position;

public class Key extends Item {
    private int key;

    public Key (Position position, Dungeon dungeon, int key) {
        super(position, dungeon);
        this.key = key;
        dungeon.addEntity(this);
    }
    
    /** 
     * returns true if the key matches the given door, false if else
     * @param door
     * @return boolean
     */
    public boolean correctKey(Door door) {
        if (getKey() == door.getKey()) {
            return true;
        }
        return false;
    }

    // basic getters and setters

    /**
     * @return int return the key
     */
    public int getKey() {
        return key;
    }
    
    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "Key";
    }
}
