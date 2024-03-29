package net.lewmc.essence.tabcompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EssenceTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(
            @NotNull CommandSender sender,
            @NotNull Command cde,
            @NotNull String arg,
            String[] args
    ) {
        String[] keys;
        if (args.length == 1) {
            keys = new String[]{"help"};
        } else if (args.length == 2) {
            keys = new String[]{"inventory", "gamemode", "teleport", "stats", "economy", "team"};
        } else if (
                args.length == 3 && (
                args[1].equalsIgnoreCase("teleport") ||
                args[1].equalsIgnoreCase("inventory")
            )
        ) {
            keys = new String[]{"1", "2"};
        } else {
            keys = new String[]{};
        }

        return new ArrayList<>(Arrays.asList(keys));
    }
}