package fr.kinj14.chatprotect;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import fr.kinj14.chatprotect.Enums.E_Files;
import fr.kinj14.chatprotect.Functions.F_Commands;
import fr.kinj14.chatprotect.Functions.F_Config;
import fr.kinj14.chatprotect.Listeners.ChatListeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    //Instance
    private static Main instance = null;

    public static Main getInstance() {
        return instance;
    }

    public Logger logger;

    public List<String> BlacklistedString = new ArrayList<>();

    @Override
    public void onEnable(){
        logger = Bukkit.getLogger();
        instance = this;

        // Load configuration.
        E_Files.CONFIG.createYML();

        //load Language
        E_Files.LANG.createYML();

        //load DATA
        E_Files.DATA.createJSON();
        loadForbiddenWord_File();


        //RegisterListeners
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new ChatListeners(), this);

        //Commands
        getCommand("chatprotect").setExecutor(new F_Commands());
    }

    @Override
    public void onDisable() {
        saveForbiddenWord_File();
    }

    public String getPrefix(){return "§7[§eChatProtect§7] §r";}

    private void CreateForbiddenWord_File(){
        E_Files.DATA.createJSON();
    }

    public void loadForbiddenWord_File(){
        CreateForbiddenWord_File();
        List<String> data = E_Files.DATA.getJSON();
        BlacklistedString.clear();
        if(data.size() > 0){
            BlacklistedString.addAll(data);
        }
    }

    public void saveForbiddenWord_File() {
        CreateForbiddenWord_File();
        E_Files.DATA.saveJSON(BlacklistedString);
    }
}
