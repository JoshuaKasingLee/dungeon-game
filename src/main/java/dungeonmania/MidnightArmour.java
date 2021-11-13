package dungeonmania;


public class MidnightArmour extends Armour {
    public final static int ADDED_ATTACK_DAMAGE = 2;

    public MidnightArmour(Dungeon dungeon) {
        super(dungeon, Armour.DURABILITY);
    }

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "midnight_armour";
    }
    
}
