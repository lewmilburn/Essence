package net.lewmc.essence.commands.teleportation.tp;

import net.lewmc.essence.Essence;
import net.lewmc.essence.utils.MessageUtil;
import net.lewmc.essence.utils.PermissionHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * /tpaccept command.
 */
public class TpacceptCommand implements CommandExecutor {
    private final Essence plugin;

    /**
     * Constructor for the TpaCommand class.
     *
     * @param plugin References to the main plugin class.
     */
    public TpacceptCommand(Essence plugin) {
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
    public boolean onCommand(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            String[] args
    ) {
        MessageUtil message = new MessageUtil(commandSender, this.plugin);
        PermissionHandler permission = new PermissionHandler(commandSender, message);

        if (permission.has("essence.teleport.request.accept")) {
            return true;
        } else {
            return permission.not();
        }
    }
}