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
        Item i1 = new Item("i1", "Treasure");
        Item i2 = new Item("i2", "Key");
        Item i3 = new Item("i3", "Wood");
        inv.add(i1);
        inv.add(i2);
        inv.add(i3);
        assertEquals(Arrays.asList("Treasure", "Key", "Wood"), inv.listInventory());
    }

    @Test
    public void useInventoryItems() {
        Character character = new Character(new Position(0, 0), "Kelly");
        Inventory inv = character.getInventory();
        Item i1 = new Item("i1", "Treasure");
        Item i2 = new Item("i2", "Key");
        Item i3 = new Item("i3", "Wood");
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
        Character character = new Character(new Position(0, 0), "Kelly");
        Inventory inv = character.getInventory();
        Item i1 = new Item("i1", "Treasure");
        inv.add(i1);
        assertThrows(InvalidActionException.class, () -> inv.use("Key", character));
    }

    @Test
    public void countInventory() {
        Character character = new Character(new Position(0, 0), "Kelly");
        Inventory inv = character.getInventory();
        Item i1 = new Item("i1", "Treasure");
        Item i2 = new Item("i2", "Key");
        Item i3 = new Item("i3", "Treasure");
        Item i4 = new Item("i4", "Treasure");
        Item i5 = new Item("i5", "Key");
        inv.add(i1);
        inv.add(i2);
        inv.add(i3);
        inv.add(i4);
        inv.add(i5);
        assertEquals(inv.count("Treasure"), 3);
        assertEquals(inv.count("Key"), 2);
        inv.use("Treasure", character);
        assertEquals(inv.count("Treasure"), 2);
    }

    @Test
    public void craftBow() {
        Character character = new Character(new Position(0, 0), "Kelly");
        Inventory inv = character.getInventory();
        Item i1 = new Item("i1", "Arrow");
        Item i2 = new Item("i2", "Wood");
        Item i3 = new Item("i3", "Arrow");
        Item i4 = new Item("i4", "Arrow");
        Item i5 = new Item("i5", "Key");
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
        Character character = new Character(new Position(0, 0), "Kelly");
        Inventory inv = character.getInventory();
        Item i1 = new Item("i1", "Arrow");
        Item i2 = new Item("i2", "Wood");
        inv.add(i1);
        inv.add(i2);
        assertThrows(InvalidActionException.class, () -> inv.craftBow(character));
    }

    @Test
    public void craftShield() {
        Character character = new Character(new Position(0, 0), "Kelly");
        Inventory inv = character.getInventory();
        Item i1 = new Item("i1", "Wood");
        Item i2 = new Item("i2", "Wood");
        Item i3 = new Item("i3", "Treasure");
        Item i4 = new Item("i4", "Key");
        inv.add(i1);
        inv.add(i2);
        inv.add(i3);
        inv.add(i4);
        inv.craftShield(character);
        assertEquals(Arrays.asList("Key", "Shield"), inv.listInventory()); // test key remains
    }

    @Test
    public void craftShieldFail() {
        Character character = new Character(new Position(0, 0), "Kelly");
        Inventory inv = character.getInventory();
        Item i1 = new Item("i1", "Key");
        inv.add(i1);
        assertThrows(InvalidActionException.class, () -> inv.craftShield(character));
        assertEquals(Arrays.asList("Key"), inv.listInventory());
    }
    
}
