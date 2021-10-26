package dungeonmania;

public class InvincibilityPotion extends Item {
    public InvincibilityPotion (String id) {
        super(id, "InvincibilityPotion");
    }

    @Override
    public void activate(Character character) {
        super.activate(character);
        character.setCharacterState(new InvincibleState(character));
    }
}
