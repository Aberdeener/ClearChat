/*
 * MajorChat plugin project
 * This program is created by Aberdeener and yangyang200.
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages configuration for YangCore
 * @author yangyang200
 */
public class ConfigurationManager {
    /**
     * A list of all the configurations loaded.
     */
    public static List<Configuration> configurations = new ArrayList<>();  // Moment of life when list is null

    /**
     * Creates the data folder.
     * This is not overwriting anything.
     */
    public void setupDataFolder() {
        File file = ClearChat.getInstance().getDataFolder();
        if (!file.exists())
            file.mkdir();
    }

    /**
     * Sets up from an embed resource.
     * @param resourceName The name of the resource. e.g. config.yml
     * @return             The configuration loaded
     */
    public Configuration loadConfigFromResource(String resourceName) {
        setupDataFolder();
        Configuration config = new Configuration();
        try {
            config.load(resourceName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        configurations.add(config);
        return config;
    }
}
