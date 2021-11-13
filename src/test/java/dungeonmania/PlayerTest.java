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
import dungeonmania.items.Anduril;
import dungeonmania.items.Armour;
import dungeonmania.static_entities.Boulder;
import dungeonmania.static_entities.Door;
import dungeonmania.static_entities.Wall;
import dungeonmania.static_entities.ZombieToastSpawner;
import dungeonmania.player.Inventory;
import dungeonmania.player.Player;
import dungeonmania.player.PlayerState;
import dungeonmania.moving_entities.Mercenary;
import dungeonmania.moving_entities.Spider;
import dungeonmania.moving_entities.ZombieToast;
import dungeonmania.items.Arrow;
import dungeonmania.items.Bomb;
import dungeonmania.items.Bow;
import dungeonmania.items.HealthPotion;
import dungeonmania.items.InvincibilityPotion;
import dungeonmania.items.InvisibilityPotion;
import dungeonmania.items.Key;
import dungeonmania.items.OneRing;
import dungeonmania.items.Shield;
import dungeonmania.items.Sword;
import dungeonmania.items.Treasure;
import dungeonmania.items.Wood;
import dungeonmania.util.Position;
import dungeonmania.util.Direction;


public class PlayerTest {
    @Test
    public void testValidMovement() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        character.moveUp();
        assertEquals(new Position(0, -1), character.getPosition());
        character.moveDown();
        assertEquals(new Position(0, 0), character.getPosition());
        character.moveLeft();
        assertEquals(new Position(-1, 0), character.getPosition());
        character.moveRight();
        assertEquals(new Position(0, 0), character.getPosition());
        character.moveUp();
        character.moveUp();
        character.moveUp();
        character.moveRight();
        character.moveRight();
        assertEquals(new Position(2, -3), character.getPosition());
    }

    @Test
    public void testInvalidMovement() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        new Door(new Position(1, 0), character.getDungeon(), 1);
        character.moveRight();
        assertEquals(new Position(0, 0), character.getPosition());
        new Wall(new Position(-1, 0), character.getDungeon());
        character.moveLeft();
        assertEquals(new Position(0, 0), character.getPosition());
        new ZombieToastSpawner(new Position(0, 1), character.getDungeon());
        character.moveDown();
        assertEquals(new Position(0, 0), character.getPosition());
        new Boulder(new Position(0, -1), character.getDungeon());
        character.moveUp();
        assertEquals(new Position(0, -1), character.getPosition());
    }

    @Test
    public void testBlockedBoulders() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Boulder b1 = new Boulder(new Position(0, 1), character.getDungeon());
        ZombieToastSpawner z = new ZombieToastSpawner(new Position(0, 2), character.getDungeon());
        assertEquals(Arrays.asList(character, b1, z), character.getDungeon().getEntities());
        character.move(Direction.DOWN);
        assertEquals(new Position(0, 0), character.getPosition());
    }

    @Test
    public void testUnlockDoorMovement() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        new Door(new Position(1, 0), character.getDungeon(), 1);
        character.moveRight();
        assertEquals(new Position(0, 0), character.getPosition());
        Inventory inv = character.getInventory();
        Key k = new Key(new Position(0, 0), character.getDungeon(), 1);
        inv.add(k);
        // door should now unlock
        character.moveRight();
        assertEquals(new Position(1, 0), character.getPosition());
    }

    @Test
    public void pickUpItems() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Sword s = new Sword(new Position(0, 1), character.getDungeon());
        assertEquals(Arrays.asList(character, s), character.getDungeon().getEntities());
        character.move(Direction.DOWN);
        assertEquals(Arrays.asList("sword"), inv.listInventory());
        assertEquals(Arrays.asList(character), character.getDungeon().getEntities());
        new Treasure(new Position(1, 3), character.getDungeon());
        character.move(Direction.DOWN);
        character.move(Direction.DOWN);
        character.move(Direction.RIGHT);
        assertEquals(Arrays.asList("sword", "treasure"), inv.listInventory());
    }

    @Test
    public void useValidItems() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        HealthPotion i1 = new HealthPotion(new Position(0, 0), character.getDungeon());
        InvisibilityPotion i2 = new InvisibilityPotion(new Position(0, 0), character.getDungeon());
        InvincibilityPotion i3 = new InvincibilityPotion(new Position(0, 0), character.getDungeon());
        Bomb i4 = new Bomb(new Position(0, 0), character.getDungeon());
        inv.add(i1);
        inv.add(i2);
        inv.add(i3);
        inv.add(i4);
        assertDoesNotThrow(() -> character.useItem("health_potion"));
        assertDoesNotThrow(() -> character.useItem("invisibility_potion"));
        assertDoesNotThrow(() -> character.useItem("invincibility_potion"));
        assertDoesNotThrow(() -> character.useItem("bomb"));
        assertDoesNotThrow(() -> character.useItem(""));
    }

    @Test
    public void useInvalidItems() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Treasure i1 = new Treasure(new Position(0, 0), character.getDungeon());
        Key i2 = new Key(new Position(0, 0), character.getDungeon(), 1);
        Wood i3 = new Wood(new Position(0, 0), character.getDungeon());
        Arrow i4 = new Arrow(new Position(0, 0), character.getDungeon());
        Shield i5 = new Shield(character.getDungeon());
        Armour i6 = new Armour(character.getDungeon(), Armour.DURABILITY);
        Bow i7 = new Bow(character.getDungeon());
        Sword i8 = new Sword(new Position(0, 0), character.getDungeon());
        OneRing i9 = new OneRing(new Position(0, 0), character.getDungeon());
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

    @Test
    public void characterDeath() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        character.setHealth(1);
        Mercenary merc = new Mercenary(new Position(0, 1), character.getDungeon());
        assertEquals(Arrays.asList(character, merc), character.getDungeon().getEntities());
        character.move(Direction.DOWN);
        // fight should eliminate character
        assertEquals(Arrays.asList(merc), character.getDungeon().getEntities());
    }

    @Test
    public void revivalWithOneRing() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        character.setHealth(1);
        Inventory inv = character.getInventory();
        OneRing ring = new OneRing(new Position(0, 0), character.getDungeon());
        inv.add(ring); // doesn't remove from dungeon
        assertEquals(Arrays.asList("one_ring"), inv.listInventory());

        Mercenary merc = new Mercenary(new Position(0, 1), character.getDungeon());
        assertEquals(Arrays.asList(character, ring, merc), character.getDungeon().getEntities());
        character.move(Direction.DOWN);
        // fight should happen, one ring should be used since merc can kill character
        assertEquals(Arrays.asList(character, ring, merc), character.getDungeon().getEntities());
        assertEquals(Arrays.asList(), inv.listInventory());
        assertEquals(Player.ORIGINAL_HEALTH_STANDARD, character.getHealth());
    }

    @Test
    public void bribeMercenary() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Treasure t1 = new Treasure(new Position(0, 0), character.getDungeon());
        Treasure t2 = new Treasure(new Position(0, 0), character.getDungeon());
        inv.add(t1);
        inv.add(t2);
        assertEquals(Arrays.asList("treasure", "treasure"), inv.listInventory());

        // too far - fail
        Mercenary merc1 = new Mercenary(new Position(0, 3), character.getDungeon());
        assertEquals(false, merc1.isAlly());
        assertThrows(InvalidActionException.class, () -> character.bribe(merc1));

        // diagonal - fail
        merc1.setPosition(new Position(1,1));
        assertThrows(InvalidActionException.class, () -> character.bribe(merc1));
        merc1.setPosition(new Position(-2,3));
        assertThrows(InvalidActionException.class, () -> character.bribe(merc1));

        // within 1 cardinal square - sucess
        merc1.setPosition(new Position(0,1));
        character.bribe(merc1);
        assertEquals(true, merc1.isAlly());
        assertEquals(Arrays.asList("treasure"), inv.listInventory());
        
        // within 2 cardinal squares - success
        Mercenary merc2 = new Mercenary(new Position(0, 1), character.getDungeon());
        assertEquals(false, merc2.isAlly());
        character.bribe(merc2);
        assertEquals(true, merc2.isAlly());
        assertEquals(Arrays.asList(), inv.listInventory());
    }

    @Test
    public void bribeMercenaryNoTreasure() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        // no treasure - fail
        Mercenary merc1 = new Mercenary(new Position(0, 1), character.getDungeon());
        assertEquals(false, merc1.isAlly());
        assertThrows(InvalidActionException.class, () -> character.bribe(merc1));
    }

    @Test
    public void destroyZombieToastSpawnerSword() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Sword s = new Sword(new Position(0, 1), character.getDungeon());
        inv.add(s); // doesn't remove from dungeon
        assertEquals(Arrays.asList("sword"), inv.listInventory());

        // too far - should fail
        ZombieToastSpawner z = new ZombieToastSpawner(new Position(0, 2), character.getDungeon());
        assertEquals(Arrays.asList(character, s, z), character.getDungeon().getEntities());
        assertThrows(InvalidActionException.class, () -> character.destroySpawner(z));

        // not cardinally adjacent - should fail
        z.setPosition(new Position(1,1));
        assertThrows(InvalidActionException.class, () -> character.destroySpawner(z));

        // now passes
        z.setPosition(new Position(0,1));
        character.destroySpawner(z);
        assertEquals(Arrays.asList(character, s), character.getDungeon().getEntities());
    }

    @Test
    public void destroyZombieToastSpawnerBow() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Bow b = new Bow(character.getDungeon());
        inv.add(b);
        assertEquals(Arrays.asList("bow"), inv.listInventory());

        // check bow works
        ZombieToastSpawner z = new ZombieToastSpawner(new Position(0, 2), character.getDungeon());
        z.setPosition(new Position(0,1));
        character.destroySpawner(z);
        assertEquals(Arrays.asList(character), character.getDungeon().getEntities());
    }

    @Test
    public void destroyZombieToastSpawnerAttempt() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        ZombieToastSpawner z = new ZombieToastSpawner(new Position(0, 1), character.getDungeon());
        // no weapon - should fail
        assertThrows(InvalidActionException.class, () -> character.destroySpawner(z));
    }

    @Test
    public void testAndurilDestroySpawner() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        new Anduril(new Position(-1, 0), character.getDungeon());
        character.move(Direction.LEFT);
        assertEquals(Arrays.asList("anduril"), inv.listInventory());

        // check anduril can destory spawner like regular sword
        ZombieToastSpawner z = new ZombieToastSpawner(new Position(-1, -1), character.getDungeon());
        character.destroySpawner(z);
        assertEquals(Arrays.asList(character), character.getDungeon().getEntities());
    }

    @Test
    public void testPeacefulBattleHealth() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Peaceful", "1"));
        PlayerState state = character.getPlayerState();
        assertEquals(state.getType(), "Standard");
       
        // spider battle
        Spider spider = new Spider(new Position(0, 0), character.getDungeon());
        int expectedEnemyHealth = spider.getHealth() - ((character.getHealth() * character.getAttackDamage()) / 5);
        state.battleEnemy(spider); // if spider is killed it may not exist after this
        assertTrue(character.getHealth() == Player.ORIGINAL_HEALTH_STANDARD);
        assertEquals(spider.getHealth(), expectedEnemyHealth);

        ZombieToast zombie = new ZombieToast(new Position(0, 0), character.getDungeon());
        state.battleEnemy(zombie);
        assertEquals(character.getHealth(), Player.ORIGINAL_HEALTH_STANDARD);
    }

    @Test
    public void testGamemodeHardHealth() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Hard", "1"));
        Inventory inv = character.getInventory();
        PlayerState state = character.getPlayerState();
        assertEquals(state.getType(), "Standard");

        InvincibilityPotion i1 = new InvincibilityPotion(new Position(0, 0), character.getDungeon());
        inv.add(i1);
        character.useItem("invincibility_potion");
        state = character.getPlayerState();
        assertEquals(state.getType(), "Standard");

        assertTrue(character.getHealth() == Player.ORIGINAL_HEALTH_HARD);
        character.setHealth(0);
        assertTrue(character.getHealth() == 0);
        inv.add(new HealthPotion(new Position(0, 0), character.getDungeon()));
        character.useItem("health_potion");
        assertTrue(character.getHealth() == Player.ORIGINAL_HEALTH_HARD);
    }
}
