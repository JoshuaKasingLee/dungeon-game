package dungeonmania;

import dungeonmania.util.Position;

public class InvincibilityPotion extends Item {
    public InvincibilityPotion (Position position, Dungeon dungeon) {
        super(position, dungeon);
        dungeon.addEntity(this);
    }

    @Override
    public void activate(Player character) {
        super.activate(character);
        character.setCharacterState(new InvincibleState(character));
    }

    @Override
    public String setType() {
        return "InvincibilityPotion";
    }
}
