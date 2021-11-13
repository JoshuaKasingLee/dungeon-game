package dungeonmania.items;

import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.moving_entities.MercControlledState;
import dungeonmania.moving_entities.Mercenary;
import dungeonmania.player.Player;

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
