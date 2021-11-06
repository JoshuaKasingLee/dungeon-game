package dungeonmania;


public class Sceptre extends Item {

    public Sceptre(Dungeon dungeon) {
        super(null, dungeon);
    }

    @Override
    public void activate(Player player) {
        super.activate(player);
        for (Entity e : player.getDungeon().getEntities()) {
            if (e instanceof Mercenary) {
                Mercenary m = (Mercenary) e;
                m.setMercenaryState(new MercControlledState(m));
            }
        }
    }

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "sceptre";
    }
    
}
