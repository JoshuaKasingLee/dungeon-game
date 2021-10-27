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


public class CharacterTest {
    @Test
    public void testMovement() {
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        character.moveUp();
        assertEquals(new Position(0, 1), character.getPosition());
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
        assertEquals(new Position(2, 3), character.getPosition());
    }

    @Test
    public void pickUpItems() {
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Sword s = new Sword(new Position(0, 1), "s", character.getDungeon());
        character.moveUp();
        assertEquals(Arrays.asList("Sword"), inv.listInventory());
        Treasure t = new Treasure(new Position(1, 3), "t", character.getDungeon());
        character.moveUp();
        character.moveUp();
        character.moveRight();
        assertEquals(Arrays.asList("Sword", "Treasure"), inv.listInventory());
    }

    @Test
    public void useValidItems() {
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        HealthPotion i1 = new HealthPotion(new Position(0, 0), "i1", character.getDungeon());
        InvisibilityPotion i2 = new InvisibilityPotion(new Position(0, 0), "i2", character.getDungeon());
        InvincibilityPotion i3 = new InvincibilityPotion(new Position(0, 0), "i3", character.getDungeon());
        Bomb i4 = new Bomb(new Position(0, 0), "i4", character.getDungeon());
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
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Treasure i1 = new Treasure(new Position(0, 0), "i1", character.getDungeon());
        Key i2 = new Key(new Position(0, 0), "i2", character.getDungeon(), 1);
        Wood i3 = new Wood(new Position(0, 0), "i3", character.getDungeon());
        Arrow i4 = new Arrow(new Position(0, 0), "i4", character.getDungeon());
        Shield i5 = new Shield("i5", character.getDungeon());
        Armour i6 = new Armour("i6", character.getDungeon());
        Bow i7 = new Bow("i7", character.getDungeon());
        Sword i8 = new Sword(new Position(0, 0), "i8", character.getDungeon());
        OneRing i9 = new OneRing(new Position(0, 0), "i9", character.getDungeon());
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
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Standard");
       
        // spider battle
        Spider spider = new Spider(new Position(0, 0), "Polly", character.getDungeon());
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
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Standard");

        // zombietoast battle - no armour
        ZombieToast zombie = new ZombieToast(new Position(0, 0), "Holly", character.getDungeon());
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
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Standard");

        // mercenary battle - no armour
        Mercenary merc = new Mercenary(new Position(0, 0), "Molly", character.getDungeon());
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
        // tests health score after battle (ignoring deaths)
        // NEED TO FIX - ALSO NEED TO DO FIGHT ENEMIES INSTEAD OF JUST BATTLE
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Standard");
        Inventory inv = character.getInventory();
        Sword s = new Sword(new Position(0, 0), "s", character.getDungeon());
        inv.add(s);

        // mercenary battle - no armour
        Mercenary merc = new Mercenary(new Position(0, 0), "Molly", character.getDungeon());
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
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Standard");
        Inventory inv = character.getInventory();
        Bow b = new Bow("i1", character.getDungeon());
        inv.add(b);

        // mercenary battle - no armour
        Mercenary merc = new Mercenary(new Position(0, 0), "Molly", character.getDungeon());
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
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Standard");
        Inventory inv = character.getInventory();
        Armour a = new Armour("i1", character.getDungeon());
        inv.add(a);

        // spider battle
        Spider spider = new Spider(new Position(0, 0), "Polly", character.getDungeon());
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
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Standard");
        Inventory inv = character.getInventory();
        Shield s = new Shield("s", character.getDungeon());
        inv.add(s);

        // spider battle - shield should protect character health completely
        Spider spider = new Spider(new Position(0, 0), "Polly", character.getDungeon());
        int expectedEnemyHealth = spider.getHealth() - ((character.getHealth() * character.getAttackDamage()) / 5);
        state.battleEnemy(spider);
        assertEquals(character.getHealth(), Character.ORIGINAL_HEALTH);
        assertEquals(spider.getHealth(), expectedEnemyHealth);
    }

    // test mix of weapons and protection priorities
    @Test
    public void testStandardBattleEquipped() {
        // tests health score after battle (ignoring deaths)
        // NEED TO FIX - ALSO NEED TO DO FIGHT ENEMIES INSTEAD OF JUST BATTLE
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Standard");
        Inventory inv = character.getInventory();
        Sword sword = new Sword(new Position(0, 0), "sword", character.getDungeon());
        inv.add(sword);
        Bow bow = new Bow("bow", character.getDungeon());
        inv.add(bow);
        Armour armour = new Armour("armour", character.getDungeon());
        inv.add(armour);
        Shield shield = new Shield("shield", character.getDungeon());
        inv.add(shield);
        
        // spider battle - shows sword priority over bow, and shield over armour
        Spider spider = new Spider(new Position(0, 0), "Polly", character.getDungeon());
        state.battleEnemy(spider);
        assertEquals(character.getHealth(), Character.ORIGINAL_HEALTH);
        assertEquals(spider.getHealth(), 0);
    }

