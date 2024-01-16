package net.lewmc.essence.commands.teleportation;

import net.lewmc.essence.MessageHandler;
import net.lewmc.essence.Essence;
import net.lewmc.essence.events.PermissionHandler;
import net.lewmc.essence.utils.ConfigUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class DelwarpCommand implements CommandExecutor {
    private Essence plugin;

    /**
     * Constructor for the GamemodeCommands class.
     *
     * @param plugin References to the main plugin class.
     */
    public DelwarpCommand(Essence plugin) {
        this.plugin = plugin;
    }

    /**
     * @param commandSender Information about who sent the command - player or console.
     * @param command       Information about what command was sent.
     * @param s             Command label - not used here.
     * @param args          The command's arguments.
     * @return boolean true/false - was the command accepted and processed or not?
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            plugin.getLogger().warning("[Essence] Sorry, you need to be an in-game player to use this command.");
            return true;
        }
        MessageHandler message = new MessageHandler(commandSender, plugin);
        Player player = (Player) commandSender;
        PermissionHandler permission = new PermissionHandler(player, message);

        if (command.getName().equalsIgnoreCase("delwarp")) {
            if (permission.has("essence.warp.delete")) {
                if (args.length == 0) {
                    message.PrivateMessage("Usage: /delwarp <name>", true);
                    return true;
                }
                ConfigUtil config = new ConfigUtil(this.plugin, message);
                config.load("warps.yml");

                String warpName = args[0].toLowerCase();

                ConfigurationSection cs = config.getSection("warps");

                cs.set(warpName, null);

                // Save the configuration to the file
                config.save();

                message.PrivateMessage("Deleted warp '" + args[0] + "'.", false);
            } else {
                permission.not();
            }
            return true;
        }

        return false;
    }
}
