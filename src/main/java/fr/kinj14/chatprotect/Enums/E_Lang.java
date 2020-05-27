package fr.kinj14.chatprotect.Enums;

import fr.kinj14.chatprotect.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public enum E_Lang {
    PLUGIN_INITIALIZATION;

    private static final Map<E_Lang, String> VALUES = new HashMap<>();

    static{
        for (E_Lang lang : values()){
            VALUES.put(lang, lang.getFromFile());
        }

        Main.getInstance().logger.info("Lang file read successfully!");
    }

    public String get(){
        return VALUES.get(this);
    }

    private String getFromFile(){
        FileConfiguration config = E_Files.LANG.getConfig();
        String key = name().toLowerCase().replace('_','-');
        String value = config.getString(key);

        if(value == null){
            value = "";
        }

        return ChatColor.translateAlternateColorCodes('&', value);
    }
}
