package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;
import dungeonmania.util.Direction;


public class ItemTest {
    @Test
    public void useInactiveItems() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Treasure i1 = new Treasure(new Position(0, 0), character.getDungeon());
        Wood i2 = new Wood(new Position(0, 0), character.getDungeon());
        Arrow i3 = new Arrow(new Position(0, 0), character.getDungeon());
        inv.add(i1);
        inv.add(i2);
        inv.add(i3);
        assertDoesNotThrow(() -> inv.use("treasure", character));
        assertDoesNotThrow(() -> inv.use("wood", character));
        assertDoesNotThrow(() -> inv.use("arrow", character));
    }

    @Test
    public void useNonExistentItems() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Treasure i1 = new Treasure(new Position(0, 0), character.getDungeon());
        Wood i2 = new Wood(new Position(0, 0), character.getDungeon());
        inv.add(i1);
        inv.add(i2);
        assertThrows(InvalidActionException.class, () -> inv.use("arrow", character));
    }
    
    @Test
    public void testHealthPotion() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        assertEquals(Player.ORIGINAL_HEALTH_STANDARD, character.getHealth());
        Inventory inv = character.getInventory();
        HealthPotion potion = new HealthPotion(new Position(0, 0), character.getDungeon());
        inv.add(potion);
        character.setHealth(2);
        assertEquals(2, character.getHealth());
        inv.use("health_potion", character);
        assertEquals(Player.ORIGINAL_HEALTH_STANDARD, character.getHealth());
    }
    
    // weapon/protection effects are tested in BattleTest.java

    @Test
    public void testArmourLongevity() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Armour a = new Armour(character.getDungeon(), Armour.DURABILITY);
        inv.add(a);
        inv.use("armour", character);
        assertEquals(Arrays.asList("armour"), inv.listInventory());
        inv.use("armour", character);
        assertEquals(Arrays.asList(), inv.listInventory());
        
    }

    @Test
    public void testShieldLongevity() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Shield s = new Shield(character.getDungeon());
        inv.add(s);
        inv.use("shield", character);
        assertEquals(Arrays.asList("shield"), inv.listInventory());
        inv.use("shield", character);
        assertEquals(Arrays.asList("shield"), inv.listInventory());
        inv.use("shield", character);
        assertEquals(Arrays.asList(), inv.listInventory());
    }

    @Test
    public void useOneRing() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        assertEquals(Player.ORIGINAL_HEALTH_STANDARD, character.getHealth());
        Inventory inv = character.getInventory();
        OneRing ring = new OneRing(new Position(0, 0), character.getDungeon());
        inv.add(ring);
        character.setHealth(-1);
        assertEquals(-1, character.getHealth());
        inv.use("one_ring", character);
        assertEquals(Player.ORIGINAL_HEALTH_STANDARD, character.getHealth());
    }
    
    @Test
    public void testSword() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Sword s = new Sword(new Position(0, 0), character.getDungeon());
        inv.add(s);
        inv.use("sword", character);
    }

    @Test
    public void testBow() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Bow b = new Bow(character.getDungeon());
        inv.add(b);
        inv.use("bow", character);
    }

    @Test
    public void testBombExplosion() {
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");
        Player character = new Player(new Position(0, 0), dungeon);
        Bomb b = new Bomb(new Position(0, 0), dungeon);

        // within radius
        Sword s = new Sword(new Position(0, 1), dungeon);
        Spider spider = new Spider(new Position(-1, -2), dungeon);
        Door d = new Door(new Position(-3, 0), dungeon, 1);

        // not within radius
        Treasure t = new Treasure(new Position(3, 3), character.getDungeon());
        assertEquals(Arrays.asList(character, b, s, spider, d, t), character.getDungeon().getEntities());
        b.explode();

        // only character and treasure should remain
        assertEquals(Arrays.asList(character, t), character.getDungeon().getEntities());
    }

    @Test
    public void testKeyMatch() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Key k = new Key(new Position(0, 0), character.getDungeon(), 1);
        inv.add(k);
        inv.useKey(null, character);
        assertEquals(Arrays.asList("key"), inv.listInventory());
        Door d1 = new Door(new Position(0, 1), character.getDungeon(), 2);
        assertEquals(false, inv.useKey(d1, character));

        Door d2 = new Door(new Position(1, 0), character.getDungeon(), 1);
        assertTrue(inv.useKey(d2, character));
        assertEquals(Arrays.asList(), inv.listInventory());
    }

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
        new Door(new Position(1, 0), character.getDungeon(), 1);

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

    @Test
    public void testAndurilTripleDamage() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        new Anduril(new Position(-1, 0), character.getDungeon());
        Shield s = new Shield(character.getDungeon());
        character.move(Direction.LEFT);
        inv.add(s);
        assertEquals(Arrays.asList("anduril", "shield"), inv.listInventory());

        // compare spider and boss battle damage
        Spider spider = new Spider(new Position(0, 0), character.getDungeon());
        character.move(Direction.RIGHT);
        assertEquals(Arrays.asList(character), character.getDungeon().getEntities());

        Assassin ass = new Assassin(new Position(0, 1), character.getDungeon());
        ass.giveArmour(0);
        character.move(Direction.DOWN);
        assertEquals(Arrays.asList(character), character.getDungeon().getEntities());

        assertEquals((Spider.ORIGINAL_HEALTH - spider.getHealth())*3, Assassin.ORIGINAL_HEALTH - ass.getHealth());
    }

    
    @Test
    public void testAndurilHydraSuccess() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Anduril a = new Anduril(new Position(0, -1), character.getDungeon());
        a.setUsesLeft(10);
        character.move(Direction.UP);
        assertEquals(Arrays.asList("anduril"), inv.listInventory());

        // anduril is powerful enough to destroy hydra in one kill
        // hydra should always be killed
        new Hydra(new Position(0, 0), character.getDungeon());
        character.move(Direction.DOWN);
        assertEquals(Arrays.asList(character), character.getDungeon().getEntities());

        new Hydra(new Position(0, -1), character.getDungeon());
        character.setHealth(Player.ORIGINAL_HEALTH_STANDARD);
        character.move(Direction.UP);
        assertEquals(Arrays.asList(character), character.getDungeon().getEntities());

        new Hydra(new Position(-1, -1), character.getDungeon());
        character.setHealth(Player.ORIGINAL_HEALTH_STANDARD);
        character.move(Direction.LEFT);
        assertEquals(Arrays.asList(character), character.getDungeon().getEntities());

        new Hydra(new Position(0, -1), character.getDungeon());
        character.setHealth(Player.ORIGINAL_HEALTH_STANDARD);
        character.move(Direction.RIGHT);
        assertEquals(Arrays.asList(character), character.getDungeon().getEntities());

        new Hydra(new Position(0, 0), character.getDungeon());
        character.setHealth(Player.ORIGINAL_HEALTH_STANDARD);
        character.move(Direction.DOWN);
        assertEquals(Arrays.asList(character), character.getDungeon().getEntities());

        new Hydra(new Position(0, -1), character.getDungeon());
        character.setHealth(Player.ORIGINAL_HEALTH_STANDARD);
        character.move(Direction.UP);
        assertEquals(Arrays.asList(character), character.getDungeon().getEntities());

        new Hydra(new Position(-1, -1), character.getDungeon());
        character.setHealth(Player.ORIGINAL_HEALTH_STANDARD);
        character.move(Direction.LEFT);
        assertEquals(Arrays.asList(character), character.getDungeon().getEntities());

        new Hydra(new Position(0, -1), character.getDungeon());
        character.setHealth(Player.ORIGINAL_HEALTH_STANDARD);
        character.move(Direction.RIGHT);
        assertEquals(Arrays.asList(character), character.getDungeon().getEntities());
    }
}
