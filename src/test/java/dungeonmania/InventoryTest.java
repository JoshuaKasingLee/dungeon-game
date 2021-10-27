package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import dungeonmania.Inventory;
import dungeonmania.Character;
import dungeonmania.util.Position;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.exceptions.InvalidActionException;


public class InventoryTest {
    @Test
    public void addToInventory() {
        Inventory inv = new Inventory();
        Treasure i1 = new Treasure("i1");
        Key i2 = new Key("i2", 1);
        Wood i3 = new Wood("i3");
        inv.add(i1);
        inv.add(i2);
        inv.add(i3);
        assertEquals(Arrays.asList("Treasure", "Key", "Wood"), inv.listInventory());
    }

    @Test
    public void useInventoryItems() {
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Treasure i1 = new Treasure("i1");
        Key i2 = new Key("i2", 1);
        Wood i3 = new Wood("i3");
        inv.add(i1);
        inv.add(i2);
        inv.add(i3);
        inv.use("Key", character);
        assertEquals(Arrays.asList("Treasure", "Wood"), inv.listInventory());
        inv.use("Treasure", character);
        inv.use("Wood", character);
        assertEquals(Arrays.asList(), inv.listInventory());
    }

    @Test
    public void useNonExistentItem() {
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Treasure i1 = new Treasure("i1");
        inv.add(i1);
        assertThrows(InvalidActionException.class, () -> inv.use("Key", character));
    }

    @Test
    public void countInventory() {
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Treasure i1 = new Treasure("i1");
        Key i2 = new Key("i2", 1);
        Treasure i3 = new Treasure("i3");
        Treasure i4 = new Treasure("i4");
        Key i5 = new Key("i5", 2);
        inv.add(i1);
        inv.add(i2);
        inv.add(i3);
        inv.add(i4);
        inv.add(i5);
        assertEquals(3, inv.count("Treasure"));
        assertEquals(2, inv.count("Key"));
        inv.use("Treasure", character);
        assertEquals(2, inv.count("Treasure"));
    }

    @Test
    public void craftBow() {
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Arrow i1 = new Arrow("i1");
        Wood i2 = new Wood("i2");
        Arrow i3 = new Arrow("i3");
        Arrow i4 = new Arrow("i4");
        Key i5 = new Key("i5", 1);
        inv.add(i1);
        inv.add(i2);
        inv.add(i3);
        inv.add(i4);
        inv.add(i5);
        inv.craftBow(character);
        assertEquals(Arrays.asList("Key", "Bow"), inv.listInventory());
    }

    @Test
    public void craftBowFail() {
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Arrow i1 = new Arrow("i1");
        Wood i2 = new Wood("i2");
        inv.add(i1);
        inv.add(i2);
        assertThrows(InvalidActionException.class, () -> inv.craftBow(character));
    }

    @Test
    public void craftShield() {
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Wood i1 = new Wood("i1");
        Wood i2 = new Wood("i2");
        Treasure i3 = new Treasure("i3");
        Key i4 = new Key("i4", 1);
        inv.add(i1);
        inv.add(i2);
        inv.add(i3);
        inv.add(i4);
        inv.craftShield(character);
        assertEquals(Arrays.asList("Key", "Shield"), inv.listInventory()); // test key remains
    }

    @Test
    public void craftShieldWithKey() {
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Wood i1 = new Wood("i1");
        Wood i2 = new Wood("i2");
        Key i3 = new Key("i3", 1);
        inv.add(i1);
        inv.add(i2);
        inv.add(i3);
        inv.craftShield(character);
        assertEquals(Arrays.asList("Shield"), inv.listInventory()); // test key remains
    }

    @Test
    public void craftShieldFail() {
        Character character = new Character(new Position(0, 0), "Kelly", new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Key i1 = new Key("i1", 1);
        inv.add(i1);
        assertThrows(InvalidActionException.class, () -> inv.craftShield(character));
        assertEquals(Arrays.asList("Key"), inv.listInventory());
    }
    
}
