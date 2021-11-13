package dungeonmania.items;

import dungeonmania.Dungeon;
import dungeonmania.player.Player;
import dungeonmania.util.Position;

public class HealthPotion extends Item {

    public HealthPotion (Position position, Dungeon dungeon) {
        super(position, dungeon);
        dungeon.addEntity(this);
    }

    /**
     * regenerates given entity's health back to full
     */
    @Override
    public void activate(Player player) {
        super.activate(player);
        player.setHealth(player.getDungeon().getGamemode().getStartingHealth());
    }
    
    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "health_potion";
    }
}
