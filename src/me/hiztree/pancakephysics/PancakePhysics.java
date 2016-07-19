package me.hiztree.pancakephysics;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PancakePhysics extends JavaPlugin implements Listener {

    private String prefix = "&8[&bPancakePhysics&8]&6 ";
    private boolean physicsAllowed = true;

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getCommand("pancakephysics").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("pancakephysics")) {
            if(sender.hasPermission("pancakephysics.change")) {

                boolean change = false;

                if(args.length < 1) {
                    change = !physicsAllowed;
                }else if(args.length == 1) {
                    try {
                        change = Boolean.parseBoolean(args[0]);
                    } catch (Exception e) {
                        sendMessage(sender, "&cPlease specify a valid value!");
                        return true;
                    }
                }else {
                    sendMessage(sender, "&cUsage: &7" + command.getUsage() + "&c.");
                    return true;
                }

                if (change) {
                    physicsAllowed = true;
                    sendMessage(sender, "You have &7enabled&6 block physics.");
                } else {
                    physicsAllowed = false;
                    sendMessage(sender, "You have &7disabled&6 block physics.");
                }
            }else {
                sendMessage(sender, "&cYou do not have enough permission to perform this command!");
            }
        }

        return true;
    }

    public void sendMessage(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + msg));
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent event) {
        if(!physicsAllowed) {
            event.setCancelled(true);
        }
    }
}
