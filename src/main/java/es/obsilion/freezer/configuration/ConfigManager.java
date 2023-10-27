package es.obsilion.freezer.configuration;

import es.obsilion.freezer.ObsilionFreezer;
import org.bukkit.Bukkit;
import org.yaml.snakeyaml.Yaml;
import space.arim.dazzleconf.ConfigurationFactory;
import space.arim.dazzleconf.ConfigurationOptions;
import space.arim.dazzleconf.error.ConfigFormatSyntaxException;
import space.arim.dazzleconf.error.InvalidConfigException;
import space.arim.dazzleconf.ext.snakeyaml.CommentMode;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlConfigurationFactory;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlOptions;
import space.arim.dazzleconf.helper.ConfigurationHelper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.logging.Level;

public final class ConfigManager<C> {

    private final ConfigurationHelper<C> configHelper;
    private volatile C configData;

    private final ObsilionFreezer obsilionFreezer;

    private ConfigManager(ConfigurationHelper<C> configHelper, ObsilionFreezer obsilionFreezer) {
        this.configHelper = configHelper;
        this.obsilionFreezer = obsilionFreezer;
        reloadConfig();
    }

    public static <C> ConfigManager<C> create(ObsilionFreezer obsilionFreezer, Path configFolder, String fileName, Class<C> configClass) {
        SnakeYamlOptions yamlOptions = new SnakeYamlOptions.Builder()
                .yamlSupplier(Yaml::new)
                .commentMode(CommentMode.alternativeWriter())
                .build();
        ConfigurationFactory<C> configFactory = SnakeYamlConfigurationFactory.create(
                configClass,
                ConfigurationOptions.defaults(),
                yamlOptions);
        return new ConfigManager<>(new ConfigurationHelper<>(configFolder, fileName, configFactory), obsilionFreezer);
    }

    public void reloadConfig() {
        try {
            configData = configHelper.reloadConfigData();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);

        } catch (ConfigFormatSyntaxException ex) {
            configData = configHelper.getFactory().loadDefaults();
            obsilionFreezer.getLogger().log(Level.WARNING, "The yaml syntax in your configuration is invalid. "
                    + "Check your YAML syntax with a tool such as https://yaml-online-parser.appspot.com/");
            ex.printStackTrace();

        } catch (InvalidConfigException ex) {
            configData = configHelper.getFactory().loadDefaults();
            obsilionFreezer.getLogger().log(Level.WARNING,"One of the values in your configuration is not valid. "
                    + "Check to make sure you have specified the right data types.");
            ex.printStackTrace();
        }
    }

    public C getConfigData() {
        C configData = this.configData;
        if (configData == null) {
            throw new IllegalStateException("Configuration has not been loaded yet");
        }
        return configData;
    }

}
