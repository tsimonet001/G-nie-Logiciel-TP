package re.forestier.edu.rpg;

public class Affichage {

    public static String afficherJoueur(player player) {
        final String[] finalString = {"Joueur " + player.Avatar_name + " joué par " + player.playerName};
        finalString[0] += "\nNiveau : " + player.retrieveLevel() + " (XP totale : " + player.xp + ")";
        finalString[0] += "\n\nCapacités :";
        player.abilities.forEach((name, level) -> {
            finalString[0] += "\n   " + name + " : " + level;
        });
        finalString[0] += "\n\nInventaire (Poids: " + player.getCurrentWeight() + "/" + player.getMaxWeight() + ") :";
        player.inventory.forEach(item -> {
            finalString[0] += "\n   " + item.toString() + " [" + item.getWeight() + "kg, " + item.getValue() + " po]";
        });

        return finalString[0];
    }
    
    public static String afficherJoueurMarkdown(player player) {
        StringBuilder md = new StringBuilder();
        
        md.append("# ").append(player.playerName).append("\n\n");
        md.append("**").append(player.Avatar_name).append("** - ");
        md.append("*").append(player.getAvatarClass()).append("*\n\n");
        
        md.append("## Statistiques\n\n");
        md.append("* Niveau : ").append(player.retrieveLevel()).append("\n");
        md.append("* XP totale : ").append(player.xp).append("\n");
        md.append("* Argent : ").append(player.money).append(" pièces d'or\n\n");
        
        md.append("## Capacités\n\n");
        player.abilities.forEach((name, level) -> {
            md.append("* ").append(name).append(" : ").append(level).append("\n");
        });
        
        md.append("\n## Inventaire\n\n");
        md.append("*Poids : ").append(player.getCurrentWeight()).append("/")
          .append(player.getMaxWeight()).append(" kg*\n\n");
        
        if (player.inventory.isEmpty()) {
            md.append("*Vide*\n");
        } else {
            player.inventory.forEach(item -> {
                md.append("* **").append(item.getName()).append("** - ")
                  .append(item.getDescription())
                  .append(" (").append(item.getWeight()).append("kg, ")
                  .append(item.getValue()).append(" po)\n");
            });
        }
        //
        return md.toString();
    }
}