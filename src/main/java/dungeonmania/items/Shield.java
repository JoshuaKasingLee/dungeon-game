package dungeonmania.items;

import dungeonmania.Dungeon;

public class Shield extends Item {
    public Shield (Dungeon dungeon) {
        super(null, dungeon);
        this.setUsesLeft(3);
    }

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "shield";
    }
}