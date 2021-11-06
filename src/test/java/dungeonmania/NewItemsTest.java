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
    public void testStandardBattleMidnightArmour() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Standard");
        Inventory inv = character.getInventory();
        MidnightArmour m = new MidnightArmour(character.getDungeon());
        inv.add(m);

        // spider battle - test using midnight armour as weapon and protection
        Spider spider = new Spider(new Position(0, 0), character.getDungeon());
        int expectedCharHealth = character.getHealth() - ((spider.getHealth() * spider.getAttackDamage()) / 20);
        int expectedEnemyHealth = spider.getHealth() - ((character.getHealth() * (character.getAttackDamage() + MidnightArmour.ADDED_ATTACK_DAMAGE)) / 5);
        state.battleEnemy(spider);
        assertEquals(character.getHealth(), expectedCharHealth);
        assertEquals(spider.getHealth(), expectedEnemyHealth);
        assertEquals(Arrays.asList(), inv.listInventory()); // used up
    }

    @Test
    public void testStandardBattleArmourPriority() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        CharacterState state = character.getCharacterState();
        assertEquals(state.getType(), "Standard");
        Inventory inv = character.getInventory();
        MidnightArmour m = new MidnightArmour(character.getDungeon());
        Armour a = new Armour(character.getDungeon(), Armour.DURABILITY);
        inv.add(m);
        inv.add(a);

        // spider battle - test using midnight armour as weapon and protection
        Spider spider = new Spider(new Position(0, 0), character.getDungeon());
        int expectedCharHealth = character.getHealth() - ((spider.getHealth() * spider.getAttackDamage()) / 20); // armour used
        int expectedEnemyHealth = spider.getHealth() - ((character.getHealth() * (character.getAttackDamage() + MidnightArmour.ADDED_ATTACK_DAMAGE)) / 5); // midnight armour used
        state.battleEnemy(spider);
        assertEquals(character.getHealth(), expectedCharHealth);
        assertEquals(spider.getHealth(), expectedEnemyHealth);
        assertEquals(Arrays.asList("midnight_armour", "armour"), inv.listInventory()); // both should have been used once, 1 use left each
    }

    @Test
    public void testAndurilTripleDamage() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Anduril a = new Anduril(new Position(-1, 0), character.getDungeon());
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
    public void testAndurilDestroySpawner() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Anduril a = new Anduril(new Position(-1, 0), character.getDungeon());
        character.move(Direction.LEFT);
        assertEquals(Arrays.asList("anduril"), inv.listInventory());

        // check anduril can destory spawner like regular sword
        ZombieToastSpawner z = new ZombieToastSpawner(new Position(-1, -1), character.getDungeon());
        character.destroySpawner(z);
        assertEquals(Arrays.asList(character), character.getDungeon().getEntities());
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
        assertThrows(InvalidActionException.class, () -> inv.craftMidnightArmour(character));
    }
    
}
