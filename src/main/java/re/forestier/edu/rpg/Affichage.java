package re.forestier.edu.rpg;

public class Affichage {

    public static String afficherJoueur(player player) {
        final String[] finalString = {"Joueur " + player.Avatar_name + " joué par " + player.playerName};
        finalString[0] += "\nNiveau : " + player.retrieveLevel() + " (XP totale : " + player.xp + ")";
        finalString[0] += "\n\nCapacités :";
        player.abilities.forEach((name, level) -> {
            finalString[0] += "\n   " + name + " : " + level;
        });
        finalString[0] += "\n\nInventaire :";
        player.inventory.forEach(item -> {
            finalString[0] += "\n   " + item;
        });

        return finalString[0];
    }
    public static String afficherJoueurMarkdown(player player) {
    StringBuilder md = new StringBuilder();
    
    // Titre avec le nom du joueur
    md.append("# ").append(player.playerName).append("\n\n");
    
    // Nom d'avatar en gras et classe en italique
    md.append("**").append(player.Avatar_name).append("** - ");
    md.append("*").append(player.getAvatarClass()).append("*\n\n");
    
    // Niveau et XP
    md.append("## Statistiques\n\n");
    md.append("* Niveau : ").append(player.retrieveLevel()).append("\n");
    md.append("* XP totale : ").append(player.xp).append("\n");
    md.append("* Argent : ").append(player.money).append(" pièces d'or\n\n");
    
    // Capacités
    md.append("## Capacités\n\n");
    player.abilities.forEach((name, level) -> {
        md.append("* ").append(name).append(" : ").append(level).append("\n");
    });
    
    // Inventaire
    md.append("\n## Inventaire\n\n");
    if (player.inventory.isEmpty()) {
        md.append("*Vide*\n");
    } else {
        player.inventory.forEach(item -> {
            md.append("* ").append(item).append("\n");
        });
    }
    
    return md.toString();
}
}
