package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class BattleTest {
    @Test
    public void testStandardBattleSpiderHealth() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        PlayerState state = character.getPlayerState();
        assertEquals(state.getType(), "Standard");
       
        // spider battle
        Spider spider = new Spider(new Position(0, 0), character.getDungeon());
        int expectedCharHealth = character.getHealth() - ((spider.getHealth() * spider.getAttackDamage()) / 10);
        int expectedEnemyHealth = spider.getHealth() - ((character.getHealth() * character.getAttackDamage()) / 5);
        state.battleEnemy(spider);
        assertTrue(character.getHealth() == expectedCharHealth);
        assertEquals(spider.getHealth(), expectedEnemyHealth);
    }

    @Test
    public void testStandardBattleZombieHealth() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        PlayerState state = character.getPlayerState();
        assertEquals(state.getType(), "Standard");

        // zombietoast battle - no armour
        ZombieToast zombie = new ZombieToast(new Position(0, 0), character.getDungeon());
        zombie.giveArmour(0);
        // expect 2 rounds to kill zombie
        int expectedCharHealth1 = character.getHealth() - ((zombie.getHealth() * zombie.getAttackDamage()) / 10);
        int expectedEnemyHealth1 = zombie.getHealth() - ((character.getHealth() * character.getAttackDamage()) / 5);
        int expectedCharHealth2 = expectedCharHealth1 - ((expectedEnemyHealth1 * zombie.getAttackDamage()) / 10);
        int expectedEnemyHealth2 = expectedEnemyHealth1 - ((expectedCharHealth1 * character.getAttackDamage()) / 5);
        state.battleEnemy(zombie);
        assertEquals(character.getHealth(), expectedCharHealth2);
        assertEquals(zombie.getHealth(), expectedEnemyHealth2);
    }

    @Test
    public void testStandardBattleMercenaryHealth() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        PlayerState state = character.getPlayerState();
        assertEquals(state.getType(), "Standard");

        // mercenary battle - no armour
        Mercenary merc = new Mercenary(new Position(0, 0), character.getDungeon());
        merc.giveArmour(0);
        // expect 3 rounds to kill mercenary
        int expectedCharHealth1 = character.getHealth() - ((merc.getHealth() * merc.getAttackDamage()) / 10);
        int expectedEnemyHealth1 = merc.getHealth() - ((character.getHealth() * character.getAttackDamage()) / 5);
        int expectedCharHealth2 = expectedCharHealth1 - ((expectedEnemyHealth1 * merc.getAttackDamage()) / 10);
        int expectedEnemyHealth2 = expectedEnemyHealth1 - ((expectedCharHealth1 * character.getAttackDamage()) / 5);
        int expectedCharHealth3 = expectedCharHealth2 - ((expectedEnemyHealth2 * merc.getAttackDamage()) / 10);
        int expectedEnemyHealth3 = expectedEnemyHealth2 - ((expectedCharHealth2 * character.getAttackDamage()) / 5);
        state.battleEnemy(merc);
        assertEquals(character.getHealth(), expectedCharHealth3);
        assertEquals(merc.getHealth(), expectedEnemyHealth3);
    }

    @Test
    public void testStandardBattleSword() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        PlayerState state = character.getPlayerState();
        assertEquals(state.getType(), "Standard");
        Inventory inv = character.getInventory();
        Sword s = new Sword(new Position(0, 0), character.getDungeon());
        inv.add(s);

        // mercenary battle - no armour
        Mercenary merc = new Mercenary(new Position(0, 0), character.getDungeon());
        merc.giveArmour(0);
        // expect instant kill mercenary 
        int expectedCharHealth = character.getHealth() - ((merc.getHealth() * merc.getAttackDamage()) / 10);
        state.battleEnemy(merc);
        assertEquals(character.getHealth(), expectedCharHealth);
        assertEquals(merc.getHealth(), Mercenary.ORIGINAL_HEALTH - 10);
    }

    @Test
    public void testStandardBattleBow() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        PlayerState state = character.getPlayerState();
        assertEquals(state.getType(), "Standard");
        Inventory inv = character.getInventory();
        Bow b = new Bow(character.getDungeon());
        inv.add(b);

        // mercenary battle - no armour
        Mercenary merc = new Mercenary(new Position(0, 0), character.getDungeon());
        merc.giveArmour(0);
        // expect double hit to instantly kill mercenary
        int expectedCharHealth = character.getHealth() - ((merc.getHealth() * merc.getAttackDamage()) / 10);
        int expectedEnemyHealth = merc.getHealth() - 2*((character.getHealth() * character.getAttackDamage()) / 5);
        state.battleEnemy(merc);
        assertEquals(character.getHealth(), expectedCharHealth);
        assertEquals(merc.getHealth(), expectedEnemyHealth);
    }

    @Test
    public void testStandardBattleArmour() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        PlayerState state = character.getPlayerState();
        assertEquals(state.getType(), "Standard");
        Inventory inv = character.getInventory();
        Armour a = new Armour(character.getDungeon(), Armour.DURABILITY);
        inv.add(a);

        // spider battle
        Spider spider = new Spider(new Position(0, 0), character.getDungeon());
        int expectedCharHealth = character.getHealth() - ((spider.getHealth() * spider.getAttackDamage()) / 20);
        int expectedEnemyHealth = spider.getHealth() - ((character.getHealth() * character.getAttackDamage()) / 5);
        state.battleEnemy(spider);
        assertEquals(character.getHealth(), expectedCharHealth);
        assertEquals(spider.getHealth(), expectedEnemyHealth);
    }

    @Test
    public void testStandardBattleShield() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        PlayerState state = character.getPlayerState();
        assertEquals(state.getType(), "Standard");
        Inventory inv = character.getInventory();
        Shield s = new Shield(character.getDungeon());
        inv.add(s);

        // spider battle - shield should protect character health completely
        Spider spider = new Spider(new Position(0, 0), character.getDungeon());
        int expectedEnemyHealth = spider.getHealth() - ((character.getHealth() * character.getAttackDamage()) / 5);
        state.battleEnemy(spider);
        assertEquals(character.getHealth(), Player.ORIGINAL_HEALTH_STANDARD);
        assertEquals(spider.getHealth(), expectedEnemyHealth);
    }

    // test mix of weapons and protection priorities
    @Test
    public void testStandardBattleEquipped() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        PlayerState state = character.getPlayerState();
        assertEquals(state.getType(), "Standard");
        Inventory inv = character.getInventory();
        Sword sword = new Sword(new Position(0, 0), character.getDungeon());
        inv.add(sword);
        Bow bow = new Bow(character.getDungeon());
        inv.add(bow);
        Armour armour = new Armour(character.getDungeon(), Armour.DURABILITY);
        inv.add(armour);
        Shield shield = new Shield(character.getDungeon());
        inv.add(shield);
        
        // spider battle - shows sword priority over bow, and shield over armour
        Spider spider = new Spider(new Position(0, 0), character.getDungeon());
        state.battleEnemy(spider);
        assertEquals(character.getHealth(), Player.ORIGINAL_HEALTH_STANDARD);
        assertEquals(spider.getHealth(), Spider.ORIGINAL_HEALTH - 10);
    }

    @Test
    public void testMixedStandardBattleHealth() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        PlayerState state = character.getPlayerState();
        assertEquals(state.getType(), "Standard");

        // spider battle - 1 round, character has 9 points remaining
        Spider spider = new Spider(new Position(0, 0), character.getDungeon());
        int expectedCharHealth1 = character.getHealth() - ((spider.getHealth() * spider.getAttackDamage()) / 10);
        int expectedEnemyHealth1 = spider.getHealth() - ((character.getHealth() * character.getAttackDamage()) / 5);
        state.battleEnemy(spider);
        assertTrue(character.getHealth() == expectedCharHealth1);
        assertEquals(spider.getHealth(), expectedEnemyHealth1);


        // followed by zombie battle - 2 rounds -> character should have 7 points after round 1 and 2
        ZombieToast zombie = new ZombieToast(new Position(0, 0), character.getDungeon());
        zombie.giveArmour(0);
        int expectedCharHealth2 = expectedCharHealth1 - ((zombie.getHealth()* zombie.getAttackDamage()) / 10);
        int expectedEnemyHealth2 = zombie.getHealth() - ((expectedCharHealth1 * character.getAttackDamage()) / 5);
        int expectedCharHealth3 = expectedCharHealth2 - ((expectedEnemyHealth2 * zombie.getAttackDamage()) / 10);
        int expectedEnemyHealth3 = expectedEnemyHealth2 - ((expectedCharHealth2 * character.getAttackDamage()) / 5);
        state.battleEnemy(zombie);
        assertEquals(character.getHealth(), expectedCharHealth3);
        assertEquals(zombie.getHealth(), expectedEnemyHealth3);

        // followed by merc battle - 3 rounds
        Mercenary merc = new Mercenary(new Position(0, 0), character.getDungeon());
        merc.giveArmour(0);
        int expectedCharHealth4 = expectedCharHealth3 - ((merc.getHealth() * merc.getAttackDamage()) / 10);
        int expectedEnemyHealth4 = merc.getHealth() - ((expectedCharHealth3 * character.getAttackDamage()) / 5);
        int expectedCharHealth5 = expectedCharHealth4 - ((expectedEnemyHealth4 * merc.getAttackDamage()) / 10);
        int expectedEnemyHealth5 = expectedEnemyHealth4 - ((expectedCharHealth4 * character.getAttackDamage()) / 5);
        int expectedCharHealth6 = expectedCharHealth5 - ((expectedEnemyHealth5 * merc.getAttackDamage()) / 10);
        int expectedEnemyHealth6 = expectedEnemyHealth5 - ((expectedCharHealth5 * character.getAttackDamage()) / 5);
        
        state.battleEnemy(merc);
        assertEquals(character.getHealth(), expectedCharHealth6);
        assertEquals(merc.getHealth(), expectedEnemyHealth6);
    }

    @Test
    public void testBattleEnemyArmour() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        PlayerState state = character.getPlayerState();
        assertEquals(state.getType(), "Standard");

        // zombietoast battle - with armour
        ZombieToast zombie = new ZombieToast(new Position(0, 0), character.getDungeon());
        zombie.giveArmour(Armour.DURABILITY);
        // now expect 3 rounds to kill zombie
        int expectedCharHealth1 = character.getHealth() - ((zombie.getHealth() * zombie.getAttackDamage()) / 10);
        int expectedEnemyHealth1 = zombie.getHealth() - ((character.getHealth() * character.getAttackDamage()) / 10);
        int expectedCharHealth2 = expectedCharHealth1 - ((expectedEnemyHealth1 * zombie.getAttackDamage()) / 10);
        int expectedEnemyHealth2 = expectedEnemyHealth1 - ((expectedCharHealth1 * character.getAttackDamage()) / 10);
        // zombie armour removed after 2 hits
        int expectedCharHealth3 = expectedCharHealth2 - ((expectedEnemyHealth2 * zombie.getAttackDamage()) / 10);
        int expectedEnemyHealth3 = expectedEnemyHealth2 - ((expectedCharHealth2 * character.getAttackDamage()) / 5);
        state.battleEnemy(zombie);
        assertEquals(character.getHealth(), expectedCharHealth3);
        assertEquals(zombie.getHealth(), expectedEnemyHealth3);

        // expect 2 rounds to kill mercenary with sword
        Inventory inv = character.getInventory();
        Sword sword = new Sword(new Position(0, 0), character.getDungeon());
        inv.add(sword);

        Mercenary merc = new Mercenary(new Position(0, 0), character.getDungeon());
        merc.giveArmour(Armour.DURABILITY);
        int expectedCharHealth4 = expectedCharHealth3 - ((merc.getHealth() * merc.getAttackDamage()) / 10);
        int expectedCharHealth5 = expectedCharHealth4 - (((merc.getHealth()-5) * merc.getAttackDamage()) / 10);
        state.battleEnemy(merc);
        assertEquals(character.getHealth(), expectedCharHealth5);
        assertEquals(merc.getHealth(), Mercenary.ORIGINAL_HEALTH - 5*2);
    }

    @Test
    public void testInvincibleBattle() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        InvincibilityPotion i1 = new InvincibilityPotion(new Position(0, 0), character.getDungeon());
        inv.add(i1);
        character.useItem("invincibility_potion");
        PlayerState state = character.getPlayerState();
        assertEquals(state.getType(), "Invincible");

        // character cannot lose health points, enemy health instantly depleted
        Spider spider = new Spider(new Position(0, 0), character.getDungeon());
        state.battleEnemy(spider);
        assertTrue(character.getHealth() == Player.ORIGINAL_HEALTH_STANDARD);
        assertTrue(spider.getHealth() == 0);

        ZombieToast zombie = new ZombieToast(new Position(0, 0), character.getDungeon());
        state.battleEnemy(zombie);
        assertTrue(character.getHealth() == Player.ORIGINAL_HEALTH_STANDARD);
        assertTrue(zombie.getHealth() == 0);

        Mercenary merc = new Mercenary(new Position(0, 0), character.getDungeon());
        state.battleEnemy(merc);
        assertTrue(character.getHealth() == Player.ORIGINAL_HEALTH_STANDARD);
        assertTrue(merc.getHealth() == 0);
    }

    @Test
    public void testInvisibleBattle() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        InvisibilityPotion i1 = new InvisibilityPotion(new Position(0, 0), character.getDungeon());
        inv.add(i1);
        character.useItem("invisibility_potion");
        PlayerState state = character.getPlayerState();
        assertEquals(state.getType(), "Invisible");

        // no health deductions should be made
        Spider spider = new Spider(new Position(0, 0), character.getDungeon());
        state.battleEnemy(spider);
        assertTrue(character.getHealth() == Player.ORIGINAL_HEALTH_STANDARD);
        assertTrue(spider.getHealth() == Spider.ORIGINAL_HEALTH);

        ZombieToast zombie = new ZombieToast(new Position(0, 0), character.getDungeon());
        state.battleEnemy(zombie);
        assertTrue(character.getHealth() == Player.ORIGINAL_HEALTH_STANDARD);
        assertTrue(zombie.getHealth() == ZombieToast.ORIGINAL_HEALTH);

        Mercenary merc = new Mercenary(new Position(0, 0), character.getDungeon());
        state.battleEnemy(merc);
        assertTrue(character.getHealth() == Player.ORIGINAL_HEALTH_STANDARD);
        assertTrue(merc.getHealth() == Mercenary.ORIGINAL_HEALTH);
    }

    @Test
    public void testBattleAlly() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        PlayerState state = character.getPlayerState();
        assertEquals(state.getType(), "Standard");

        // mercenary ally - should not battle
        Mercenary merc = new Mercenary(new Position(0, 0), character.getDungeon());
        merc.setAlly(true);
        state.battleEnemy(merc);
        assertEquals(character.getHealth(), Player.ORIGINAL_HEALTH_STANDARD);
        assertEquals(merc.getHealth(), Mercenary.ORIGINAL_HEALTH);
    }

    @Test
    public void fightEnemies() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Spider spider = new Spider(new Position(0, 1), character.getDungeon());
        assertEquals(Arrays.asList(character, spider), character.getDungeon().getEntities());
        character.move(Direction.DOWN);
        assertEquals(Arrays.asList(character), character.getDungeon().getEntities());
    }

    @Test
    public void fightEnemiesInheritArmour() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Spider spider = new Spider(new Position(0, 1), character.getDungeon());
        spider.giveArmour(5);
        assertEquals(Arrays.asList(character, spider), character.getDungeon().getEntities());
        character.move(Direction.DOWN);
        assertEquals(Arrays.asList(character), character.getDungeon().getEntities());
        assertEquals(Arrays.asList("armour"), character.getInventory().listInventory());
    }

    @Test
    public void mercenaryAllyBattle() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Mercenary merc1 = new Mercenary(new Position(0, 3), character.getDungeon());
        merc1.setAlly(true);
        
        Mercenary merc2 = new Mercenary(new Position(0, 1), character.getDungeon());
        merc2.giveArmour(0);
        assertEquals(Arrays.asList(character, merc1, merc2), character.getDungeon().getEntities());
        int expectedCharHealth = character.getHealth() - ((merc2.getHealth() * merc2.getAttackDamage()) / 10);

        // should only take 1 round of battle to defeat now when with ally
        character.move(Direction.DOWN);
        assertEquals(Arrays.asList(character, merc1), character.getDungeon().getEntities());
        assertEquals(expectedCharHealth, character.getHealth());
    }

    @Test
    public void useSceptreBeforeBattle() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Sceptre s = new Sceptre(character.getDungeon());
        inv.add(s);

        Mercenary m = new Mercenary(new Position(0, 0), character.getDungeon());
        character.useItem("sceptre");
        assertTrue(m.isAlly() == true);

        new ZombieToast(new Position(0, -1), character.getDungeon());
        // with ally, player should be able to kill zombie in one go
        character.move(Direction.UP);
        assertEquals(Arrays.asList(character, m), character.getDungeon().getEntities());
    }

    @Test
    public void testStandardBattleMidnightArmour() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        PlayerState state = character.getPlayerState();
        assertEquals(state.getType(), "Standard");
        Inventory inv = character.getInventory();
        MidnightArmour m = new MidnightArmour(character.getDungeon());
        inv.add(m);

        // spider battle - test using midnight armour as weapon and protection
        Spider spider = new Spider(new Position(0, 0), character.getDungeon());
        int expectedCharHealth = character.getHealth() - ((spider.getHealth() * spider.getAttackDamage()) / 20);
        int expectedEnemyHealth = spider.getHealth() - ((character.getHealth() * (character.getAttackDamage() + MidnightArmour.ADDED_ATTACK_DAMAGE)) / 5);
        state.battleEnemy(spider);
        assertEquals(character.getHealth(), expectedCharHealth);
        assertEquals(spider.getHealth(), expectedEnemyHealth);
        assertEquals(Arrays.asList(), inv.listInventory()); // used up
    }

    @Test
    public void testStandardBattleArmourPriority() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        PlayerState state = character.getPlayerState();
        assertEquals(state.getType(), "Standard");
        Inventory inv = character.getInventory();
        MidnightArmour m = new MidnightArmour(character.getDungeon());
        Armour a = new Armour(character.getDungeon(), Armour.DURABILITY);
        inv.add(m);
        inv.add(a);

        // spider battle - test using midnight armour as weapon and protection
        Spider spider = new Spider(new Position(0, 0), character.getDungeon());
        int expectedCharHealth = character.getHealth() - ((spider.getHealth() * spider.getAttackDamage()) / 20); // armour used
        int expectedEnemyHealth = spider.getHealth() - ((character.getHealth() * (character.getAttackDamage() + MidnightArmour.ADDED_ATTACK_DAMAGE)) / 5); // midnight armour used
        state.battleEnemy(spider);
        assertEquals(character.getHealth(), expectedCharHealth);
        assertEquals(spider.getHealth(), expectedEnemyHealth);
        assertEquals(Arrays.asList("midnight_armour", "armour"), inv.listInventory()); // both should have been used once, 1 use left each
    }
    
}