    @Test
    public void testMixedStandardBattleHealth() {
        // tests health score after battle (ignoring deaths)
        // NEED TO FIX - ALSO NEED TO DO FIGHT ENEMIES INSTEAD OF JUST BATTLE
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Standard");

        // spider battle - 1 round, character has 9 points remaining
        Spider spider = new Spider(new Position(0, 0), "Polly", character.getDungeon());
        int expectedCharHealth1 = character.getHealth() - ((spider.getHealth() * spider.getAttackDamage()) / 10);
        int expectedEnemyHealth1 = spider.getHealth() - ((character.getHealth() * character.getAttackDamage()) / 5);
        state.battleEnemy(spider);
        assertTrue(character.getHealth() == expectedCharHealth1);
        assertEquals(spider.getHealth(), expectedEnemyHealth1);


        // followed by zombie battle - 2 rounds -> character should have 7 points after round 1 and 2
        ZombieToast zombie = new ZombieToast(new Position(0, 0), "Holly", character.getDungeon());
        zombie.giveArmour(0);
        int expectedCharHealth2 = expectedCharHealth1 - ((zombie.getHealth()* zombie.getAttackDamage()) / 10);
        int expectedEnemyHealth2 = zombie.getHealth() - ((expectedCharHealth1 * character.getAttackDamage()) / 5);
        int expectedCharHealth3 = expectedCharHealth2 - ((expectedEnemyHealth2 * zombie.getAttackDamage()) / 10);
        int expectedEnemyHealth3 = expectedEnemyHealth2 - ((expectedCharHealth2 * character.getAttackDamage()) / 5);
        state.battleEnemy(zombie);
        assertEquals(character.getHealth(), expectedCharHealth3);
        assertEquals(zombie.getHealth(), expectedEnemyHealth3);

        // followed by merc battle - 3 rounds
        Mercenary merc = new Mercenary(new Position(0, 0), "Molly", character.getDungeon());
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
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Standard");

        // zombietoast battle - with armour
        ZombieToast zombie = new ZombieToast(new Position(0, 0), "Holly", character.getDungeon());
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
        Sword sword = new Sword(new Position(0, 0), "sword", character.getDungeon());
        inv.add(sword);

        Mercenary merc = new Mercenary(new Position(0, 0), "Molly", character.getDungeon());
        merc.giveArmour(Armour.DURABILITY);
        int expectedCharHealth4 = expectedCharHealth3 - ((merc.getHealth() * merc.getAttackDamage()) / 10);
        state.battleEnemy(merc);
        assertEquals(character.getHealth(), expectedCharHealth4);
        assertEquals(merc.getHealth(), 0);
    }

    @Test
    public void testInvincibleBattle() {
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        InvincibilityPotion i1 = new InvincibilityPotion(new Position(0, 0), "i1", character.getDungeon());
        inv.add(i1);
        character.useItem("InvincibilityPotion");
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Invincible");

        // character cannot lose health points, enemy health instantly depleted
        Spider spider = new Spider(new Position(0, 0), "Polly", character.getDungeon());
        state.battleEnemy(spider);
        assertTrue(character.getHealth() == Character.ORIGINAL_HEALTH);
        assertTrue(spider.getHealth() == 0);

        ZombieToast zombie = new ZombieToast(new Position(0, 0), "Holly", character.getDungeon());
        state.battleEnemy(zombie);
        assertTrue(character.getHealth() == Character.ORIGINAL_HEALTH);
        assertTrue(zombie.getHealth() == 0);

        Mercenary merc = new Mercenary(new Position(0, 0), "Molly", character.getDungeon());
        state.battleEnemy(merc);
        assertTrue(character.getHealth() == Character.ORIGINAL_HEALTH);
        assertTrue(merc.getHealth() == 0);
    }

    @Test
    public void testInvisibleBattle() {
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        InvisibilityPotion i1 = new InvisibilityPotion(new Position(0, 0), "i1", character.getDungeon());
        inv.add(i1);
        character.useItem("InvisibilityPotion");
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Invisible");

        // no health deductions should be made
        Spider spider = new Spider(new Position(0, 0), "Polly", character.getDungeon());
        state.battleEnemy(spider);
        assertTrue(character.getHealth() == Character.ORIGINAL_HEALTH);
        assertTrue(spider.getHealth() == Spider.ORIGINAL_HEALTH);

        ZombieToast zombie = new ZombieToast(new Position(0, 0), "Holly", character.getDungeon());
        state.battleEnemy(zombie);
        assertTrue(character.getHealth() == Character.ORIGINAL_HEALTH);
        assertTrue(zombie.getHealth() == ZombieToast.ORIGINAL_HEALTH);

        Mercenary merc = new Mercenary(new Position(0, 0), "Molly", character.getDungeon());
        state.battleEnemy(merc);
        assertTrue(character.getHealth() == Character.ORIGINAL_HEALTH);
        assertTrue(merc.getHealth() == Mercenary.ORIGINAL_HEALTH);
    }

    @Test
    public void testBattleAlly() {
        // tests health score after battle (ignoring deaths)
        // NEED TO FIX - ALSO NEED TO DO FIGHT ENEMIES INSTEAD OF JUST BATTLE
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Standard");

        // mercenary ally - should not battle
        Mercenary merc = new Mercenary(new Position(0, 0), "Molly", character.getDungeon());
        merc.setAlly(true);
        state.battleEnemy(merc);
        assertEquals(character.getHealth(), Character.ORIGINAL_HEALTH);
        assertEquals(merc.getHealth(), Mercenary.ORIGINAL_HEALTH);
    }

    @Test
    public void fightEnemies() {
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        Spider spider = new Spider(new Position(0, 1), "Polly", character.getDungeon());
        assertEquals(Arrays.asList(character, spider), character.getDungeon().getAllEntities());
        character.moveUp();
        // fight should happen
        assertEquals(Arrays.asList(character), character.getDungeon().getAllEntities());
    }
    
}
