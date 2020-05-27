package fr.kinj14.chatprotect.Listeners;

import fr.kinj14.chatprotect.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListeners implements Listener {
    protected final Main main = Main.getInstance();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        String msg = event.getMessage();
        if(CheckMessage(msg)){
            Player player = event.getPlayer();
            String bannedWord = getBannedWord(msg);
            main.logger.info(main.getPrefix()+player.getName()+"("+player.getUniqueId()+") typing a forbidden word ("+bannedWord+")");
            player.sendMessage(main.getPrefix()+"'"+bannedWord+"' is a forbidden word!");
            event.setCancelled(true);
            return;
        }
    }

    public Boolean CheckMessage(String msg) {
        for(String s : main.BlacklistedString){
            if(msg.toLowerCase().contains(s.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public String getBannedWord(String msg){
        for(String s : main.BlacklistedString){
            if(msg.toLowerCase().contains(s.toLowerCase())){
                return s.toLowerCase();
            }
        }
        return "";
    }
}
