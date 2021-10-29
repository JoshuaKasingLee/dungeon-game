package dungeonmania;

import dungeonmania.util.Position;

public class InvisibilityPotion extends Item {

    public InvisibilityPotion (Position position, Dungeon dungeon) {
        super(position, dungeon);
    }

    @Override
    public void activate(Character character) {
        super.activate(character);
        character.setCharacterState(new InvisibleState(character));
    }
    
    @Override
    public String setType() {
        return "InvisibilityPotion";
    }
}
