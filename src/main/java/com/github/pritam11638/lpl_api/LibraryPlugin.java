package com.github.pritam11638.lpl_api;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;

public abstract class LibraryPlugin {
    private final JavaPlugin libraryPluginLoaderInstance;
    private final Logger libraryPluginLogger;

    public LibraryPlugin(JavaPlugin libraryPluginLoaderInstance) {
        this.libraryPluginLoaderInstance = libraryPluginLoaderInstance;
        this.libraryPluginLogger = Logger.getLogger(this.getPluginMeta().name());
    }

    public abstract LibraryPluginMeta getPluginMeta();

    public abstract void onLoad();

    public abstract void onEnable();

    public abstract void onDisable();

    public List<RemoteRepository> getRepositories() {
        return List.of();
    }

    public List<Dependency> getDependencies() {
        return List.of();
    }

    public final Server getServer() {
        return this.libraryPluginLoaderInstance.getServer();
    }

    public final Logger getLogger() {
        return this.libraryPluginLogger;
    }

    public final YamlConfiguration getConfig() {
        File configFile = new File(this.getDataFolder(), "config.yml");

        try {
            if (!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
            }
            return YamlConfiguration.loadConfiguration(configFile);
        } catch (IOException e) {
            this.getLogger().severe("Failed to load config: " + e.getMessage());
            return new YamlConfiguration();
        }
    }

    public final void saveConfig() {
        File configFile = new File(this.getDataFolder(), "config.yml");

        try {
            YamlConfiguration config = getConfig();
            if (config != null) {
                config.save(configFile);
            }
        } catch (IOException e) {
            this.getLogger().severe("Failed to save config: " + e.getMessage());
        }
    }

    public final File getDataFolder() {
        return new File(this.libraryPluginLoaderInstance.getDataFolder(), this.getPluginMeta().name());
    }
}