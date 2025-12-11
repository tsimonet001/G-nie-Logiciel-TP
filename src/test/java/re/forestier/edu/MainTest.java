package re.forestier.edu;

import org.junit.jupiter.api.Test;
import re.forestier.edu.rpg.player;
import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.Affichage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testMainExecutionAndOutput() {
        // Capture la sortie console
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            // Exécute main
            Main.main(new String[0]);
            
            // Récupére la sortie
            String output = outContent.toString();
            
            // TUE LES MUTANTS DES println (lignes 14, 15, 17)
            assertTrue(output.contains("Florian"), "La sortie doit contenir le nom du joueur");
            assertTrue(output.contains("Ruzberg de Rivehaute"), "La sortie doit contenir le nom d'avatar");
            assertTrue(output.contains("Niveau :"), "La sortie doit contenir le niveau");
            assertTrue(output.contains("------------------"), "La sortie doit contenir le séparateur");
            
            // Vérifie qu'il y a bien 2 affichages du joueur
            String[] parts = output.split("------------------");
            assertEquals(2, parts.length, "Il doit y avoir exactement 2 parties séparées par '------------------'");
            assertTrue(parts[0].contains("Florian"), "La première partie doit contenir Florian");
            assertTrue(parts[1].contains("Florian"), "La deuxième partie doit contenir Florian");
            
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testMainClassInstantiation() {
        // Instanciation explicite de Main
        assertDoesNotThrow(() -> {
            Main instance = new Main();
            assertNotNull(instance);
        });
    }

    @Test
    public void testMainBehaviorWithAssertionsAndOutputCapture() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        player testPlayer = new player("Florian", "Ruzberg de Rivehaute", "DWARF", 200, new ArrayList<>());

        // Vérifie addMoney
        testPlayer.addMoney(400);
        assertEquals(600, testPlayer.money, "Le joueur doit avoir 600 après addMoney(400)");

        // Vérifie addXp
        UpdatePlayer.addXp(testPlayer, 15);
        assertTrue(testPlayer.retrieveLevel() >= 1);

        String output1 = Affichage.afficherJoueur(testPlayer);
        System.out.println(output1);

        // Vérifie que la sortie contient le nom et le niveau
        String capturedOutput1 = outContent.toString();
        assertTrue(capturedOutput1.contains("Florian"));
        assertTrue(capturedOutput1.contains("Niveau :"));

        UpdatePlayer.addXp(testPlayer, 20);
        String output2 = Affichage.afficherJoueur(testPlayer);
        System.out.println("------------------");
        System.out.println(output2);

        String capturedOutput2 = outContent.toString();
        assertTrue(capturedOutput2.contains("------------------"));
        assertTrue(capturedOutput2.contains("Florian"));

        System.setOut(originalOut);
    }
    
    @Test
    public void testAddMoneyEffectiveness() {
        // Test spécifique pour tuer le mutant addMoney
        player p1 = new player("Test", "Avatar", "DWARF", 100, new ArrayList<>());
        int initialMoney = p1.money;
        
        p1.addMoney(50);
        
        assertEquals(150, p1.money, "addMoney doit augmenter l'argent");
        assertTrue(p1.money > initialMoney, "L'argent doit augmenter après addMoney");
    }
    
    @Test
    public void testPlayerMoneyAfterAddMoney() {
        // Reproduire exactement le scénario de Main.main() pour vérifier addMoney
        player testPlayer = new player("Florian", "Ruzberg de Rivehaute", "DWARF", 200, new ArrayList<>());
        
        // Le joueur commence avec 200
        assertEquals(200, testPlayer.money, "Le joueur doit commencer avec 200");
        
        // Appele addMoney(400) comme dans Main
        testPlayer.addMoney(400);
        
        // Après addMoney, le joueur doit avoir 600
        assertEquals(600, testPlayer.money, "Après addMoney(400), le joueur doit avoir 600");
        
        // Vérifie que le montant a bien changé
        assertNotEquals(200, testPlayer.money, "L'argent ne doit plus être 200 après addMoney");
    }
    
    @Test
    public void testAddMoneyWithZeroAmount() {
        player p = new player("Test", "Avatar", "DWARF", 100, new ArrayList<>());
        p.addMoney(0);
        assertEquals(100, p.money, "addMoney(0) ne doit pas changer l'argent");
    }
    
    @Test
    public void testAddMoneyMultipleTimes() {
        player p = new player("Test", "Avatar", "DWARF", 100, new ArrayList<>());
        
        p.addMoney(50);
        assertEquals(150, p.money);
        
        p.addMoney(100);
        assertEquals(250, p.money);
        
        p.addMoney(150);
        assertEquals(400, p.money, "Plusieurs appels à addMoney doivent être cumulatifs");
    }

    @Test
public void testMainScenario_VerifyAddMoneyIsCalled() {
    // Reproduire EXACTEMENT le scénario de Main.main()
    player firstPlayer = new player("Florian", "Ruzberg de Rivehaute", "DWARF", 200, new ArrayList<>());
    
    // Vérifie l'argent AVANT addMoney
    assertEquals(200, firstPlayer.money, "Le joueur doit commencer avec 200");
    
    // Appele addMoney comme dans Main.main()
    firstPlayer.addMoney(400);
    
    // Vérifie l'argent APRÈS addMoney
    assertEquals(600, firstPlayer.money, "Après addMoney(400), le joueur doit avoir 600");
    
    // Continue avec le reste du scénario pour coller à Main.main()
    UpdatePlayer.addXp(firstPlayer, 15);
    String output1 = Affichage.afficherJoueur(firstPlayer);
    assertTrue(output1.contains("Florian"));
    
    UpdatePlayer.addXp(firstPlayer, 20);
    String output2 = Affichage.afficherJoueur(firstPlayer);
    assertTrue(output2.contains("Florian"));
    
    // Vérifie que l'argent n'a pas changé après les addXp
    assertEquals(600, firstPlayer.money, "L'argent doit rester à 600 après les opérations");
}
}
