package re.forestier.edu;

import org.junit.jupiter.api.Test;
import re.forestier.edu.rpg.Affichage;
import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;
import re.forestier.edu.rpg.Item;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AffichageTest {

    @Test
    void testAffichageConstructorCoverage() {
        new Affichage();
    }

    @Test
    void testAfficherJoueur_withPlayer() {
        ArrayList<Item> inventory = new ArrayList<>();
        player p = new player("Florian", "Ruzberg de Rivehaute", "DWARF", 200, inventory);

        UpdatePlayer.addXp(p, 15);

        String affichage = Affichage.afficherJoueur(p);

        assertTrue(affichage.contains("Florian"));
        assertTrue(affichage.contains("Ruzberg de Rivehaute"));
        assertTrue(affichage.contains("Niveau :"));
        assertTrue(affichage.contains("Inventaire"));
    }

    @Test
    void testAfficherJoueur_withInventoryItem() {
        ArrayList<Item> inventory = new ArrayList<>();
        inventory.add(new Item("Épée magique", "Une épée enchantée", 3, 100));
        inventory.add(new Item("Potion de vie", "Restaure 50 HP", 1, 50));
        
        player p = new player("Tom", "Le Guerrier", "ADVENTURER", 100, inventory);
        
        String affichage = Affichage.afficherJoueur(p);
        
        assertTrue(affichage.contains("Inventaire"), "Doit contenir le titre Inventaire");
        assertTrue(affichage.contains("Épée magique"), "Doit afficher l'objet 1");
        assertTrue(affichage.contains("Potion de vie"), "Doit afficher l'objet 2");
    }

    @Test
    void testAfficherJoueur_withEmptyInventory() {
        ArrayList<Item> inventory = new ArrayList<>();
        player p = new player("Tom", "Le Guerrier", "ADVENTURER", 100, inventory);
        
        String affichage = Affichage.afficherJoueur(p);
        
        assertTrue(affichage.contains("Inventaire"));
    }

    @Test
    void testAfficherJoueurMarkdown() {
        ArrayList<Item> inventory = new ArrayList<>();
        inventory.add(new Item("Épée magique", "Une épée enchantée", 3, 100));
        inventory.add(new Item("Potion de vie", "Restaure 50 HP", 1, 50));
        
        player p = new player("Tom", "Le Guerrier", "ADVENTURER", 100, inventory);
        UpdatePlayer.addXp(p, 15);
        
        String markdown = Affichage.afficherJoueurMarkdown(p);
        
        assertTrue(markdown.contains("# Tom"), "Doit contenir le titre avec #");
        assertTrue(markdown.contains("**Le Guerrier**"), "Le nom d'avatar doit être en gras");
        assertTrue(markdown.contains("*ADVENTURER*"), "La classe doit être en italique");
        assertTrue(markdown.contains("Épée magique"), "Les objets doivent être en liste");
        assertTrue(markdown.contains("Potion de vie"));
    }

    @Test
    void testAfficherJoueurMarkdown_WithAbilities() {
        ArrayList<Item> inventory = new ArrayList<>();
        player p = new player("Test", "Hero", "DWARF", 100, inventory);
        
        String markdown = Affichage.afficherJoueurMarkdown(p);
        
        assertTrue(markdown.contains("## Capacités"));
        assertTrue(markdown.contains("* ALC : 4"));
        assertTrue(markdown.contains("* INT : 1"));
        assertTrue(markdown.contains("* ATK : 3"));
    }
    
    @Test
    void testAfficherJoueurMarkdown_WithWeight() {
        ArrayList<Item> inventory = new ArrayList<>();
        inventory.add(new Item("Épée lourde", "Très lourde", 10, 200));
        
        player p = new player("Test", "Hero", "DWARF", 100, inventory);
        
        String markdown = Affichage.afficherJoueurMarkdown(p);
        
        assertTrue(markdown.contains("Poids :"), "Doit afficher le poids");
        assertTrue(markdown.contains("10"), "Doit afficher le poids actuel");
    }
}