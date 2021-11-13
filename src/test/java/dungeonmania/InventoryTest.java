package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

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

    @Test
    public void craftMidnightArmour() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Armour a = new Armour(character.getDungeon(), Armour.DURABILITY);
        SunStone s = new SunStone(new Position(0, 1), character.getDungeon());
        Wood w = new Wood(new Position(0, 0), character.getDungeon());
        inv.add(a);
        inv.add(s);
        inv.add(w);
        inv.craftMidnightArmour(character);
        assertEquals(Arrays.asList("wood", "midnight_armour"), inv.listInventory());
        // craft another one
        Armour a1 = new Armour(character.getDungeon(), Armour.DURABILITY);
        SunStone s1 = new SunStone(new Position(0, 1), character.getDungeon());
        inv.add(a1);
        inv.add(s1);
        inv.craftMidnightArmour(character);
        assertEquals(Arrays.asList("wood", "midnight_armour", "midnight_armour"), inv.listInventory());
    }

    @Test
    public void craftMidnightArmourZombieFail() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Armour a = new Armour(character.getDungeon(), Armour.DURABILITY);
        SunStone s = new SunStone(new Position(0, 1), character.getDungeon());
        inv.add(a);
        inv.add(s);
        // build fails due to presence of zombie
        ZombieToast z = new ZombieToast(new Position(0, 0), character.getDungeon());
        assertThrows(InvalidActionException.class, () -> inv.craftMidnightArmour(character));
        assertEquals(Arrays.asList("armour", "sun_stone"), inv.listInventory());
        // once zombie is removed, build works
        character.getDungeon().removeEntity(z);
        inv.craftMidnightArmour(character);
        assertEquals(Arrays.asList("midnight_armour"), inv.listInventory());
    }

    @Test
    public void craftMidnightArmourMaterialFail() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Armour a = new Armour(character.getDungeon(), Armour.DURABILITY);
        Wood w = new Wood(new Position(0, 0), character.getDungeon());
        inv.add(a);
        inv.add(w);
        assertThrows(InvalidActionException.class, () -> inv.craftMidnightArmour(character));
        assertEquals(Arrays.asList("armour", "wood"), inv.listInventory());
    }

    @Test
    public void craftSceptre() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();

        // test crafting with different materials
        Wood w = new Wood(new Position(0, 0), character.getDungeon());
        Treasure t = new Treasure(new Position(0, 0), character.getDungeon());
        SunStone s = new SunStone(new Position(0, 1), character.getDungeon());
        inv.add(w);
        inv.add(t);
        inv.add(s);
        inv.craftSceptre(character);
        assertEquals(Arrays.asList("sceptre"), inv.listInventory());

        // two sunstones, no treasure
        Wood w1 = new Wood(new Position(0, 0), character.getDungeon());
        SunStone s1 = new SunStone(new Position(0, 1), character.getDungeon());
        SunStone s2 = new SunStone(new Position(0, 1), character.getDungeon());
        inv.add(w1);
        inv.add(s1);
        inv.add(s2);
        assertEquals(Arrays.asList("sceptre", "wood", "sun_stone", "sun_stone"), inv.listInventory());
        inv.craftSceptre(character);
        assertEquals(Arrays.asList("sceptre", "sceptre"), inv.listInventory());

        // arrows and keys
        Arrow arrow1 = new Arrow(new Position(0, 0), character.getDungeon());
        Arrow arrow2 = new Arrow(new Position(0, 0), character.getDungeon());
        Key k = new Key(new Position(0, 0), character.getDungeon(), 1);
        SunStone s3 = new SunStone(new Position(0, 1), character.getDungeon());
        inv.add(arrow1);
        inv.add(arrow2);
        inv.add(k);
        inv.add(s3);
        inv.craftSceptre(character);
        assertEquals(Arrays.asList("sceptre", "sceptre", "sceptre"), inv.listInventory());
    }

    @Test
    public void craftSceptreFail() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Treasure t = new Treasure(new Position(0, 0), character.getDungeon());
        SunStone s = new SunStone(new Position(0, 1), character.getDungeon());
        inv.add(t);
        inv.add(s);
        assertThrows(InvalidActionException.class, () -> inv.craftSceptre(character));
    }
    
}
