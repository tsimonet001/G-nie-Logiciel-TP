package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;

public class player {
    public String playerName;
    public String Avatar_name;
    private String AvatarClass;

    public Integer money;

    public int healthpoints;
    public int currenthealthpoints;
    protected int xp;

    public HashMap<String, Integer> abilities;
    public ArrayList<String> inventory;
    
    public player(String playerName, String avatar_name, String avatarClass, int money, ArrayList<String> inventory) {
        if (!avatarClass.equals("ARCHER") && !avatarClass.equals("ADVENTURER") && !avatarClass.equals("DWARF")) {
            return;
        }

        this.playerName = playerName;
        Avatar_name = avatar_name;
        AvatarClass = avatarClass;
        this.money = money;
        this.inventory = inventory;
        this.abilities = UpdatePlayer.abilitiesPerTypeAndLevel().get(AvatarClass).get(1);
    }

    public String getAvatarClass() {
        return AvatarClass;
    }

    public void removeMoney(int amount) throws IllegalArgumentException {
        if (money - amount < 0) {
            throw new IllegalArgumentException("Player can't have a negative money!");
        }
        money -= amount;
    }
    
    public void addMoney(int amount) {
        money += amount;
    }
    
    public int retrieveLevel() {
        HashMap<Integer, Integer> levels = new HashMap<>();
        levels.put(2, 10);
        levels.put(3, 27);
        levels.put(4, 57);
        levels.put(5, 111);

        if (xp < levels.get(2)) {
            return 1;
        } else if (xp < levels.get(3)) {
            return 2;
        } else if (xp < levels.get(4)) {
            return 3;
        } else if (xp < levels.get(5)) {
            return 4;
        }
        return 5;
    }

    public int getXp() {
        return this.xp;
    }
}