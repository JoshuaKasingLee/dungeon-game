package dungeonmania;

import dungeonmania.util.Position;

public class OneRing extends Item {
    public OneRing (Position position, Dungeon dungeon) {
        super(position, dungeon);
        dungeon.addEntity(this);
    }

    /** 
     * regenerates player's health back to full
     * @param player
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
        return "OneRing";
    }
}
