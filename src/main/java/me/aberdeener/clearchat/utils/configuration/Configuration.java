/*
 * The VaultCore project
 * This program is created by yangyang200, and some of the VaultMC developers.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package me.aberdeener.clearchat.utils.configuration;

import me.aberdeener.clearchat.ClearChat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

/**
 * Represents an instance of a configuration.
 * @author yangyang200
 */
public class Configuration {
    private File file;
    private FileConfiguration config;
    private boolean initialized;

    /**
     * Gets the configuration represented by this class.
     * @return The configuration.
     */
    public FileConfiguration getConfig() {
        return this.config;
    }

    /**
     * Gets the file represented by this class.
     * @return The file
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Check if the configuration is initialized or not.
     * @return Initialization status
     */
    public boolean isInitialized() {
        return this.initialized;
    }

    /**
     * Set the initialization status of the configuration.
     * @param initialized Initialization status
     */
    private void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    /**
     * Saves the configuration
     * @throws IOException If isn't initialized, or other exception
     */
    public void save() throws IOException {
        if (!this.isInitialized()) throw new IOException("Not initialized");
        this.config.save(this.file);
    }

    /**
     * Reloads the configuration
     * @throws IOException If isn't initialized, or other exception
     */
    public void reload() throws IOException {
        if (!this.isInitialized()) throw new IOException("Not initialized");
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    /**
     * Read from an input stream
     * @param stream The input stream to read from
     * @return The contents
     */
    public String readInputStream(InputStream stream) {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String resp;
        try {
            while ((resp = reader.readLine()) != null) {
                builder.append(resp);
                builder.append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * Load this configuration from an inner resource.
     * @param from Name of the inner resource
     * @throws IOException If data folder non-exist, or other exception
     */
    public void load(String from) throws IOException {
        if (!ClearChat.getInstance().getDataFolder().exists()) throw new IOException("Data folder not initialized");
        this.file = new File(ClearChat.getInstance().getDataFolder(), from);
        if (!this.file.exists()) {
            if (!this.file.createNewFile()) throw new IOException("File creation failed");
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.file));
            writer.write(readInputStream(ClearChat.getInstance().getClass().getResourceAsStream("/" + from)));
            writer.flush();
            writer.close();
            this.config = YamlConfiguration.loadConfiguration(this.file);
            this.setInitialized(true);
        } else {
            this.config = YamlConfiguration.loadConfiguration(this.file);
        }
        this.setInitialized(true);
    }
}
