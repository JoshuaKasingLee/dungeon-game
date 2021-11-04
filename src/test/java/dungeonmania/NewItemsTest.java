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
import dungeonmania.util.Direction;

public class NewItemsTest {

    @Test
    public void useSunStoneManually() {
        Player player = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = player.getInventory();
        SunStone s = new SunStone(new Position(0, 0), player.getDungeon());
        inv.add(s);
        assertThrows(IllegalArgumentException.class, () -> player.useItem("sun_stone"));
    }

    @Test
    public void sunStoneBribeMercenary() {
        Player player = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = player.getInventory();
        SunStone s = new SunStone(new Position(0, 1), player.getDungeon());
        inv.add(s);
        Mercenary merc = new Mercenary(new Position(0, 2), player.getDungeon());
        assertEquals(false, merc.isAlly());

        // check sunstone bribing works, player retains sunstone
        player.bribe(merc);
        assertEquals(true, merc.isAlly());
        assertEquals(Arrays.asList("sun_stone"), inv.listInventory());

        // check if player has sunstone, treasure will not get used up in bribe
        merc.setAlly(false);
        Treasure t = new Treasure(new Position(0, 0), player.getDungeon());
        inv.add(t);
        player.bribe(merc);
        assertEquals(true, merc.isAlly());
        assertEquals(Arrays.asList("sun_stone", "treasure"), inv.listInventory());
    }

    @Test
    public void sunStoneBribeAssassin() {
        Player player = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = player.getInventory();
        SunStone s = new SunStone(new Position(0, 1), player.getDungeon());
        inv.add(s);
        Assassin ass = new Assassin(new Position(0, -2), player.getDungeon());
        assertEquals(false, ass.isAlly());

        // bribe still fails if no one_ring
        assertThrows(InvalidActionException.class, () -> player.bribe(ass));

        // check sunstone bribing works, player retains sunstone
        OneRing ring = new OneRing(new Position(0, 0), player.getDungeon());
        inv.add(ring);
        player.bribe(ass);
        assertEquals(true, ass.isAlly());
        assertEquals(Arrays.asList("sun_stone"), inv.listInventory());
    }

    @Test
    public void sunStoneOpenDoor() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Door d = new Door(new Position(1, 0), character.getDungeon(), 1);

        // door should not open since no key
        character.moveRight();
        assertEquals(new Position(0, 0), character.getPosition());

        // now door should open, key should remain in inventory
        Inventory inv = character.getInventory();
        SunStone s = new SunStone(new Position(0, 1), character.getDungeon());
        inv.add(s);
        Key k = new Key(new Position(0, 0), character.getDungeon(), 1);
        inv.add(k);
        character.moveRight();
        assertEquals(new Position(1, 0), character.getPosition());
        assertEquals(Arrays.asList("sun_stone", "key"), inv.listInventory());
    }

    @Test
    public void craftShieldWithSunStone() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Wood i1 = new Wood(new Position(0, 0), character.getDungeon());
        Wood i2 = new Wood(new Position(0, 0), character.getDungeon());
        SunStone s = new SunStone(new Position(0, 1), character.getDungeon());
        inv.add(i1);
        inv.add(i2);
        inv.add(s);
        inv.craftShield(character);
        assertEquals(Arrays.asList("shield"), inv.listInventory());
    }

    @Test
    public void craftingSunStonePriority() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Wood i1 = new Wood(new Position(0, 0), character.getDungeon());
        Wood i2 = new Wood(new Position(0, 0), character.getDungeon());
        Treasure i3 = new Treasure(new Position(0, 0), character.getDungeon());
        SunStone s = new SunStone(new Position(0, 1), character.getDungeon());
        inv.add(i1);
        inv.add(i2);
        inv.add(i3);
        inv.add(s);
        inv.craftShield(character);
        // sun-stone should be prioritised last in crafting
        assertEquals(Arrays.asList("sun_stone", "shield"), inv.listInventory());
    }
    
}
