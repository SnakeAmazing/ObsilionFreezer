package es.obsilion.freezer.command;

import es.obsilion.freezer.ObsilionFreezer;
import es.obsilion.freezer.configuration.MainConfig;
import es.obsilion.freezer.message.MessageDecorator;
import es.obsilion.freezer.player.FrozenPlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class UnFreezeCommand implements CommandExecutor {

    private final ObsilionFreezer plugin;
    private final MainConfig config;
    private final FrozenPlayerHandler frozenHandler;

    public UnFreezeCommand(ObsilionFreezer plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfiguration();
        this.frozenHandler = plugin.getFrozenHandler();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) {
            plugin.getLogger().log(Level.SEVERE, MessageDecorator.decorate(config.onlyPlayer()));
            return true;
        }

        if (args.length < 1) {
            player.sendMessage(MessageDecorator.decorate(config.commandUsage()));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (!target.isOnline()) {
            player.sendMessage(MessageDecorator.decorate(config.offlinePlayer()));
            return true;
        }

        frozenHandler.remove(target);
        player.sendMessage(MessageDecorator.decorate(config.youHaveUnFrozenPlayer()));

        return true;
    }
}
