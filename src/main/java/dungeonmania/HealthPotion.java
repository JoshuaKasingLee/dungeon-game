package dungeonmania;

import dungeonmania.util.Position;

public class HealthPotion extends Item {

    public HealthPotion (Position position, Dungeon dungeon) {
        super(position, dungeon);
    }

    /**
     * regenerates given entity's health back to full
     */
    @Override
    public void activate(Character character) {
        super.activate(character);
        character.setHealth(Character.ORIGINAL_HEALTH);
    }

    @Override
    public String setType() {
        return "HealthArrow";
    }
}
