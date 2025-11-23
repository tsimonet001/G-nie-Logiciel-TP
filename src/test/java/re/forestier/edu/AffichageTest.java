package re.forestier.edu;

import org.junit.jupiter.api.Test;
import re.forestier.edu.rpg.Affichage;
import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.approvaltests.Approvals.verify;

public class AffichageTest {

    // Couverture du constructeur
    @Test
    void testAffichageConstructorCoverage() {
        new Affichage();
    }

    // Test d'affichage d'un joueur simple (issu de MainTest)
    @Test
    void testAfficherJoueur_withPlayer() {
        ArrayList<String> inventory = new ArrayList<>();
        player p = new player("Florian", "Ruzberg de Rivehaute", "DWARF", 200, inventory);

        // Ajout XP pour tester la montée de niveau
        UpdatePlayer.addXp(p, 15);

        String affichage = Affichage.afficherJoueur(p);

        assertTrue(affichage.contains("Florian"));
        assertTrue(affichage.contains("Ruzberg de Rivehaute"));
        assertTrue(affichage.contains("Niveau :"));
        assertTrue(affichage.contains("Inventaire :"));
    }

    @Test
void testAfficherJoueur_withInventoryItem() {
    ArrayList<String> inventory = new ArrayList<>();
    inventory.add("Épée magique");
    inventory.add("Potion de vie");
    
    player p = new player("Tom", "Le Guerrier", "ADVENTURER", 100, inventory);
    
    String affichage = Affichage.afficherJoueur(p);
    
    // Vérifier que l'inventaire est affiché
    assertTrue(affichage.contains("Inventaire :"), "Doit contenir le titre Inventaire");
    assertTrue(affichage.contains("Épée magique"), "Doit afficher l'objet 1");
    assertTrue(affichage.contains("Potion de vie"), "Doit afficher l'objet 2");
}

@Test
void testAfficherJoueur_withEmptyInventory() {
    ArrayList<String> inventory = new ArrayList<>();
    player p = new player("Tom", "Le Guerrier", "ADVENTURER", 100, inventory);
    
    String affichage = Affichage.afficherJoueur(p);
    
    // Même avec inventaire vide, la section doit être présente
    assertTrue(affichage.contains("Inventaire :"));
    
    // Compter les lignes pour vérifier que l'inventaire n'ajoute rien
    int inventoryIndex = affichage.indexOf("Inventaire :");
    String afterInventory = affichage.substring(inventoryIndex + "Inventaire :".length()).trim();
    
    // Si l'inventaire est vide, il ne devrait rien y avoir après (ou juste des espaces)
    assertTrue(afterInventory.isEmpty() || !afterInventory.contains("\n   "), 
               "L'inventaire vide ne doit pas afficher d'objets");
}
@Test
void testAfficherJoueurMarkdown() {
    ArrayList<String> inventory = new ArrayList<>();
    inventory.add("Épée magique");
    inventory.add("Potion de vie");
    
    player p = new player("Tom", "Le Guerrier", "ADVENTURER", 100, inventory);
    UpdatePlayer.addXp(p, 15);
    
    String markdown = Affichage.afficherJoueurMarkdown(p);
    
    // Vérifier le format Markdown
    assertTrue(markdown.contains("# Tom"), "Doit contenir le titre avec #");
    assertTrue(markdown.contains("**Le Guerrier**"), "Le nom d'avatar doit être en gras");
    assertTrue(markdown.contains("*ADVENTURER*"), "La classe doit être en italique");
    assertTrue(markdown.contains("* Épée magique"), "Les objets doivent être en liste");
    assertTrue(markdown.contains("* Potion de vie"));
}

@Test
void testAfficherJoueurMarkdown_WithAbilities() {
    ArrayList<String> inventory = new ArrayList<>();
    player p = new player("Test", "Hero", "DWARF", 100, inventory);
    
    String markdown = Affichage.afficherJoueurMarkdown(p);
    
    // Vérifier que les capacités sont listées
    assertTrue(markdown.contains("## Capacités"));
    assertTrue(markdown.contains("* ALC : 4"));
    assertTrue(markdown.contains("* INT : 1"));
    assertTrue(markdown.contains("* ATK : 3"));
}
}
