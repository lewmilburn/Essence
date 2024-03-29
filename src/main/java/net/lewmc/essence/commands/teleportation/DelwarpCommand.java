package net.lewmc.essence.commands.teleportation;

import net.lewmc.essence.utils.LogUtil;
import net.lewmc.essence.utils.MessageUtil;
import net.lewmc.essence.Essence;
import net.lewmc.essence.utils.PermissionHandler;
import net.lewmc.essence.utils.DataUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DelwarpCommand implements CommandExecutor {
    private final Essence plugin;
    private final LogUtil log;

    /**
     * Constructor for the DelwarpCommand class.
     *
     * @param plugin References to the main plugin class.
     */
    public DelwarpCommand(Essence plugin) {
        this.plugin = plugin;
        this.log = new LogUtil(plugin);
    }

    /**
     * @param commandSender Information about who sent the command - player or console.
     * @param command       Information about what command was sent.
     * @param s             Command label - not used here.
     * @param args          The command's arguments.
     * @return boolean true/false - was the command accepted and processed or not?
     */
    @Override
    public boolean onCommand(
        @NotNull CommandSender commandSender,
        @NotNull Command command,
        @NotNull String s,
        String[] args
    ) {
        if (!(commandSender instanceof Player)) {
            this.log.noConsole();
            return true;
        }
        MessageUtil message = new MessageUtil(commandSender, plugin);
        PermissionHandler permission = new PermissionHandler(commandSender, message);

        if (command.getName().equalsIgnoreCase("delwarp")) {
            if (permission.has("essence.warp.delete")) {
                if (args.length == 0) {
                    message.PrivateMessage("warp", "delusage");
                    return true;
                }
                DataUtil config = new DataUtil(this.plugin, message);
                config.load("data/warps.yml");

                String warpName = args[0].toLowerCase();

                if (!config.sectionExists("warps."+warpName)) {
                    config.close();
                    message.PrivateMessage("warp", "notfound", warpName);
                    return true;
                }

                ConfigurationSection cs = config.getSection("warps");

                cs.set(warpName, null);

                // Save the configuration to the file
                config.save();

                message.PrivateMessage("warp", "deleted", warpName);
            } else {
                permission.not();
            }
            return true;
        }

        return false;
    }
}
