package dungeonmania;


public class HealthPotion extends Item {

    public HealthPotion (String id) {
        super(id, "HealthPotion");
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
