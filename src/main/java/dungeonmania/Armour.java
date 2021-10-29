package dungeonmania;


public class Armour extends Item {
    public static final int DURABILITY = 2;

    // assume armour can ONLY be collected from enemies -> important for id naming

    public Armour(Dungeon dungeon) {
        super(null, dungeon);
        this.setUsesLeft(DURABILITY);
    }

    @Override
    public String setType() {
        return "Armour";
    }
}
