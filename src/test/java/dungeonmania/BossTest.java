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

public class BossTest {

    @Test
    public void fightAssassinStandard() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));

        // asssasin battle, no armour
        Assassin ass = new Assassin(new Position(0, 1), character.getDungeon());
        ass.giveArmour(0);
        assertEquals(Arrays.asList(character, ass), character.getDungeon().getEntities());

        // expect player dies if encounters assassin without weapons
        character.move(Direction.DOWN);
        assertEquals(Arrays.asList(ass), character.getDungeon().getEntities());
    }

    @Test
    public void fightAssassinWithSword() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Sword s = new Sword(new Position(0,0), character.getDungeon());
        inv.add(s);
        assertEquals(Arrays.asList("sword"), inv.listInventory());
        Armour a = new Armour(character.getDungeon(), Armour.DURABILITY);
        inv.add(a);

        // assassin battle - no armour
        Assassin ass = new Assassin(new Position(0, 1), character.getDungeon());
        ass.giveArmour(0);

        // expect assassin to die in battle now
        character.move(Direction.DOWN);
        assertEquals(Arrays.asList(character, s), character.getDungeon().getEntities());
    }

    @Test
    public void bribeAssassin() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Inventory inv = character.getInventory();
        Assassin ass = new Assassin(new Position(0, 3), character.getDungeon());

        // bribery should operate same as mercenary, with additional requirement of one ring

        // no treasure - fail
        assertThrows(InvalidActionException.class, () -> character.bribe(ass));

        // too far - fail
        Treasure t1 = new Treasure(new Position(0, 0), character.getDungeon());
        Treasure t2 = new Treasure(new Position(0, 0), character.getDungeon());
        inv.add(t1);
        inv.add(t2);
        assertEquals(Arrays.asList("treasure", "treasure"), inv.listInventory());
        assertEquals(false, ass.isAlly());
        assertThrows(InvalidActionException.class, () -> character.bribe(ass));

        // diagonal - fail
        ass.setPosition(new Position(1,1));
        assertThrows(InvalidActionException.class, () -> character.bribe(ass));
        ass.setPosition(new Position(-2,3));
        assertThrows(InvalidActionException.class, () -> character.bribe(ass));

        // within 1 cardinal square but no one ring - fail
        ass.setPosition(new Position(0,1));
        assertThrows(InvalidActionException.class, () -> character.bribe(ass));
        
        // within 2 cardinal squares - success
        OneRing ring = new OneRing(new Position(0, 0), character.getDungeon());
        inv.add(ring);
        assertEquals(Arrays.asList("treasure", "treasure", "one_ring"), inv.listInventory());
        assertEquals(false, ass.isAlly());
        character.bribe(ass);
        assertEquals(true, ass.isAlly());
        assertEquals(Arrays.asList("treasure"), inv.listInventory());
    }

    @Test
    public void assassinAllyBattle() {
        Player character = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Assassin ass1 = new Assassin(new Position(0, 3), character.getDungeon());
        ass1.setAlly(true);
        
        Mercenary merc2 = new Mercenary(new Position(0, 1), character.getDungeon());
        merc2.giveArmour(0);
        assertEquals(Arrays.asList(character, ass1, merc2), character.getDungeon().getEntities());
        int expectedCharHealth = character.getHealth() - ((merc2.getHealth() * merc2.getAttackDamage()) / 10);

        // should only take 1 round of battle to defeat now when with ally
        character.move(Direction.DOWN);
        assertEquals(Arrays.asList(character, ass1), character.getDungeon().getEntities());
        assertEquals(expectedCharHealth, character.getHealth());
    }

    @Test
    public void hydraMovementRange() {
        Player player = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Hydra h = new Hydra(new Position(0, 0), player.getDungeon());
        Door d = new Door(new Position(1, 0), h.getDungeon(), 1);
        Wall w = new Wall(new Position(-1, 0), h.getDungeon());
        ZombieToastSpawner z = new ZombieToastSpawner(new Position(0, 1), h.getDungeon());
        Boulder b1 = new Boulder(new Position(0, -1), h.getDungeon());
        Boulder b2 = new Boulder(new Position(0, -2), h.getDungeon());
        // test hydra should not be able to move past static entities like player
        h.updatePosition();
        assertEquals(new Position(0, 0), h.getPosition());
        h.updatePosition();
        assertEquals(new Position(0, 0), h.getPosition());
        h.updatePosition();
        assertEquals(new Position(0, 0), h.getPosition());
        h.updatePosition();
        assertEquals(new Position(0, 0), h.getPosition());
        h.updatePosition();
        assertEquals(new Position(0, 0), h.getPosition());
        h.updatePosition();
        assertEquals(new Position(0, 0), h.getPosition());
    }

    @Test
    public void testRandomHeadGeneration() {
        int attackFail = 0;
        int attackSuccess = 0;
        Player player = new Player(new Position(0, 0), new Dungeon("Dungeon", "Standard", "1"));
        Hydra h = new Hydra(new Position(0, 0), player.getDungeon());

        // use law of large numbers to test whether the health increase/decrease is 50%
        for (int i = 0; i < 1000; i++) {
            h.updateHealth(player);
            if (h.getHealth() > Hydra.ORIGINAL_HEALTH) {
                attackFail++;
            } else {
                attackSuccess++;
            }
            player.setHealth(Player.ORIGINAL_HEALTH_STANDARD);
            h.setHealth(Hydra.ORIGINAL_HEALTH);
        }

        assertTrue(attackFail >= 400 && attackFail <= 600);
        assertTrue(attackSuccess >= 400 && attackSuccess <= 600);
    }
    
}
