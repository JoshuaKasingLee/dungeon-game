package dungeonmania;

import dungeonmania.util.Position;

public class InvincibilityPotion extends Item {
    public InvincibilityPotion (Position position, String id, Dungeon dungeon) {
        super(position, id, "InvincibilityPotion", dungeon);
    }

    @Override
    public void activate(Character character) {
        super.activate(character);
        character.setCharacterState(new InvincibleState(character));
    }
}
