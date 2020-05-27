package fr.kinj14.chatprotect.Functions;

import fr.kinj14.chatprotect.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class F_Config {
    protected static FileConfiguration cfg;

    public F_Config(){
        cfg = Main.getInstance().getConfig();
    }

    public static String getStringConfig(String StringName){
        return cfg.getString(StringName);
    }

    public static int getIntConfig(String IntName){
        return cfg.getInt(IntName);
    }

    public static Boolean getBooleanConfig(String BooleanName){
        return cfg.getBoolean(BooleanName);
    }

    public static Location getLocationConfig(String WorldName, String LocationName){
        return deserializeLocation(WorldName, cfg.getString(LocationName));
    }

    public static ConfigurationSection getConfigurationSection(String ConfigurationSectionName){
        return cfg.getConfigurationSection(ConfigurationSectionName);
    }

    //(de)serialize

    public static Location deserializeConfigLocation(String World_Name, String configname){
        String[] split = getStringConfig(configname).split(", ");
        return new Location(
                Bukkit.getWorld(World_Name),
                Double.parseDouble(split[0]),
                Double.parseDouble(split[1]),
                Double.parseDouble(split[2]),
                Float.parseFloat(split[3]),
                Float.parseFloat(split[4])
        );
    }

    public static Location deserializeLocation(String World_Name, String name){
        String[] split = name.split(", ");
        return new Location(
                Bukkit.getWorld(World_Name),
                Double.parseDouble(split[0]),
                Double.parseDouble(split[1]),
                Double.parseDouble(split[2]),
                Float.parseFloat(split[3]),
                Float.parseFloat(split[4])
        );
    }

    public static String serializeLocation(Location loc) {
        return loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", " + loc.getYaw() + ", " + loc.getPitch();
    }
}