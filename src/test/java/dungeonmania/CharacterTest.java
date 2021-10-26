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
    public void useValidItems() {
        Character character = new Character(new Position(0, 0), "Kelly");
        Inventory inv = character.getInventory();
        HealthPotion i1 = new HealthPotion("i1");
        InvisibilityPotion i2 = new InvisibilityPotion("my_potion");
        InvincibilityPotion i3 = new InvincibilityPotion("my_potion");
        Bomb i4 = new Bomb("i4");
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
        Character character = new Character(new Position(0, 0), "Kelly");
        Inventory inv = character.getInventory();
        Treasure i1 = new Treasure("i1");
        Key i2 = new Key("i2");
        Wood i3 = new Wood("i3");
        Arrow i4 = new Arrow("i4");
        Shield i5 = new Shield("shield");
        Armour i6 = new Armour("a");
        Bow i7 = new Bow("b");
        Sword i8 = new Sword("s");
        OneRing i9 = new OneRing("one_ring");
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
    
}
