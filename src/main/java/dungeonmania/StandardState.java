package dungeonmania;

public class StandardState implements CharacterState {
    private Player player;
    private String type;

    public StandardState(Player player) {
        this.player = player;
        this.type = "Standard";
    }
    
    /** 
     * simulate battle between player and given enemy
     * @param enemy
     */
    public void battleEnemy(Enemy enemy) {
        // update mercenary position if they are within battle radius
        for (Entity e : player.getDungeon().getEntities()) {
            if (e instanceof Mercenary) {
                Mercenary m = (Mercenary) e;
                if (!m.isAlly() && m.withinPlayerRadius(player)) {
                    m.updatePosition();
                }
            }
        }

        // commence battle until an entity loses
        while (enemy.getHealth() > 0 && player.getHealth() > 0 && !enemy.isAlly()) {
            // assume original health points at start of round are used in battle
            int enemyOriginalHealth = enemy.getHealth();
            Inventory inventory = player.getInventory();

            // equip weapon to fight enemy
            if (inventory.getItem("sword") != null) {
                inventory.use("sword", player);
                enemy.setHealth(0);
            } else if (inventory.getItem("bow") != null) {
                inventory.use("bow", player);
                enemy.updateHealth(player);
                enemy.updateHealth(player);
            } else {
                enemy.updateHealth(player);
            }

            // if player has allies, contribute their health deductions
            for (Entity e : player.getDungeon().getEntities()) {
                if (e instanceof Mercenary) {
                    Mercenary m = (Mercenary) e;
                    if (m.isAlly()) {
                        enemy.updateHealth(m);
                    }
                }
            }
           
            // equip protection to receive attack
            if (player.getDungeon().getGamemode().isBattle()) {
                if (inventory.getItem("shield") != null) {
                    inventory.use("shield", player);
                } else if (inventory.getItem("armour") != null) {
                    inventory.use("armour", player);
                    int newHealth = player.getHealth() - ((enemyOriginalHealth * enemy.getAttackDamage()) / 20 );
                    player.setHealth(newHealth);
                } else {
                    int newHealth = player.getHealth() - ((enemyOriginalHealth * enemy.getAttackDamage()) / 10 );
                    player.setHealth(newHealth);
                }
            }
        }
    }

    /** 
     * player's state remains as is
     */
    public void updateState() {
    }

    /** 
     * @return String
     */
    public String getType() {
        return type;
    }
}
