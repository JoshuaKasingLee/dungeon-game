package dungeonmania;


public class Armour extends Item {
    public static final int DURABILITY = 2;

    public Armour(Dungeon dungeon, int usesLeft) {
        super(null, dungeon);
        this.setUsesLeft(usesLeft);
    }

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "armour";
    }
}
