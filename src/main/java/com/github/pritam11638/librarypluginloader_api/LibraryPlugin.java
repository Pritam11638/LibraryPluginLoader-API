package com.github.pritam11638.librarypluginloader_api;

import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public abstract class LibraryPlugin {
    private final JavaPlugin libraryPluginLoaderInstance;
    private final Logger libraryPluginLogger;

    public LibraryPlugin(JavaPlugin libraryPluginLoaderInstance) {
        this.libraryPluginLoaderInstance = libraryPluginLoaderInstance;
        this.libraryPluginLogger = (Logger) LoggerFactory.getLogger(getPluginMeta().name());
    }

    public abstract LibraryPluginMeta getPluginMeta();

    public abstract void onLoad();
    public abstract void onEnable();
    public abstract void onDisable();

    public List<RemoteRepository> getRepositories() {return List.of();}
    public List<Dependency> getDependencies() {return List.of();};

    public final Server getServer() {return libraryPluginLoaderInstance.getServer();}
    public final Logger getLogger() {return libraryPluginLogger;}
    public final YamlConfiguration getConfig() {
        File configFile = new File(getDataFolder(), "config.yml");

        try {
            configFile.createNewFile();

            return YamlConfiguration.loadConfiguration(configFile);
        } catch (IOException e) {
            getLogger().severe(e.getMessage());
            return null;
        }
    }

    public final void saveConfig() {
        File configFile = new File(getDataFolder(), "config.yml");

        try {
            Objects.requireNonNull(getConfig()).save(configFile);
        } catch (IOException e) {
            getLogger().severe(e.getMessage());
        }
    }
    public final File getDataFolder() {return new File(libraryPluginLoaderInstance.getDataFolder(), "/" + getPluginMeta().name());}
}
