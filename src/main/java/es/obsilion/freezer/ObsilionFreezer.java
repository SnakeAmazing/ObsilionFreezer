package es.obsilion.freezer;

import es.obsilion.freezer.command.FreezeCommand;
import es.obsilion.freezer.command.FreezeCommandTabCompleter;
import es.obsilion.freezer.command.UnFreezeCommand;
import es.obsilion.freezer.configuration.ConfigManager;
import es.obsilion.freezer.configuration.MainConfig;
import es.obsilion.freezer.listeners.FrozenListeners;
import es.obsilion.freezer.listeners.PlayerChatListener;
import es.obsilion.freezer.player.FrozenPlayerHandler;
import es.obsilion.freezer.player.SimpleFrozenPlayerHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ObsilionFreezer extends JavaPlugin {

    private MainConfig config;

    private FrozenPlayerHandler frozenHandler;

    @Override
    public void onEnable() {
        ConfigManager<MainConfig> configManager = ConfigManager.create(this, this.getDataFolder().toPath(), "config.yml", MainConfig.class);
        config = configManager.getConfigData();

        this.frozenHandler = new SimpleFrozenPlayerHandler(this);

        registerListeners(
                new PlayerChatListener(this),
                new FrozenListeners(this)
        );

        registerCommands();
    }

    @Override
    public void onDisable() {

    }

    @NotNull
    public MainConfig getConfiguration() {
        return config;
    }

    public FrozenPlayerHandler getFrozenHandler() {
        return frozenHandler;
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            this.getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    private void registerCommands() {
        this.getCommand("freeze").setExecutor(new FreezeCommand(this));
        this.getCommand("freeze").setTabCompleter(new FreezeCommandTabCompleter());

        this.getCommand("unfreeze").setExecutor(new UnFreezeCommand(this));
        //this.getCommand("unfreeze").setTabCompleter(new FreezeCommandTabCompleter());
    }
}
