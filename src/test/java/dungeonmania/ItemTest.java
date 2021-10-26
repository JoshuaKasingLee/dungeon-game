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


public class ItemTest {
    @Test
    public void useInactiveItems() {
        Character character = new Character(new Position(0, 0), "Kelly");
        Inventory inv = character.getInventory();
        Treasure i1 = new Treasure("i1");
        Wood i2 = new Wood("i2");
        Arrow i3 = new Arrow("i3");
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
        Character character = new Character(new Position(0, 0), "Kelly");
        Inventory inv = character.getInventory();
        Treasure i1 = new Treasure("i1");
        Wood i2 = new Wood("i2");
        inv.add(i1);
        inv.add(i2);
        assertThrows(InvalidActionException.class, () -> inv.use("Arrow", character));
    }
    
    @Test
    public void testHealthPotion() {
        Character character = new Character(new Position(0, 0), "Kelly");
        assertEquals(Character.ORIGINAL_HEALTH, character.getHealth());
        Inventory inv = character.getInventory();
        HealthPotion potion = new HealthPotion("i1");
        inv.add(potion);
        character.setHealth(2);
        assertEquals(2, character.getHealth());
        inv.use("HealthPotion", character);
        assertEquals(Character.ORIGINAL_HEALTH, character.getHealth());
    }

    @Test
    public void useInvisibilityPotion() {
        Character character = new Character(new Position(0, 0), "Kelly");
        assertEquals(Character.ORIGINAL_HEALTH, character.getHealth());
        Inventory inv = character.getInventory();
        InvisibilityPotion i = new InvisibilityPotion("my_potion");
        inv.add(i);
        assertEquals("Standard", character.getCharacterState().getType());
        inv.use("InvisibilityPotion", character);
        assertEquals("Invisible", character.getCharacterState().getType());
        // NEED TO ADD
    }

    @Test
    public void useInvincibilityPotion() {
        Character character = new Character(new Position(0, 0), "Kelly");
        assertEquals(Character.ORIGINAL_HEALTH, character.getHealth());
        Inventory inv = character.getInventory();
        InvincibilityPotion i = new InvincibilityPotion("my_potion");
        inv.add(i);
        assertEquals("Standard", character.getCharacterState().getType());
        inv.use("InvincibilityPotion", character);
        assertEquals("Invincible", character.getCharacterState().getType());
        // NEED TO ADD
    }

    

    @Test
    public void testArmourLongevity() {
        Character character = new Character(new Position(0, 0), "Kelly");
        Inventory inv = character.getInventory();
        Armour a = new Armour("a");
        inv.add(a);
        inv.use("Armour", character);
        assertEquals(Arrays.asList("Armour"), inv.listInventory());
        inv.use("Armour", character);
        assertEquals(Arrays.asList(), inv.listInventory());
    }

    @Test
    public void testShieldLongevity() {
        Character character = new Character(new Position(0, 0), "Kelly");
        Inventory inv = character.getInventory();
        Shield s = new Shield("shield");
        inv.add(s);
        inv.use("Shield", character);
        assertEquals(Arrays.asList("Shield"), inv.listInventory());
        inv.use("Shield", character);
        assertEquals(Arrays.asList("Shield"), inv.listInventory());
        inv.use("Shield", character);
        assertEquals(Arrays.asList(), inv.listInventory());
    }

    

    @Test
    public void useOneRing() {
        Character character = new Character(new Position(0, 0), "Kelly");
        assertEquals(Character.ORIGINAL_HEALTH, character.getHealth());
        Inventory inv = character.getInventory();
        OneRing ring = new OneRing("one_ring");
        inv.add(ring);
        character.setHealth(-1);
        assertEquals(-1, character.getHealth());
        inv.use("OneRing", character);
        assertEquals(Character.ORIGINAL_HEALTH, character.getHealth());
    }

    // need to test one ring again for real death
    
    @Test
    public void testSword() {
        Character character = new Character(new Position(0, 0), "Kelly");
        Inventory inv = character.getInventory();
        Sword s = new Sword("s");
        inv.add(s);
        // WRITE TEST
    }

    @Test
    public void testBow() {
        Character character = new Character(new Position(0, 0), "Kelly");
        Inventory inv = character.getInventory();
        Bow b = new Bow("b");
        inv.add(b);
        // WRITE TEST
    }

    @Test
    public void testBomb() {
        Character character = new Character(new Position(0, 0), "Kelly");
        Inventory inv = character.getInventory();
        Bomb b = new Bomb("b");
        inv.add(b);
        // WRITE TEST
    }

    @Test
    public void testKey() {
        Character character = new Character(new Position(0, 0), "Kelly");
        Inventory inv = character.getInventory();
        Key k = new Key("silver key");
        inv.add(k);
        // WRITE TEST
    }
}
