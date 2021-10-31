package dungeonmania;

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