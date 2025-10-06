package re.forestier.edu;

import re.forestier.edu.rpg.player;
import re.forestier.edu.rpg.UpdatePlayer;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testCreationPlayer() {
        player p = new player("Tom", "Avatar", "ADVENTURER", 100, new ArrayList<>());
        assertEquals("Tom", p.playerName);
        assertEquals("Avatar", p.Avatar_name);
        assertEquals("ADVENTURER", p.getAvatarClass());
        assertEquals(100, p.money);
        assertNotNull(p.inventory);
        assertNotNull(p.abilities);
    }

    @Test
    void testConstructorWithInvalidClass() {
        ArrayList<String> inventory = new ArrayList<>();
        player p = new player("Tom", "Avatar", "INVALID", 50, inventory);

        assertNull(p.playerName);
        assertNull(p.Avatar_name);
        assertNull(p.getAvatarClass());
        assertNull(p.money);
        assertNull(p.inventory);
    }

    @Test
    void testConstructorAllValidClasses() {
        ArrayList<String> inventory = new ArrayList<>();

        player p1 = new player("Tom", "Avatar", "ARCHER", 50, inventory);
        assertNotNull(p1.playerName);

        player p2 = new player("Tom", "Avatar", "ADVENTURER", 50, inventory);
        assertNotNull(p2.playerName);

        player p3 = new player("Tom", "Avatar", "DWARF", 50, inventory);
        assertNotNull(p3.playerName);
    }

    @Test
    void testAddAndRemoveMoney() {
        player p = new player("Tom", "Avatar", "ADVENTURER", 50, new ArrayList<>());
        p.addMoney(30);
        assertEquals(80, p.money);

        p.removeMoney(20);
        assertEquals(60, p.money);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> p.removeMoney(100));
        assertEquals("Player can't have a negative money!", exception.getMessage());
    }

    @Test
    void testRetrieveLevel() {
        player p = new player("Tom", "Avatar", "ADVENTURER", 50, new ArrayList<>());

        assertEquals(1, p.retrieveLevel());

        UpdatePlayer.addXp(p, 10);
        assertEquals(2, p.retrieveLevel());

        UpdatePlayer.addXp(p, 17);
        assertEquals(3, p.retrieveLevel());

        UpdatePlayer.addXp(p, 30);
        assertEquals(4, p.retrieveLevel());

        UpdatePlayer.addXp(p, 54);
        assertEquals(5, p.retrieveLevel());
    }

    @Test
    void testGetXp() {
        player p = new player("Tom", "Avatar", "ADVENTURER", 50, new ArrayList<>());
        UpdatePlayer.addXp(p, 15);
        assertEquals(15, p.getXp());
    }
}
