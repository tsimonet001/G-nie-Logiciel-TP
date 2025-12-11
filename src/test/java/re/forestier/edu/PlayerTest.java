package re.forestier.edu;

import re.forestier.edu.rpg.player;
import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.Item;
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
        ArrayList<Item> inventory = new ArrayList<>();
        player p = new player("Tom", "Avatar", "INVALID", 50, inventory);

        assertNull(p.playerName);
        assertNull(p.Avatar_name);
        assertNull(p.getAvatarClass());
        assertNull(p.money);
        assertNull(p.inventory);
    }

    @Test
    void testConstructorAllValidClasses() {
        ArrayList<Item> inventory = new ArrayList<>();

        player p1 = new player("Tom", "Avatar", "ARCHER", 50, inventory);
        assertNotNull(p1.playerName);

        player p2 = new player("Tom", "Avatar", "ADVENTURER", 50, inventory);
        assertNotNull(p2.playerName);

        player p3 = new player("Tom", "Avatar", "DWARF", 50, inventory);
        assertNotNull(p3.playerName);
        
        player p4 = new player("Tom", "Avatar", "GOBLIN", 50, inventory);
        assertNotNull(p4.playerName);
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

    @Test
    void testRemoveMoneyExactBoundary() {
        player p = new player("Tom", "Avatar", "ADVENTURER", 50, new ArrayList<>());
        p.removeMoney(50);
        assertEquals(0, p.money);
    }
    
    @Test
    void testGoblinCreation() {
        player p = new player("Gob", "Sneaky", "GOBLIN", 50, new ArrayList<>());
        assertEquals("GOBLIN", p.getAvatarClass());
        assertEquals(2, p.abilities.get("INT"));
        assertEquals(2, p.abilities.get("ATK"));
        assertEquals(1, p.abilities.get("ALC"));
    }

    @Test
    void testGoblinLevelUp() {
        player p = new player("Gob", "Sneaky", "GOBLIN", 50, new ArrayList<>());
        UpdatePlayer.addXp(p, 10);
        assertEquals(2, p.retrieveLevel());
        assertEquals(3, p.abilities.get("ATK"));
        assertEquals(4, p.abilities.get("ALC"));
    }

    // ========================================
    // NOUVEAUX TESTS pour Item
    // ========================================
    
    @Test
    void testAddItem_Success() {
        player p = new player("Tom", "Avatar", "ADVENTURER", 50, new ArrayList<>());
        Item sword = new Item("Épée", "Une épée", 5, 100);
        
        boolean result = p.addItem(sword);
        
        assertTrue(result, "L'ajout doit réussir");
        assertEquals(1, p.inventory.size());
        assertEquals(5, p.getCurrentWeight());
    }
    
    @Test
    void testAddItem_TooHeavy() {
        player p = new player("Tom", "Avatar", "ADVENTURER", 50, new ArrayList<>());
        p.setMaxWeight(10);
        
        Item heavyItem = new Item("Enclume", "Très lourde", 15, 500);
        
        boolean result = p.addItem(heavyItem);
        
        assertFalse(result, "L'ajout doit échouer car trop lourd");
        assertEquals(0, p.inventory.size());
    }
    
    @Test
    void testAddItem_ExactWeight() {
        player p = new player("Tom", "Avatar", "ADVENTURER", 50, new ArrayList<>());
        p.setMaxWeight(10);
        
        Item item = new Item("Objet", "Exactement le max", 10, 100);
        
        boolean result = p.addItem(item);
        
        assertTrue(result, "L'ajout doit réussir à la limite exacte");
        assertEquals(10, p.getCurrentWeight());
    }
    
    @Test
    void testSellItem_Success() {
        player p = new player("Tom", "Avatar", "ADVENTURER", 50, new ArrayList<>());
        Item sword = new Item("Épée", "Une épée", 5, 100);
        p.addItem(sword);
        
        int moneyBefore = p.money;
        boolean result = p.sell(sword);
        
        assertTrue(result, "La vente doit réussir");
        assertEquals(0, p.inventory.size());
        assertEquals(moneyBefore + 100, p.money);
    }
    
    @Test
    void testSellItem_NotInInventory() {
        player p = new player("Tom", "Avatar", "ADVENTURER", 50, new ArrayList<>());
        Item sword = new Item("Épée", "Une épée", 5, 100);
        
        int moneyBefore = p.money;
        boolean result = p.sell(sword);
        
        assertFalse(result, "La vente doit échouer");
        assertEquals(moneyBefore, p.money);
    }
    
    @Test
    void testGetCurrentWeight_Empty() {
        player p = new player("Tom", "Avatar", "ADVENTURER", 50, new ArrayList<>());
        assertEquals(0, p.getCurrentWeight());
    }
    
    @Test
    void testGetCurrentWeight_MultipleItems() {
        player p = new player("Tom", "Avatar", "ADVENTURER", 50, new ArrayList<>());
        p.addItem(new Item("Épée", "Une épée", 5, 100));
        p.addItem(new Item("Bouclier", "Un bouclier", 8, 150));
        p.addItem(new Item("Potion", "Une potion", 1, 20));
        
        assertEquals(14, p.getCurrentWeight());
    }
    
    @Test
    void testMaxWeight_GetSet() {
        player p = new player("Tom", "Avatar", "ADVENTURER", 50, new ArrayList<>());
        assertEquals(50, p.getMaxWeight(), "Poids max par défaut");
        
        p.setMaxWeight(100);
        assertEquals(100, p.getMaxWeight());
    }
}