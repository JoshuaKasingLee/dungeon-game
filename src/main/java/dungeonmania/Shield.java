package dungeonmania;

public class Shield extends Item {

    // assume a shield completely deflects an enemy's attack
    public Shield (Dungeon dungeon) {
        super(null, dungeon);
        this.setUsesLeft(3);
    }

    @Override
    public String setType() {
        return "Shield";
    }
}