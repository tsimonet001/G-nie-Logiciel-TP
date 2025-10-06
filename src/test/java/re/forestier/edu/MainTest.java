package re.forestier.edu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import re.forestier.edu.rpg.Affichage;
import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;

import java.util.ArrayList;

public class MainTest {

    @Test
    public void testAfficherJoueur_withPlayer() {
        ArrayList<String> inventory = new ArrayList<>();
        player p = new player("Florian", "Ruzberg de Rivehaute", "DWARF", 200, inventory);

        // Ajout XP pour tester la montée de niveau (comme dans main)
        UpdatePlayer.addXp(p, 15);

        String affichage = Affichage.afficherJoueur(p);

        // Vérifier que la chaîne contient des informations importantes
        assertTrue(affichage.contains("Florian"));
        assertTrue(affichage.contains("Ruzberg de Rivehaute"));
        assertTrue(affichage.contains("Niveau :"));
        assertTrue(affichage.contains("Inventaire :"));
    }

    @Test
    public void testMainExecutionAndOutput() {
        // Exécuter main sans exception
        assertDoesNotThrow(() -> {
            Main.main(new String[0]);
        });
    }

    @Test
    public void testMainClassInstantiation() {
        // Instanciation explicite de Main pour couvrir la déclaration de classe
        assertDoesNotThrow(() -> {
            Main instance = new Main();
            assertNotNull(instance);
        });
    }
}
