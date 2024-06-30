package net.lewmc.essence.commands.playerinfo;

import net.lewmc.essence.Essence;
import net.lewmc.essence.utils.FileUtil;
import net.lewmc.essence.utils.LogUtil;
import net.lewmc.essence.utils.MessageUtil;
import net.lewmc.essence.utils.PermissionHandler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * /info command.
 */
public class InfoCommand implements CommandExecutor {
    private final Essence plugin;
    private final LogUtil log;

    /**
     * Constructor for the InfoCommand class.
     * @param plugin References to the main plugin class.
     */
    public InfoCommand(Essence plugin) {
        this.plugin = plugin;
        this.log = new LogUtil(plugin);
    }

    /**
     * @param commandSender Information about who sent the command - player or console.
     * @param command Information about what command was sent.
     * @param s Command label - not used here.
     * @param args The command's arguments.
     * @return boolean true/false - was the command accepted and processed or not?
     */
    @Override
    public boolean onCommand(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            String[] args
    ) {
        MessageUtil message = new MessageUtil(commandSender, plugin);
        Player player = (Player) commandSender;
        PermissionHandler permission = new PermissionHandler(commandSender, message);

        if (command.getName().equalsIgnoreCase("info")) {
            if (permission.has("essence.playerinfo.info")) {
                if (args.length == 1) {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
                    if (p.hasPlayedBefore()) {
                        FileUtil fu = new FileUtil(this.plugin);
                        if (fu.exists(fu.playerDataFile(p.getUniqueId()))) {
                            fu.load(fu.playerDataFile(p.getUniqueId()));
                            message.send("info", "lastname", new String[] { fu.getString("user.last-known-name") });
                            message.send("info", "lastseen", new String[] { fu.getString("user.last-seen") });
                            fu.close();
                        } else {
                            message.send("generic","playernotfound");
                        }
                    } else {
                        message.send("generic","playernotfound");
                    }
                } else {
                    message.send("generic","playernotfound");
                }
                return true;
            } else {
                return permission.not();
            }
        }

        return false;
    }
}