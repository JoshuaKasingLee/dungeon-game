package dungeonmania;

import dungeonmania.util.Position;

public class OneRing extends Item {
    public OneRing (Position position, Dungeon dungeon) {
        super(position, dungeon);
    }

    /**
     * regenerates character's health back to full
     * auto-used when character dies if exists in inventory
     */
    @Override
    public void activate(Player character) {
        super.activate(character);
        character.setHealth(Player.ORIGINAL_HEALTH);
    }

    @Override
    public String setType() {
        return "OneRing";
    }
}
