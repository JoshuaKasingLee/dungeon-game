/// ASSUMPTIONS ///
Kelly, Cyrus, Josh & Sami
Team Avocado

/// ITEMS ///
- Armour can only be attained by battling an armoured enemy
- Bows and shields can only be attained via crafting
- Durabilities:
    Armour - 2
    Sword - 3
    Shield - 3
    Bow - 2
- Bomb radius is less than or equal to 3 positions away
- Shield completely deflects an enemy's attack
- Sword will deduct 10 health points from enemy in any battle
- Only one invicibility/invisibility potion can be taken at a time - player is always in state of most recently consumed potion
- Armours and shields will not be used in invincible state, since enemies cannot damage their health already
- Invincibility/invisibility time limit = 10 ticks

/// PLAYER ///
- Original starting health is 10, unless hard mode - then is 7
- Weapons get "used" when used to destroy zombie toast spawner
- Only the most recently used potion state is active if one potion is used before another potion is finished
- The following health/attack damages were assumed:
    Health (Standard/Peaceful): 10
    Health (Hard): 7
    Attack Damage (Standard/Peaceful): 3

/// MOVING ///
- Player cannot move on top of zombie toast spawner
- Zombies & mercenaries are also able to push boulders, etc. -> can do everything a player can do, except open a door
- Items are picked after a player finishes a fight with enemies in the current position
- If there is a boulder above the spider's spawning position, the spider will not move up until it is clear

/// BATTLES ///
- Player and enemy health point deductions occur simultaneously (i.e. doesn't matter if player or enemy strikes first, result will be the same)
- Only one weapon and one protection item can be used per round -> e.g. no point using armour if shield already deflects all attack
- And for above reasons:
    - Sword will be prioritised over bow
    - Shield will be prioritised over armour
- Using sword takes precedence over enemy's armour
- An enemy's hits will also deduct health points from player, and not allied mercenaries

/// ENEMIES ///
- Zombie Toast has 25% chance spawning with armour
- Mercenary has 50% chance spawning with armour
- The following constants were assumed:
    - Spider
        Health: 5 
        Attack Damage: 2
    - ZombieToast
        Health: 7
        Attack Damage: 3
    - Mercenary
        Health: 10
        Attack Damage: 4
        Battle Radius: 3

/// MERCENARIES ///
- Can be bribed using 1 treasure
- Bribing only occurs if within 2 cardinally adjacent tiles of the original player position (e.g. cannot bribe 1 square diagonally)
- Battle radius is 3 positions
- When within radius of a fighting player, position is updated once per battle, not per round (i.e. to simulate moving twice)

// STATIC ENTITIES ///
- Boulders can be pushed onto other items/moving entites
- Boulders never exist on the edge of the dungeon (i.e. there is always a wall border)
- Only two portals can exist for the same colour 
- Only player can move through doors/portals etc. 
- If portal doesn't work, player will just step onto the portal 
- Only 1 player exists 
- Constants:
    - Standard zombieSpawnerTimer = 20 ticks 
    - Hard zombieSpawnerTimer = 15 ticks 
    - Peaceful zombieSpawnerTimer = 0 
- If there are no cardinally adjacent squares next to a zombie spawner, it won't spawn any zombies 
- The bomb does not explode if the boulder is already on a switch adjacent to it 
- Doors are created in conjunction to a key 
- When a zombie spawner is destroyed, it is removed off the map 
- Key must be an integer 
- There is only 1 exit


//////// MILESTONE 3 ASSUMPTIONS /////////

/// BOSSES ///
- The following constants were assumed:
    - Assassin
        Health: 20
        Attack Damage: 6
    - Hydra
        Health: 15
        Attack Damage: 4

/// NEW ITEMS ///

- If sunstone can be used in place of a key/bribe, it will get used first (i.e. key or treasure will remain in inventory if exists)
- Where there is an option, sunstone is used last in crafting - we want to "save" it since it is the most valuable
- Sceptre only has 1 use
- Sceptre is "used" like a potion
- Buildables can only be built (i.e. can't be picked up from the ground)
- Crafting midnight armour returns InvalidActionException if zombie is in dungeon
- Midnight Armour has same durability as armour (2 uses)
- Midnight Armour adds 2 attack damage points points to player if used as a weapon
- Midnight Armour has same functionaltiy as Armour if used as protection
- Use midnight armour last when player has multiple weapons/protection choices as it is most valuable
- Anduril has same number of uses as sword (since it is a subclass)
- Anduril gets used last if used for destroying zombie toast spawner, because it is the most valuable
- Crafting sceptre: list of priorities:
    - Wood > Arrow
    - Treasure > Key > Sunstone