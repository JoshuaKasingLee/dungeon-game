package dungeonmania.items;

import dungeonmania.Dungeon;
import dungeonmania.player.InvisibleState;
import dungeonmania.player.Player;
import dungeonmania.util.Position;

public class InvisibilityPotion extends Item {

    public InvisibilityPotion (Position position, Dungeon dungeon) {
        super(position, dungeon);
        dungeon.addEntity(this);
    }

    /** 
     * changes player state to invincible
     * @param player
     */
    @Override
    public void activate(Player player) {
        super.activate(player);
        player.setCharacterState(new InvisibleState(player));
    }
    
    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "invisibility_potion";
    }
}
