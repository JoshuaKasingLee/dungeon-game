package dungeonmania.player;

import dungeonmania.Entity;
import dungeonmania.items.Sword;
import dungeonmania.moving_entities.Assassin;
import dungeonmania.moving_entities.Enemy;
import dungeonmania.moving_entities.Hydra;
import dungeonmania.moving_entities.Mercenary;

public class StandardState implements PlayerState {
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
            if (inventory.getItem("anduril") != null) {
                inventory.use("anduril", player);
                if (enemy instanceof Assassin || enemy instanceof Hydra) {
                    enemy.setHealth(enemy.getHealth() - Sword.ATTACK_DAMAGE*3);
                } else {
                    enemy.setHealth(enemy.getHealth() - Sword.ATTACK_DAMAGE);
                }
            } else if (inventory.getItem("sword") != null) {
                inventory.use("sword", player);
                int swordDamage = Sword.ATTACK_DAMAGE;
                if (enemy.getArmour() > 0) {
                    swordDamage = swordDamage / 2;
                }
                if (enemy instanceof Hydra) {
                    Hydra h = (Hydra) enemy;
                    if (h.attackSuccess()) {
                        enemy.setHealth(enemy.getHealth() - swordDamage);
                    } else {
                        enemy.setHealth(enemy.getHealth() + swordDamage);
                    }
                } else {
                    enemy.setHealth(enemy.getHealth() - swordDamage);
                }
            } else if (inventory.getItem("bow") != null) {
                inventory.use("bow", player);
                enemy.updateHealth(player);
                enemy.updateHealth(player);
            } else if (inventory.getItem("midnight_armour") != null) {
                inventory.use("midnight_armour", player);
                player.setAttackDamage(Player.CHARACTER_ATTACK_DAMAGE + 2);
                enemy.updateHealth(player);
                player.setAttackDamage(Player.CHARACTER_ATTACK_DAMAGE + 2);
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
                } else if (inventory.getItem("midnight_armour") != null) {
                    inventory.use("midnight_armour", player);
                    int newHealth = player.getHealth() - ((enemyOriginalHealth * enemy.getAttackDamage()) / 20 );
                    player.setHealth(newHealth);
                }else {
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
