package dungeonmania.items;

import dungeonmania.Dungeon;

public class Bow extends Item {
    public Bow (Dungeon dungeon) {
        super(null, dungeon);
        this.setUsesLeft(2);
    }
    
    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "bow";
    }
}
