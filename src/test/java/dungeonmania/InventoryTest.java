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
import dungeonmania.Player;
import dungeonmania.util.Position;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.exceptions.InvalidActionException;


public class InventoryTest {
    @Test
    public void addToInventory() {
        Inventory inv = new Inventory();
        Treasure i1 = new Treasure(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Key i2 = new Key(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"), 1);
        Wood i3 = new Wood(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        inv.add(i1);
        inv.add(i2);
        inv.add(i3);
        assertEquals(Arrays.asList("Treasure", "Key", "Wood"), inv.listInventory());
    }

    @Test
    public void useInventoryItems() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Treasure i1 = new Treasure(new Position(0, 0), character.getDungeon());
        Key i2 = new Key(new Position(0, 0), character.getDungeon(), 1);
        Wood i3 = new Wood(new Position(0, 0), character.getDungeon());
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
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Treasure i1 = new Treasure(new Position(0, 0), character.getDungeon());
        inv.add(i1);
        assertThrows(InvalidActionException.class, () -> inv.use("Key", character));
    }

    @Test
    public void countInventory() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Treasure i1 = new Treasure(new Position(0, 0), character.getDungeon());
        Key i2 = new Key(new Position(0, 0), character.getDungeon(), 1);
        Treasure i3 = new Treasure(new Position(0, 0), character.getDungeon());
        Treasure i4 = new Treasure(new Position(0, 0), character.getDungeon());
        Key i5 = new Key(new Position(0, 0), character.getDungeon(), 1);
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
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Arrow i1 = new Arrow(new Position(0, 0), character.getDungeon());
        Wood i2 = new Wood(new Position(0, 0), character.getDungeon());
        Arrow i3 = new Arrow(new Position(0, 0), character.getDungeon());
        Arrow i4 = new Arrow(new Position(0, 0), character.getDungeon());
        Key i5 = new Key(new Position(0, 0), character.getDungeon(), 1);
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
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Arrow i1 = new Arrow(new Position(0, 0), character.getDungeon());
        Wood i2 = new Wood(new Position(0, 0), character.getDungeon());
        inv.add(i1);
        inv.add(i2);
        assertThrows(InvalidActionException.class, () -> inv.craftBow(character));
    }

    @Test
    public void craftShield() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Wood i1 = new Wood(new Position(0, 0), character.getDungeon());
        Wood i2 = new Wood(new Position(0, 0), character.getDungeon());
        Treasure i3 = new Treasure(new Position(0, 0), character.getDungeon());
        Key i4 = new Key(new Position(0, 0), character.getDungeon(), 1);
        inv.add(i1);
        inv.add(i2);
        inv.add(i3);
        inv.add(i4);
        inv.craftShield(character);
        assertEquals(Arrays.asList("Key", "Shield"), inv.listInventory()); // test key remains
    }

    @Test
    public void craftShieldWithKey() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Wood i1 = new Wood(new Position(0, 0), character.getDungeon());
        Wood i2 = new Wood(new Position(0, 0), character.getDungeon());
        Key i3 = new Key(new Position(0, 0), character.getDungeon(), 1);
        inv.add(i1);
        inv.add(i2);
        inv.add(i3);
        inv.craftShield(character);
        assertEquals(Arrays.asList("Shield"), inv.listInventory()); // test key remains
    }

    @Test
    public void craftShieldFail() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Key i1 = new Key(new Position(0, 0), character.getDungeon(), 1);
        inv.add(i1);
        assertThrows(InvalidActionException.class, () -> inv.craftShield(character));
        assertEquals(Arrays.asList("Key"), inv.listInventory());
    }
    
}
