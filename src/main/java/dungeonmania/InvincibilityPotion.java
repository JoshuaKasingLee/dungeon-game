package dungeonmania;

import dungeonmania.util.Position;

public class InvincibilityPotion extends Item {
    public InvincibilityPotion (Position position, Dungeon dungeon) {
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
        if (player.getDungeon().getGamemode().getInvincible()) {
            player.setCharacterState(new InvincibleState(player));
        }
    }
    
    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "invincibility_potion";
    }
}
