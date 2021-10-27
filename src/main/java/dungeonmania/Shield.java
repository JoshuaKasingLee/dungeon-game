package dungeonmania;

public class Shield extends Item {

    // assume a shield completely deflects an enemy's attack
    public Shield (String id, Dungeon dungeon) {
        super(null, id, "Shield", dungeon);
        this.setUsesLeft(3);
    }

    
}