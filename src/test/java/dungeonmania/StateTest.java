package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.items.InvincibilityPotion;
import dungeonmania.items.InvisibilityPotion;
import dungeonmania.items.Sceptre;
import dungeonmania.moving_entities.Assassin;
import dungeonmania.moving_entities.Mercenary;
import dungeonmania.player.Inventory;
import dungeonmania.player.Player;
import dungeonmania.util.Position;

public class StateTest {
    @Test
    public void useInvisibilityPotion() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        assertEquals(Player.ORIGINAL_HEALTH_STANDARD, character.getHealth());
        Inventory inv = character.getInventory();
        InvisibilityPotion i = new InvisibilityPotion(new Position(0, 0), character.getDungeon());
        inv.add(i);
        assertEquals("Standard", character.getPlayerState().getType());
        inv.use("invisibility_potion", character);
        assertEquals("Invisible", character.getPlayerState().getType());
        // effect is tested in character.java
    }

    @Test
    public void useInvincibilityPotion() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        assertEquals(Player.ORIGINAL_HEALTH_STANDARD, character.getHealth());
        Inventory inv = character.getInventory();
        InvincibilityPotion i = new InvincibilityPotion(new Position(0, 0), character.getDungeon());
        inv.add(i);
        assertEquals("Standard", character.getPlayerState().getType());
        inv.use("invincibility_potion", character);
        assertEquals("Invincible", character.getPlayerState().getType());
        // effect is tested in character.java
    }

    @Test
    public void useSceptre() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Sceptre s = new Sceptre(character.getDungeon());
        inv.add(s);

        Mercenary m = new Mercenary(new Position(0, 0), character.getDungeon());
        assertTrue(m.isAlly() == false);
        character.useItem("sceptre");
        assertTrue(m.isAlly() == true);

        // tick 1
        m.getMercenaryState().updateState();
        assertTrue(m.isAlly() == true);
        assertEquals("MercControlled", m.getMercenaryState().getType());

        // tick 2
        m.getMercenaryState().updateState();
        assertTrue(m.isAlly() == true);

        // tick 3
        m.getMercenaryState().updateState();
        assertTrue(m.isAlly() == true);

        // tick 4
        m.getMercenaryState().updateState();
        assertTrue(m.isAlly() == true);

        // tick 5
        m.getMercenaryState().updateState();
        assertTrue(m.isAlly() == true);

        // tick 6
        m.getMercenaryState().updateState();
        assertTrue(m.isAlly() == true);

        // tick 7
        m.getMercenaryState().updateState();
        assertTrue(m.isAlly() == true);

        // tick 8
        m.getMercenaryState().updateState();
        assertTrue(m.isAlly() == true);

        // tick 9
        m.getMercenaryState().updateState();
        assertTrue(m.isAlly() == true);

        // tick 10
        m.getMercenaryState().updateState();
        assertTrue(m.isAlly() == false);
        assertEquals("MercStandard", m.getMercenaryState().getType());
    }

    @Test
    public void useSceptreOnAlly() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Sceptre s = new Sceptre(character.getDungeon());
        inv.add(s);

        Mercenary m = new Mercenary(new Position(0, 0), character.getDungeon());
        m.setAlly(true);
        assertTrue(m.isAlly() == true);
        character.useItem("sceptre");
        assertTrue(m.isAlly() == true);

        // tick 1
        m.getMercenaryState().updateState();
        assertTrue(m.isAlly() == true);
        assertEquals("MercControlled", m.getMercenaryState().getType());

        // tick 2
        m.getMercenaryState().updateState();
        assertTrue(m.isAlly() == true);

        // tick 3
        m.getMercenaryState().updateState();
        assertTrue(m.isAlly() == true);

        // tick 4
        m.getMercenaryState().updateState();
        assertTrue(m.isAlly() == true);

        // tick 5
        m.getMercenaryState().updateState();
        assertTrue(m.isAlly() == true);

        // tick 6
        m.getMercenaryState().updateState();
        assertTrue(m.isAlly() == true);

        // tick 7
        m.getMercenaryState().updateState();
        assertTrue(m.isAlly() == true);

        // tick 8
        m.getMercenaryState().updateState();
        assertTrue(m.isAlly() == true);

        // tick 9
        m.getMercenaryState().updateState();
        assertTrue(m.isAlly() == true);

        // tick 10 - check retains allied status
        m.getMercenaryState().updateState();
        assertTrue(m.isAlly() == true);
        assertEquals("MercStandard", m.getMercenaryState().getType());
    }

    @Test
    public void useSceptreOnMultipleMercs() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Sceptre s = new Sceptre(character.getDungeon());
        inv.add(s);

        Mercenary m1 = new Mercenary(new Position(0, 0), character.getDungeon());
        m1.setAlly(true);
        Mercenary m2 = new Mercenary(new Position(0, 0), character.getDungeon());
        Assassin a = new Assassin(new Position(0, 0), character.getDungeon()); // test assassin too
        character.useItem("sceptre");
        assertTrue(m1.isAlly() == true);
        assertTrue(m2.isAlly() == true);
        assertTrue(a.isAlly() == true);
        assertEquals("MercControlled", m1.getMercenaryState().getType());
        assertEquals("MercControlled", m2.getMercenaryState().getType());
        assertEquals("MercControlled", a.getMercenaryState().getType());

        for (Entity e : character.getDungeon().getEntities()) {
            if (e instanceof Mercenary) {
                Mercenary m = (Mercenary) e;
                // tick 10 times
                m.getMercenaryState().updateState();
                m.getMercenaryState().updateState();
                m.getMercenaryState().updateState();
                m.getMercenaryState().updateState();
                m.getMercenaryState().updateState();
                m.getMercenaryState().updateState();
                m.getMercenaryState().updateState();
                m.getMercenaryState().updateState();
                m.getMercenaryState().updateState();
                m.getMercenaryState().updateState();
            }
        }

        assertEquals("MercStandard", m1.getMercenaryState().getType());
        assertEquals("MercStandard", m2.getMercenaryState().getType());
        assertEquals("MercStandard", a.getMercenaryState().getType());

        // check original ally status is retained
        assertTrue(m1.isAlly() == true);
        assertTrue(m2.isAlly() == false);
        assertTrue(a.isAlly() == false);
    }
    
}
