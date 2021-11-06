package dungeonmania;


public class Sceptre extends Item {

    public Sceptre(Dungeon dungeon) {
        super(null, dungeon);
    }

    @Override
    public void activate(Player player) {
        super.activate(player);
        
    }

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "sceptre";
    }
    
}
