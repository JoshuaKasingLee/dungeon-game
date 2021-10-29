package dungeonmania;

public class StandardState implements CharacterState {
    private Player character;
    private String type;

    public StandardState(Player character) {
        this.character = character;
        this.type = "Standard";
    }


    public void battleEnemy(Enemy enemy) {
        while (enemy.getHealth() > 0 && character.getHealth() > 0 && !enemy.isAlly()) {
            // assume original health points at start of round are used in battle
            int enemyOriginalHealth = enemy.getHealth();

            Inventory inventory = character.getInventory();

            // prioritise using sword over bow
            // assume can only use one weapon per round - to save weapons e.g. no point using bow if sword already instantly kills
            // equip weapon to fight enemy
            if (inventory.getItem("Sword") != null) {
                inventory.use("Sword", character);
                enemy.setHealth(0); // doesn't matter if enemy has armour (assume sword > armour)
            } else if (inventory.getItem("Bow") != null) {
                inventory.use("Bow", character);
                enemy.updateHealth(character);
                enemy.updateHealth(character);
            } else {
                enemy.updateHealth(character);
            }

            // prioritise using shield over armour
            // assume can only use one protection per round - to save protection e.g. no point using armour if shield already deflects all attack
            // equip protection to receive attack
            if (inventory.getItem("Shield") != null) {
                inventory.use("Shield", character);
                // do nothing
            } else if (inventory.getItem("Armour") != null) {
                inventory.use("Armour", character);
                int newHealth = character.getHealth() - ((enemyOriginalHealth * enemy.getAttackDamage()) / 20 );
                character.setHealth(newHealth);
            } else {
                int newHealth = character.getHealth() - ((enemyOriginalHealth * enemy.getAttackDamage()) / 10 );
                character.setHealth(newHealth);
            }

            // System.out.println(character.getHealth());
            // System.out.println(enemy.getHealth());
             
        }
    }

    public void updateState() {
        // remain in standard state
    }

    public String getType() {
        return type;
    }
    




}
