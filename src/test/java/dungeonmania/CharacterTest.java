package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

// import dungeonmania.Inventory;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;
import dungeonmania.util.Direction;


public class CharacterTest {
    @Test
    public void testValidMovement() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        character.moveUp();
        assertEquals(new Position(0, -1), character.getPosition());
        character.moveDown();
        assertEquals(new Position(0, 0), character.getPosition());
        character.moveLeft();
        assertEquals(new Position(-1, 0), character.getPosition());
        character.moveRight();
        assertEquals(new Position(0, 0), character.getPosition());
        character.moveUp();
        character.moveUp();
        character.moveUp();
        character.moveRight();
        character.moveRight();
        assertEquals(new Position(2, -3), character.getPosition());
    }

    @Test
    public void testInvalidMovement() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Door d = new Door(new Position(1, 0), character.getDungeon(), 1);
        character.moveRight();
        assertEquals(new Position(0, 0), character.getPosition());
        Wall w = new Wall(new Position(-1, 0), character.getDungeon());
        character.moveLeft();
        assertEquals(new Position(0, 0), character.getPosition());
        ZombieToastSpawner z = new ZombieToastSpawner(new Position(0, 1), character.getDungeon());
        character.moveDown();
        assertEquals(new Position(0, 0), character.getPosition());
        Boulder b = new Boulder(new Position(0, -1), character.getDungeon());
        character.moveUp();
        assertEquals(new Position(0, -1), character.getPosition());
    }

    @Test
    public void testBlockedBoulders() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Boulder b1 = new Boulder(new Position(0, 1), character.getDungeon());
        ZombieToastSpawner z = new ZombieToastSpawner(new Position(0, 2), character.getDungeon());
        assertEquals(Arrays.asList(character, b1, z), character.getDungeon().getEntities());
        character.move(Direction.DOWN);  // should fail since boulder is blocked
        assertEquals(new Position(0, 0), character.getPosition());
    }

    @Test
    public void testUnlockDoorMovement() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Door d = new Door(new Position(1, 0), character.getDungeon(), 1);
        character.moveRight();
        assertEquals(new Position(0, 0), character.getPosition());
        Inventory inv = character.getInventory();
        Key k = new Key(new Position(0, 0), character.getDungeon(), 1);
        inv.add(k);
        // door should now unlock
        character.moveRight();
        assertEquals(new Position(1, 0), character.getPosition());
    }

    @Test
    public void pickUpItems() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Sword s = new Sword(new Position(0, 1), character.getDungeon());
        assertEquals(Arrays.asList(character, s), character.getDungeon().getEntities());
        character.move(Direction.DOWN);
        assertEquals(Arrays.asList("Sword"), inv.listInventory());
        assertEquals(Arrays.asList(character), character.getDungeon().getEntities());
        Treasure t = new Treasure(new Position(1, 3), character.getDungeon());
        character.move(Direction.DOWN);
        character.move(Direction.DOWN);
        character.move(Direction.RIGHT);
        assertEquals(Arrays.asList("Sword", "Treasure"), inv.listInventory());
    }

    @Test
    public void useValidItems() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        HealthPotion i1 = new HealthPotion(new Position(0, 0), character.getDungeon());
        InvisibilityPotion i2 = new InvisibilityPotion(new Position(0, 0), character.getDungeon());
        InvincibilityPotion i3 = new InvincibilityPotion(new Position(0, 0), character.getDungeon());
        Bomb i4 = new Bomb(new Position(0, 0), character.getDungeon());
        inv.add(i1);
        inv.add(i2);
        inv.add(i3);
        inv.add(i4);
        assertDoesNotThrow(() -> character.useItem("HealthPotion"));
        assertDoesNotThrow(() -> character.useItem("InvisibilityPotion"));
        assertDoesNotThrow(() -> character.useItem("InvincibilityPotion"));
        assertDoesNotThrow(() -> character.useItem("Bomb"));
        assertDoesNotThrow(() -> character.useItem(""));
    }

    @Test
    public void useInvalidItems() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Treasure i1 = new Treasure(new Position(0, 0), character.getDungeon());
        Key i2 = new Key(new Position(0, 0), character.getDungeon(), 1);
        Wood i3 = new Wood(new Position(0, 0), character.getDungeon());
        Arrow i4 = new Arrow(new Position(0, 0), character.getDungeon());
        Shield i5 = new Shield(character.getDungeon());
        Armour i6 = new Armour(character.getDungeon());
        Bow i7 = new Bow(character.getDungeon());
        Sword i8 = new Sword(new Position(0, 0), character.getDungeon());
        OneRing i9 = new OneRing(new Position(0, 0), character.getDungeon());
        inv.add(i1);
        inv.add(i2);
        inv.add(i3);
        inv.add(i4);
        inv.add(i5);
        inv.add(i6);
        inv.add(i7);
        inv.add(i8);
        inv.add(i9);
        assertThrows(IllegalArgumentException.class, () -> character.useItem("Treasure"));
        assertThrows(IllegalArgumentException.class, () -> character.useItem("Key"));
        assertThrows(IllegalArgumentException.class, () -> character.useItem("Wood"));
        assertThrows(IllegalArgumentException.class, () -> character.useItem("Arrow"));
        assertThrows(IllegalArgumentException.class, () -> character.useItem("Shield"));
        assertThrows(IllegalArgumentException.class, () -> character.useItem("Armour"));
        assertThrows(IllegalArgumentException.class, () -> character.useItem("Bow"));
        assertThrows(IllegalArgumentException.class, () -> character.useItem("Sword"));
        assertThrows(IllegalArgumentException.class, () -> character.useItem("OneRing"));
    }


    @Test
    public void testStandardBattleSpiderHealth() {
        // tests health score after battle (ignoring deaths)
        // NEED TO FIX - ALSO NEED TO DO FIGHT ENEMIES INSTEAD OF JUST BATTLE
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Standard");
       
        // spider battle
        Spider spider = new Spider(new Position(0, 0), character.getDungeon());
        int expectedCharHealth = character.getHealth() - ((spider.getHealth() * spider.getAttackDamage()) / 10);
        int expectedEnemyHealth = spider.getHealth() - ((character.getHealth() * character.getAttackDamage()) / 5);
        state.battleEnemy(spider); // if spider is killed it may not exist after this
        assertTrue(character.getHealth() == expectedCharHealth);
        assertEquals(spider.getHealth(), expectedEnemyHealth);
    }

    @Test
    public void testStandardBattleZombieHealth() {
        // tests health score after battle (ignoring deaths)
        // NEED TO FIX - ALSO NEED TO DO FIGHT ENEMIES INSTEAD OF JUST BATTLE
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
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
        // tests health score after battle (ignoring deaths)
        // NEED TO FIX - ALSO NEED TO DO FIGHT ENEMIES INSTEAD OF JUST BATTLE
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Standard");

        // mercenary battle - no armour
        Mercenary merc = new Mercenary(new Position(0, 0), character.getDungeon());
        // merc.giveArmour(0);
        // // expect 3 rounds to kill mercenary
        // int expectedCharHealth1 = character.getHealth() - ((merc.getHealth() * merc.getAttackDamage()) / 10);
        // int expectedEnemyHealth1 = merc.getHealth() - ((character.getHealth() * character.getAttackDamage()) / 5);
        // int expectedCharHealth2 = expectedCharHealth1 - ((expectedEnemyHealth1 * merc.getAttackDamage()) / 10);
        // int expectedEnemyHealth2 = expectedEnemyHealth1 - ((expectedCharHealth1 * character.getAttackDamage()) / 5);
        // int expectedCharHealth3 = expectedCharHealth2 - ((expectedEnemyHealth2 * merc.getAttackDamage()) / 10);
        // int expectedEnemyHealth3 = expectedEnemyHealth2 - ((expectedCharHealth2 * character.getAttackDamage()) / 5);
        // state.battleEnemy(merc);
        // assertEquals(character.getHealth(), expectedCharHealth3);
        // assertEquals(merc.getHealth(), expectedEnemyHealth3);
    }

    @Test
    public void testStandardBattleSword() {
        // tests health score after battle (ignoring deaths)
        // NEED TO FIX - ALSO NEED TO DO FIGHT ENEMIES INSTEAD OF JUST BATTLE
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
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
        assertEquals(merc.getHealth(), 0);
    }

    @Test
    public void testStandardBattleBow() {
        // tests health score after battle (ignoring deaths)
        // NEED TO FIX - ALSO NEED TO DO FIGHT ENEMIES INSTEAD OF JUST BATTLE
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
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
        // tests health score after battle (ignoring deaths)
        // NEED TO FIX - ALSO NEED TO DO FIGHT ENEMIES INSTEAD OF JUST BATTLE
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Standard");
        Inventory inv = character.getInventory();
        Armour a = new Armour(character.getDungeon());
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
        // tests health score after battle (ignoring deaths)
        // NEED TO FIX - ALSO NEED TO DO FIGHT ENEMIES INSTEAD OF JUST BATTLE
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Standard");
        Inventory inv = character.getInventory();
        Shield s = new Shield(character.getDungeon());
        inv.add(s);

        // spider battle - shield should protect character health completely
        Spider spider = new Spider(new Position(0, 0), character.getDungeon());
        int expectedEnemyHealth = spider.getHealth() - ((character.getHealth() * character.getAttackDamage()) / 5);
        state.battleEnemy(spider);
        assertEquals(character.getHealth(), Player.ORIGINAL_HEALTH);
        assertEquals(spider.getHealth(), expectedEnemyHealth);
    }

    // test mix of weapons and protection priorities
    @Test
    public void testStandardBattleEquipped() {
        // tests health score after battle (ignoring deaths)
        // NEED TO FIX - ALSO NEED TO DO FIGHT ENEMIES INSTEAD OF JUST BATTLE
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Standard");
        Inventory inv = character.getInventory();
        Sword sword = new Sword(new Position(0, 0), character.getDungeon());
        inv.add(sword);
        Bow bow = new Bow(character.getDungeon());
        inv.add(bow);
        Armour armour = new Armour(character.getDungeon());
        inv.add(armour);
        Shield shield = new Shield(character.getDungeon());
        inv.add(shield);
        
        // spider battle - shows sword priority over bow, and shield over armour
        Spider spider = new Spider(new Position(0, 0), character.getDungeon());
        state.battleEnemy(spider);
        assertEquals(character.getHealth(), Player.ORIGINAL_HEALTH);
        assertEquals(spider.getHealth(), 0);
    }

    @Test
    public void testMixedStandardBattleHealth() {
        // tests health score after battle (ignoring deaths)
        // NEED TO FIX - ALSO NEED TO DO FIGHT ENEMIES INSTEAD OF JUST BATTLE
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
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
        // tests health score after battle (ignoring deaths)
        // NEED TO FIX - ALSO NEED TO DO FIGHT ENEMIES INSTEAD OF JUST BATTLE
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
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

        // show sword overrides armour
        Inventory inv = character.getInventory();
        Sword sword = new Sword(new Position(0, 0), character.getDungeon());
        inv.add(sword);

        Mercenary merc = new Mercenary(new Position(0, 0), character.getDungeon());
        merc.giveArmour(Armour.DURABILITY);
        int expectedCharHealth4 = expectedCharHealth3 - ((merc.getHealth() * merc.getAttackDamage()) / 10);
        state.battleEnemy(merc);
        assertEquals(character.getHealth(), expectedCharHealth4);
        assertEquals(merc.getHealth(), 0);
    }

    @Test
    public void testInvincibleBattle() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        InvincibilityPotion i1 = new InvincibilityPotion(new Position(0, 0), character.getDungeon());
        inv.add(i1);
        character.useItem("InvincibilityPotion");
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Invincible");

        // character cannot lose health points, enemy health instantly depleted
        Spider spider = new Spider(new Position(0, 0), character.getDungeon());
        state.battleEnemy(spider);
        assertTrue(character.getHealth() == Player.ORIGINAL_HEALTH);
        assertTrue(spider.getHealth() == 0);

        ZombieToast zombie = new ZombieToast(new Position(0, 0), character.getDungeon());
        state.battleEnemy(zombie);
        assertTrue(character.getHealth() == Player.ORIGINAL_HEALTH);
        assertTrue(zombie.getHealth() == 0);

        Mercenary merc = new Mercenary(new Position(0, 0), character.getDungeon());
        state.battleEnemy(merc);
        assertTrue(character.getHealth() == Player.ORIGINAL_HEALTH);
        assertTrue(merc.getHealth() == 0);
    }

    @Test
    public void testInvisibleBattle() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        InvisibilityPotion i1 = new InvisibilityPotion(new Position(0, 0), character.getDungeon());
        inv.add(i1);
        character.useItem("InvisibilityPotion");
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Invisible");

        // no health deductions should be made
        Spider spider = new Spider(new Position(0, 0), character.getDungeon());
        state.battleEnemy(spider);
        assertTrue(character.getHealth() == Player.ORIGINAL_HEALTH);
        assertTrue(spider.getHealth() == Spider.ORIGINAL_HEALTH);

        ZombieToast zombie = new ZombieToast(new Position(0, 0), character.getDungeon());
        state.battleEnemy(zombie);
        assertTrue(character.getHealth() == Player.ORIGINAL_HEALTH);
        assertTrue(zombie.getHealth() == ZombieToast.ORIGINAL_HEALTH);

        Mercenary merc = new Mercenary(new Position(0, 0), character.getDungeon());
        state.battleEnemy(merc);
        assertTrue(character.getHealth() == Player.ORIGINAL_HEALTH);
        assertTrue(merc.getHealth() == Mercenary.ORIGINAL_HEALTH);
    }

    @Test
    public void testBattleAlly() {
        // tests health score after battle (ignoring deaths)
        // NEED TO FIX - ALSO NEED TO DO FIGHT ENEMIES INSTEAD OF JUST BATTLE
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Standard");

        // mercenary ally - should not battle
        Mercenary merc = new Mercenary(new Position(0, 0), character.getDungeon());
        merc.setAlly(true);
        state.battleEnemy(merc);
        assertEquals(character.getHealth(), Player.ORIGINAL_HEALTH);
        assertEquals(merc.getHealth(), Mercenary.ORIGINAL_HEALTH);
    }

    @Test
    public void fightEnemies() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Spider spider = new Spider(new Position(0, 1), character.getDungeon());
        assertEquals(Arrays.asList(character, spider), character.getDungeon().getEntities());
        character.moveUp();
        // fight should happen
        assertEquals(Arrays.asList(character), character.getDungeon().getEntities());
    }

    @Test
    public void fightEnemiesInheritArmour() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Spider spider = new Spider(new Position(0, 1), character.getDungeon());
        spider.giveArmour(Armour.DURABILITY);
        assertEquals(Arrays.asList(character, spider), character.getDungeon().getEntities());
        character.move(Direction.DOWN);
        // fight should happen
        assertEquals(Arrays.asList(character), character.getDungeon().getEntities());
        assertEquals(Arrays.asList("Armour"), character.getInventory().listInventory());
    }

    @Test
    public void characterDeath() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        character.setHealth(1);
        Mercenary merc = new Mercenary(new Position(0, 1), character.getDungeon());
        assertEquals(Arrays.asList(character, merc), character.getDungeon().getEntities());
        character.moveUp();
        // fight should eliminate character
        assertEquals(Arrays.asList(merc), character.getDungeon().getEntities());
    }

    @Test
    public void revivalWithOneRing() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        character.setHealth(1);
        Inventory inv = character.getInventory();
        OneRing ring = new OneRing(new Position(0, 0), character.getDungeon());
        inv.add(ring); // doesn't remove from dungeon
        assertEquals(Arrays.asList("OneRing"), inv.listInventory());

        Mercenary merc = new Mercenary(new Position(0, 1), character.getDungeon());
        assertEquals(Arrays.asList(character, ring, merc), character.getDungeon().getEntities());
        character.moveUp();
        // fight should happen, one ring should be used since merc can kill character
        assertEquals(Arrays.asList(character, ring, merc), character.getDungeon().getEntities());
        assertEquals(Arrays.asList(), inv.listInventory());
        assertEquals(Player.ORIGINAL_HEALTH, character.getHealth());
    }

    @Test
    public void bribeMercenary() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Treasure t1 = new Treasure(new Position(0, 0), character.getDungeon());
        Treasure t2 = new Treasure(new Position(0, 0), character.getDungeon());
        inv.add(t1);
        inv.add(t2);
        assertEquals(Arrays.asList("Treasure", "Treasure"), inv.listInventory());

        // too far - fail
        Mercenary merc1 = new Mercenary(new Position(0, 3), character.getDungeon());
        assertEquals(false, merc1.isAlly());
        assertThrows(InvalidActionException.class, () -> character.bribe(merc1));

        // diagonal - fail
        merc1.setPosition(new Position(1,1));
        assertThrows(InvalidActionException.class, () -> character.bribe(merc1));
        merc1.setPosition(new Position(-2,3));
        assertThrows(InvalidActionException.class, () -> character.bribe(merc1));

        // within 1 cardinal square - sucess
        merc1.setPosition(new Position(0,1));
        character.bribe(merc1);
        assertEquals(true, merc1.isAlly());
        assertEquals(Arrays.asList("Treasure"), inv.listInventory());
        
        // within 2 cardinal squares - success
        Mercenary merc2 = new Mercenary(new Position(0, 1), character.getDungeon());
        assertEquals(false, merc2.isAlly());
        character.bribe(merc2);
        assertEquals(true, merc2.isAlly());
        assertEquals(Arrays.asList(), inv.listInventory());
    }

    @Test
    public void bribeMercenaryNoTreasure() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        // no treasure - fail
        Mercenary merc1 = new Mercenary(new Position(0, 1), character.getDungeon());
        assertEquals(false, merc1.isAlly());
        assertThrows(InvalidActionException.class, () -> character.bribe(merc1));
    }

    @Test
    public void destroyZombieToastSpawnerSword() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Sword s = new Sword(new Position(0, 1), character.getDungeon());
        inv.add(s); // doesn't remove from dungeon
        assertEquals(Arrays.asList("Sword"), inv.listInventory());

        // too far - should fail
        ZombieToastSpawner z = new ZombieToastSpawner(new Position(0, 2), character.getDungeon());
        assertEquals(Arrays.asList(character, s, z), character.getDungeon().getEntities());
        assertThrows(InvalidActionException.class, () -> character.destroySpawner(z));

        // not cardinally adjacent - should fail
        z.setPosition(new Position(1,1));
        assertThrows(InvalidActionException.class, () -> character.destroySpawner(z));

        // now passes
        z.setPosition(new Position(0,1));
        character.destroySpawner(z);
        assertEquals(Arrays.asList(character, s), character.getDungeon().getEntities());
    }

    @Test
    public void destroyZombieToastSpawnerBow() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Bow b = new Bow(character.getDungeon());
        inv.add(b);
        assertEquals(Arrays.asList("Bow"), inv.listInventory());

        // check bow works
        ZombieToastSpawner z = new ZombieToastSpawner(new Position(0, 2), character.getDungeon());
        z.setPosition(new Position(0,1));
        character.destroySpawner(z);
        assertEquals(Arrays.asList(character, b), character.getDungeon().getEntities());
    }

    @Test
    public void destroyZombieToastSpawnerAttempt() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        ZombieToastSpawner z = new ZombieToastSpawner(new Position(0, 1), character.getDungeon());
        // no weapon - should fail
        assertThrows(InvalidActionException.class, () -> character.destroySpawner(z));
    }

}
