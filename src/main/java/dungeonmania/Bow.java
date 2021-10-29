package dungeonmania;


public class Bow extends Item {
    // since bow is craftable, give null position
    public Bow (Dungeon dungeon) {
        super(null, dungeon);
        this.setUsesLeft(2);
    }

    @Override
    public String setType() {
        return "Bow";
    }
}
