package fr.kinj14.chatprotect.Functions;

import fr.kinj14.chatprotect.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class F_Commands implements CommandExecutor {
    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        //chatprotect
        if(cmd.getName().equalsIgnoreCase("chatprotect")  && sender.hasPermission("chatprotect")) {
            if(args.length >= 1){
                String subcommand = args[0];

                if(subcommand.equalsIgnoreCase("help")){
                    sender.sendMessage(main.getPrefix()+"Command Lists:");
                    sender.sendMessage("/chatprotect list");
                    sender.sendMessage("/chatprotect add <word>");
                    sender.sendMessage("/chatprotect remove <word>");
                    sender.sendMessage("/chatprotect clear");
                    sender.sendMessage("/chatprotect reload");
                    return true;
                }
                if(args.length >= 2) {
                    String arg = argsToText(args);
                    if (subcommand.equalsIgnoreCase("add")) {
                        if (!main.BlacklistedString.contains(arg)) {
                            main.BlacklistedString.add(arg);
                            main.saveForbiddenWord_File();
                            sender.sendMessage(main.getPrefix() + arg + " has just been added to the forbidden word!");
                            return true;
                        }
                    }
                    if (subcommand.equalsIgnoreCase("remove")) {
                        if (main.BlacklistedString.contains(arg)) {
                            main.BlacklistedString.remove(arg);
                            main.saveForbiddenWord_File();
                            sender.sendMessage(main.getPrefix() + arg + " has just been deleted at the forbidden word!!");
                            return true;
                        }
                    }
                }
                if(subcommand.equalsIgnoreCase("reload")){
                    main.saveForbiddenWord_File();
                    sender.sendMessage(main.getPrefix()+"The plugin has just been recharged!");
                    return true;
                }
                if(subcommand.equalsIgnoreCase("clear")){
                    main.BlacklistedString.clear();
                    main.saveForbiddenWord_File();
                    sender.sendMessage(main.getPrefix()+"The plugin has just been purged!");
                    return true;
                }
                if(subcommand.equalsIgnoreCase("list")){
                    sender.sendMessage(main.getPrefix()+"Forbidden words:");
                    for(String bannedword : main.BlacklistedString){
                        sender.sendMessage(bannedword);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public String argsToText(String[] args) {
        String value = args[1];
        for(String arg : args) {
            if(args[0] != arg && args[1] != arg) {
                value = value + " " + arg;
            }
        }
        return value;
    }
}
