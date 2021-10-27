package dungeonmania;


public class Bow extends Item {
    // since bow is craftable, give null position
    public Bow (String id, Dungeon dungeon) {
        super(null, id, "Bow", dungeon);
        this.setUsesLeft(2);
    }

    
}
