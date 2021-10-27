package dungeonmania;

public class Armour extends Item {
    public static final int DURABILITY = 2;

    // assume armour can ONLY be collected from enemies -> important for id naming

    public Armour (String id) {
        super(id, "Armour");
        this.setUsesLeft(DURABILITY);
    }

}
