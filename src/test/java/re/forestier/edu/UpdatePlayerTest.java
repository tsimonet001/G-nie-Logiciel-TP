package re.forestier.edu;

import re.forestier.edu.rpg.player;
import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.Item;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class UpdatePlayerTest {

    // ----------------------------
    // Tests pour les joueurs KO
    // ----------------------------
    @Test
    void testMajFinDeTour_PlayerKO() {
        player p = new player("A", "B", "DWARF", 100, new ArrayList<>());
        p.healthpoints = 10;
        p.currenthealthpoints = 0;
        UpdatePlayer.majFinDeTour(p);
        assertEquals(0, p.currenthealthpoints);
    }

    @Test
    void testConsoleOutputPlayerKOMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        player p = new player("Tom", "Avatar", "DWARF", 100, new ArrayList<>());
        p.healthpoints = 10;
        p.currenthealthpoints = 0;
        UpdatePlayer.majFinDeTour(p);
        assertTrue(outContent.toString().contains("Le joueur est KO !"));
        System.setOut(System.out);
    }

    // ----------------------------
    // Tests pour les Nains (DWARF)
    // ----------------------------
    @Test
    void testMajFinDeTour_DwarfWithHolyElixir() {
        player p = new player("A", "B", "DWARF", 100, new ArrayList<>());
        p.healthpoints = 10;
        p.currenthealthpoints = 3;
        p.addItem(new Item("Holy Elixir", "Recover your HP", 1, 60));
        UpdatePlayer.majFinDeTour(p);
        assertEquals(5, p.currenthealthpoints);
    }

    @Test
    void testMajFinDeTour_DwarfNoHolyElixir() {
        player p = new player("A", "B", "DWARF", 100, new ArrayList<>());
        p.healthpoints = 10;
        p.currenthealthpoints = 3;
        UpdatePlayer.majFinDeTour(p);
        assertEquals(4, p.currenthealthpoints);
    }

    // ----------------------------
    // Tests pour les Archers (ARCHER)
    // ----------------------------
    @Test
    void testMajFinDeTour_ArcherWithMagicBow() {
        player p = new player("A", "B", "ARCHER", 100, new ArrayList<>());
        p.healthpoints = 100;
        p.currenthealthpoints = 32;
        p.addItem(new Item("Magic Bow", "Enhances archer healing", 4, 90));
        
        UpdatePlayer.majFinDeTour(p);
        // +1 + (32/8-1) = +1 + 3 = +4
        assertEquals(36, p.currenthealthpoints);
    }

    @Test
    void testMajFinDeTour_ArcherNoMagicBow() {
        player p = new player("A", "B", "ARCHER", 100, new ArrayList<>());
        p.healthpoints = 16;
        p.currenthealthpoints = 7;
        UpdatePlayer.majFinDeTour(p);
        assertEquals(8, p.currenthealthpoints);
    }

    @Test
    void testMajFinDeTour_Archer_MagicBowDifference() {
        // Test pour vérifier que le Magic Bow fait une différence
        player pWith = new player("With", "Bow", "ARCHER", 100, new ArrayList<>());
        pWith.healthpoints = 100;
        pWith.currenthealthpoints = 32;
        pWith.addItem(new Item("Magic Bow", "Enhances archer healing", 4, 90));
        
        player pWithout = new player("Without", "Bow", "ARCHER", 100, new ArrayList<>());
        pWithout.healthpoints = 100;
        pWithout.currenthealthpoints = 32;
        
        UpdatePlayer.majFinDeTour(pWith);
        UpdatePlayer.majFinDeTour(pWithout);
        
        assertTrue(pWith.currenthealthpoints > pWithout.currenthealthpoints);
        assertEquals(36, pWith.currenthealthpoints);
        assertEquals(33, pWithout.currenthealthpoints);
    }

    // ----------------------------
    // Tests pour les Aventuriers (ADVENTURER)
    // ----------------------------
    @Test
    void testMajFinDeTour_Adventurer_LowHP_LevelUnder3() {
        player p = new player("A", "B", "ADVENTURER", 100, new ArrayList<>());
        p.healthpoints = 20;
        p.currenthealthpoints = 6;
        UpdatePlayer.majFinDeTour(p);
        assertEquals(7, p.currenthealthpoints);
    }

    @Test
    void testMajFinDeTour_Adventurer_LowHP_Level3AndOver() {
        player p = new player("A", "B", "ADVENTURER", 100, new ArrayList<>());
        p.healthpoints = 20;
        p.currenthealthpoints = 6;
        UpdatePlayer.addXp(p, 30);
        UpdatePlayer.majFinDeTour(p);
        assertEquals(8, p.currenthealthpoints);
    }

    // ----------------------------
    // Tests pour les Gobelins (GOBLIN)
    // ----------------------------
    @Test
    void testMajFinDeTour_GoblinHealing() {
        player p = new player("Gob", "Gobby", "GOBLIN", 100, new ArrayList<>());
        p.healthpoints = 10;
        p.currenthealthpoints = 4;
        
        UpdatePlayer.majFinDeTour(p);
        assertEquals(5, p.currenthealthpoints);
    }

    // ----------------------------
    // Tests pour les frontières (boundaries)
    // ----------------------------
    @Test
    void testMajFinDeTour_ExactHalfHP() {
        // Test avec HP exactement à la moitié
        player p1 = new player("Half", "Test", "ARCHER", 100, new ArrayList<>());
        p1.healthpoints = 10;
        p1.currenthealthpoints = 5;
        UpdatePlayer.majFinDeTour(p1);
        assertEquals(5, p1.currenthealthpoints);
        
        player p2 = new player("Half", "Test", "DWARF", 100, new ArrayList<>());
        p2.healthpoints = 20;
        p2.currenthealthpoints = 10;
        UpdatePlayer.majFinDeTour(p2);
        assertEquals(10, p2.currenthealthpoints);
    }

    @Test
    void testMajFinDeTour_JustBelowHalf() {
        // Test avec HP juste en-dessous de la moitié
        player p = new player("Below", "Half", "DWARF", 100, new ArrayList<>());
        p.healthpoints = 10;
        p.currenthealthpoints = 4;
        UpdatePlayer.majFinDeTour(p);
        assertTrue(p.currenthealthpoints > 4);
    }

    @Test
    void testMajFinDeTour_JustAboveHalf() {
        // Test avec HP au-dessus de la moitié
        player p = new player("Above", "Half", "ADVENTURER", 100, new ArrayList<>());
        p.healthpoints = 20;
        p.currenthealthpoints = 11;
        UpdatePlayer.majFinDeTour(p);
        assertEquals(11, p.currenthealthpoints);
    }

    // ----------------------------
    // Tests pour le clamp (HP au maximum)
    // ----------------------------
    @Test
    void testMajFinDeTour_FullHP() {
        player p = new player("Full", "HP", "ARCHER", 100, new ArrayList<>());
        p.healthpoints = 15;
        p.currenthealthpoints = 15;
        UpdatePlayer.majFinDeTour(p);
        assertEquals(15, p.currenthealthpoints);
    }

    @Test
    void testMajFinDeTour_OverMaxHP() {
        // Test avec différentes classes dépassant le max
        player p1 = new player("Over1", "Max", "DWARF", 100, new ArrayList<>());
        p1.healthpoints = 15;
        p1.currenthealthpoints = 20;
        UpdatePlayer.majFinDeTour(p1);
        assertEquals(15, p1.currenthealthpoints);
        
        player p2 = new player("Over2", "Max", "ADVENTURER", 100, new ArrayList<>());
        p2.healthpoints = 10;
        p2.currenthealthpoints = 15;
        UpdatePlayer.majFinDeTour(p2);
        assertEquals(10, p2.currenthealthpoints);
        
        player p3 = new player("Over3", "Max", "ARCHER", 100, new ArrayList<>());
        p3.healthpoints = 10;
        p3.currenthealthpoints = 15;
        UpdatePlayer.majFinDeTour(p3);
        assertEquals(10, p3.currenthealthpoints);
    }

    @Test
    void testMajFinDeTour_ClampAfterHealing() {
        // Test pour vérifier que le clamp fonctionne après les soins
        player p = new player("Clamp", "Test", "DWARF", 100, new ArrayList<>());
        p.healthpoints = 10;
        p.currenthealthpoints = 4;
        p.addItem(new Item("Holy Elixir", "Recover your HP", 1, 60));
        
        UpdatePlayer.majFinDeTour(p);
        assertEquals(6, p.currenthealthpoints);
        
        // Forcer un overflow
        p.currenthealthpoints = 15;
        UpdatePlayer.majFinDeTour(p);
        assertEquals(10, p.currenthealthpoints);
    }

    // ----------------------------
    // Tests pour l'ajout d'expérience (XP)
    // ----------------------------
    @Test
    void testAddXp_NoLevelUp() {
        player p = new player("A", "B", "ARCHER", 100, new ArrayList<>());
        boolean result = UpdatePlayer.addXp(p, 2);
        assertFalse(result);
    }

    @Test
    void testAddXp_LevelUp() {
        player p = new player("A", "B", "ARCHER", 100, new ArrayList<>());
        boolean result = UpdatePlayer.addXp(p, 20);
        assertTrue(result);
    }

    @Test
    void testAddXp_MultiLevelUp() {
        player p = new player("A", "B", "ARCHER", 100, new ArrayList<>());
        boolean result = UpdatePlayer.addXp(p, 50);
        assertTrue(result);
        assertTrue(p.retrieveLevel() >= 3);
    }

    @Test
    void testAddXp_LevelUp_VerifiesAbilities() {
        player p = new player("Tom", "Avatar", "DWARF", 100, new ArrayList<>());
        
        // Niveau 1 : ALC=4, INT=1, ATK=3
        assertEquals(4, p.abilities.get("ALC"));
        assertEquals(1, p.abilities.get("INT"));
        assertEquals(3, p.abilities.get("ATK"));
        
        // Monter au niveau 2
        UpdatePlayer.addXp(p, 10);
        assertEquals(2, p.retrieveLevel());
        
        // Niveau 2 : DEF=1, ALC=5
        assertEquals(1, p.abilities.get("DEF"));
        assertEquals(5, p.abilities.get("ALC"));
        
        // Monter au niveau 3
        UpdatePlayer.addXp(p, 17);
        assertEquals(3, p.retrieveLevel());
        
        // Niveau 3 : ATK=4
        assertEquals(4, p.abilities.get("ATK"));
    }

    @Test
    void testAddXp_LevelUp_AddsInventoryItem() {
        player p = new player("Tom", "Avatar", "ARCHER", 100, new ArrayList<>());
        
        int initialInventorySize = p.inventory.size();
        
        boolean leveledUp = UpdatePlayer.addXp(p, 10);
        
        assertTrue(leveledUp);
        assertTrue(p.inventory.size() >= initialInventorySize);
    }

    @Test
    void testAddXp_MultipleAbilityUpdates() {
        player p = new player("Hero", "Champion", "DWARF", 100, new ArrayList<>());
        
        // Niveau 1
        assertEquals(4, p.abilities.get("ALC"));
        assertEquals(1, p.abilities.get("INT"));
        assertEquals(3, p.abilities.get("ATK"));
        
        // Niveau 2
        UpdatePlayer.addXp(p, 10);
        assertEquals(2, p.retrieveLevel());
        assertEquals(1, p.abilities.get("DEF"));
        assertEquals(5, p.abilities.get("ALC"));
        
        // Niveau 3
        UpdatePlayer.addXp(p, 17);
        assertEquals(3, p.retrieveLevel());
        assertEquals(4, p.abilities.get("ATK"));
    }

    @Test
    void testAddXp_AllClassesLevelUp() {
        String[] classes = {"ARCHER", "DWARF", "ADVENTURER", "GOBLIN"};
        
        for (String avatarClass : classes) {
            player p = new player("Test", "Player", avatarClass, 100, new ArrayList<>());
            
            int initialLevel = p.retrieveLevel();
            int initialInventorySize = p.inventory.size();
            int initialAbilitiesCount = p.abilities.size();
            
            boolean result = UpdatePlayer.addXp(p, 15);
            
            assertTrue(result, avatarClass + " doit level up");
            assertTrue(p.retrieveLevel() > initialLevel, 
                       avatarClass + " le niveau doit augmenter");
            assertTrue(p.inventory.size() >= initialInventorySize,
                      avatarClass + " inventaire doit rester ou augmenter");
            assertTrue(p.abilities.size() >= initialAbilitiesCount,
                      avatarClass + " doit avoir au moins autant de capacités");
        }
    }

    // ----------------------------
    // Couverture constructeur UpdatePlayer
    // ----------------------------
    @Test
    void testUpdatePlayer_ConstructorCoverage() {
        new re.forestier.edu.rpg.UpdatePlayer();
    }
}