package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

// import java.util.ArrayList;
// import java.util.List;
import java.util.Arrays;

import dungeonmania.util.Position;
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
        assertEquals(Arrays.asList("treasure", "key", "wood"), inv.listInventory());
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
        inv.use("key", character);
        assertEquals(Arrays.asList("treasure", "wood"), inv.listInventory());
        inv.use("treasure", character);
        inv.use("wood", character);
        assertEquals(Arrays.asList(), inv.listInventory());
    }

    @Test
    public void useNonExistentItem() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Treasure i1 = new Treasure(new Position(0, 0), character.getDungeon());
        inv.add(i1);
        assertThrows(InvalidActionException.class, () -> inv.use("key", character));
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
        assertEquals(3, inv.count("treasure"));
        assertEquals(2, inv.count("key"));
        inv.use("treasure", character);
        assertEquals(2, inv.count("treasure"));
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
        assertEquals(Arrays.asList("key", "bow"), inv.listInventory());
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
        assertEquals(Arrays.asList("key", "shield"), inv.listInventory()); // test key remains
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
        assertEquals(Arrays.asList("shield"), inv.listInventory()); // test key remains
    }

    @Test
    public void craftShieldFail() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Key i1 = new Key(new Position(0, 0), character.getDungeon(), 1);
        inv.add(i1);
        assertThrows(InvalidActionException.class, () -> inv.craftShield(character));
        assertEquals(Arrays.asList("key"), inv.listInventory());
    }
    
}
