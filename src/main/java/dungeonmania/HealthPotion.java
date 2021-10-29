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
    public void activate(Player character) {
        super.activate(character);
        character.setHealth(Player.ORIGINAL_HEALTH);
    }

    @Override
    public String setType() {
        return "HealthPotion";
    }
}
