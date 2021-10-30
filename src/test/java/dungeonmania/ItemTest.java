package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

// import java.util.ArrayList;
// import java.util.List;
import java.util.Arrays;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;


public class ItemTest {
    @Test
    public void useInactiveItems() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Treasure i1 = new Treasure(new Position(0, 0), character.getDungeon());
        Wood i2 = new Wood(new Position(0, 0), character.getDungeon());
        Arrow i3 = new Arrow(new Position(0, 0), character.getDungeon());
        inv.add(i1);
        inv.add(i2);
        inv.add(i3);
        // check "use" works but does not do anything
        assertDoesNotThrow(() -> inv.use("Treasure", character));
        assertDoesNotThrow(() -> inv.use("Wood", character));
        assertDoesNotThrow(() -> inv.use("Arrow", character));

    }

    @Test
    public void useNonExistentItems() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Treasure i1 = new Treasure(new Position(0, 0), character.getDungeon());
        Wood i2 = new Wood(new Position(0, 0), character.getDungeon());
        inv.add(i1);
        inv.add(i2);
        assertThrows(InvalidActionException.class, () -> inv.use("Arrow", character));
    }
    
    @Test
    public void testHealthPotion() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        assertEquals(Player.ORIGINAL_HEALTH, character.getHealth());
        Inventory inv = character.getInventory();
        HealthPotion potion = new HealthPotion(new Position(0, 0), character.getDungeon());
        inv.add(potion);
        character.setHealth(2);
        assertEquals(2, character.getHealth());
        inv.use("HealthPotion", character);
        assertEquals(Player.ORIGINAL_HEALTH, character.getHealth());
    }

    @Test
    public void useInvisibilityPotion() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        assertEquals(Player.ORIGINAL_HEALTH, character.getHealth());
        Inventory inv = character.getInventory();
        InvisibilityPotion i = new InvisibilityPotion(new Position(0, 0), character.getDungeon());
        inv.add(i);
        assertEquals("Standard", character.getCharacterState().getType());
        inv.use("InvisibilityPotion", character);
        assertEquals("Invisible", character.getCharacterState().getType());
        // effect is tested in character.java
    }

    @Test
    public void useInvincibilityPotion() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        assertEquals(Player.ORIGINAL_HEALTH, character.getHealth());
        Inventory inv = character.getInventory();
        InvincibilityPotion i = new InvincibilityPotion(new Position(0, 0), character.getDungeon());
        inv.add(i);
        assertEquals("Standard", character.getCharacterState().getType());
        inv.use("InvincibilityPotion", character);
        assertEquals("Invincible", character.getCharacterState().getType());
        // effect is tested in character.java
    }
    

    @Test
    public void testArmourLongevity() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Armour a = new Armour(character.getDungeon(), Armour.DURABILITY);
        inv.add(a);
        inv.use("Armour", character);
        assertEquals(Arrays.asList("Armour"), inv.listInventory());
        inv.use("Armour", character);
        assertEquals(Arrays.asList(), inv.listInventory());
        // armour effect is tested in character.java
    }

    @Test
    public void testShieldLongevity() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Shield s = new Shield(character.getDungeon());
        inv.add(s);
        inv.use("Shield", character);
        assertEquals(Arrays.asList("Shield"), inv.listInventory());
        inv.use("Shield", character);
        assertEquals(Arrays.asList("Shield"), inv.listInventory());
        inv.use("Shield", character);
        assertEquals(Arrays.asList(), inv.listInventory());
        // shield effect is tested in character.java
    }

    @Test
    public void useOneRing() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        assertEquals(Player.ORIGINAL_HEALTH, character.getHealth());
        Inventory inv = character.getInventory();
        OneRing ring = new OneRing(new Position(0, 0), character.getDungeon());
        inv.add(ring);
        character.setHealth(-1);
        assertEquals(-1, character.getHealth());
        inv.use("OneRing", character);
        assertEquals(Player.ORIGINAL_HEALTH, character.getHealth());
        // more testing in character.java
    }
    
    @Test
    public void testSword() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Sword s = new Sword(new Position(0, 0), character.getDungeon());
        inv.add(s);
        inv.use("Sword", character);
        // sword effect is tested in character.java
    }

    @Test
    public void testBow() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Bow b = new Bow(character.getDungeon());
        inv.add(b);
        inv.use("Bow", character);
        // bow effect is tested in character.java
    }

    @Test
    public void testBombExplosion() {
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");
        Player character = new Player(new Position(0, 0), dungeon);
        Bomb b = new Bomb(new Position(0, 0), dungeon);

        // within radius
        Sword s = new Sword(new Position(0, 1), dungeon);
        Spider spider = new Spider(new Position(-1, -2), dungeon);
        Door d = new Door(new Position(-3, 0), dungeon, 1);

        // not within radius
        Treasure t = new Treasure(new Position(3, 3), character.getDungeon());

        assertEquals(Arrays.asList(character, b, s, spider, d, t), character.getDungeon().getEntities());
        b.explode();

        // only character and treasure should remain
        assertEquals(Arrays.asList(character, t), character.getDungeon().getEntities());
    }

    @Test
    public void testKeyMatch() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Key k = new Key(new Position(0, 0), character.getDungeon(), 1);
        inv.add(k);
        inv.useKey(null, character);
        assertEquals(Arrays.asList("Key"), inv.listInventory());
        Door d1 = new Door(new Position(0, 1), character.getDungeon(), 2);
        assertEquals(false, inv.useKey(d1, character));

        Door d2 = new Door(new Position(1, 0), character.getDungeon(), 1);
        assertTrue(inv.useKey(d2, character));
        assertEquals(Arrays.asList(), inv.listInventory());
    }
}
