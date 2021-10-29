package dungeonmania;

import dungeonmania.util.Position;

public class Key extends Item {
    private int key;

    public Key (Position position, Dungeon dungeon, int key) {
        super(position, dungeon);
        this.key = key;
    }

    @Override
    public boolean correctKey(Door door) {
        if (getKey() == door.getKey()) {
            return true;
        }
        return false;
    }

    /**
     * @return int return the key
     */
    public int getKey() {
        return key;
    }

    @Override
    public String setType() {
        return "Key";
    }
}
