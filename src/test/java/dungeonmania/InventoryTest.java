package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import dungeonmania.Inventory;
import dungeonmania.response.models.ItemResponse;


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
    public void removeFromInventory() {
        Inventory inv = new Inventory();
        Item i1 = new Item("i1", "Treasure");
        Item i2 = new Item("i2", "Key");
        Item i3 = new Item("i3", "Wood");
        inv.add(i1);
        inv.add(i2);
        inv.add(i3);
        inv.remove(i2);
        assertEquals(Arrays.asList("Treasure", "Wood"), inv.listInventory());
        inv.remove(i1);
        inv.remove(i3);
        assertEquals(Arrays.asList(), inv.listInventory());
    }

    @Test
    public void countInventory() {
        Inventory inv = new Inventory();
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
        assertEquals(inv.countItem(i1), 3);
        assertEquals(inv.countItem(i2), 2);

    }
    
}
