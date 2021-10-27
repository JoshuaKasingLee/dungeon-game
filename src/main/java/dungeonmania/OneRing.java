package dungeonmania;

public class OneRing extends Item {
    public OneRing (String id) {
        super(id, "OneRing");
    }

    /**
     * regenerates character's health back to full
     * auto-used when character dies if exists in inventory
     */
    @Override
    public void activate(Character character) {
        super.activate(character);
        character.setHealth(Character.ORIGINAL_HEALTH);
    }
}
