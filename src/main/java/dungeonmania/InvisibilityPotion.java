package dungeonmania;

import dungeonmania.util.Position;

public class InvisibilityPotion extends Item {

    public InvisibilityPotion (Position position, String id, Dungeon dungeon) {
        super(position, id, "InvisibilityPotion", dungeon);
    }

    @Override
    public void activate(Character character) {
        super.activate(character);
        character.setCharacterState(new InvisibleState(character));
    }
    
}
