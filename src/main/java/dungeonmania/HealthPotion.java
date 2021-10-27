package dungeonmania;

import dungeonmania.util.Position;

public class HealthPotion extends Item {

    public HealthPotion (Position position, String id, Dungeon dungeon) {
        super(position, id, "HealthPotion", dungeon);
    }

    /**
     * regenerates given entity's health back to full
     */
    @Override
    public void activate(Character character) {
        super.activate(character);
        character.setHealth(Character.ORIGINAL_HEALTH);
    }
}
