package dungeonmania;

public class InvisibilityPotion extends Item {

    public InvisibilityPotion (String id) {
        super(id, "InvisibilityPotion");
    }

    @Override
    public void activate(Character character) {
        super.activate(character);
        character.setCharacterState(new InvisibleState(character));
    }
    
}
