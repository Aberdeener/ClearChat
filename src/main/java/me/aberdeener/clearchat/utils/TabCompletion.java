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

package me.aberdeener.clearchat.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple tab completer system
 *
 * @author yangyang200
 */
public final class TabCompletion {
    /**
     * Avoid misconstruction
     */
    private TabCompletion() {
    }

    /**
     * Gets a list of all online player's name.
     *
     * @param currentArg Current argument to get a best match
     * @return List of all online player's name with best match
     */
    public static List<String> allOnlinePlayers(String currentArg) {

        if (currentArg.equals("")) {
            List<String> output = new ArrayList<>();
            for (Player i : Bukkit.getOnlinePlayers())
                output.add(i.getName());
            Collections.sort(output);
            return output;
        } else {
            List<String> outputz = new ArrayList<>();
            for (Player i : Bukkit.getOnlinePlayers()) outputz.add(i.getName());
            List<String> output = new ArrayList<>();
            for (String x : outputz) if (x.startsWith(currentArg)) output.add(x);
            Collections.sort(output);
            return output;
        }
    }

    /**
     * Get the best match
     * @param currentArg The current argument
     * @param possibleMatches Possible matches
     * @return List of best matched index
     */
    public static List<String> bestMatch(String currentArg, List<String> possibleMatches) {
        if (currentArg.equals("")) {
            return possibleMatches;
        } else {
            List<String> outputz = new ArrayList<>(possibleMatches);
            List<String> output = new ArrayList<>();
            for (String x : outputz) if (x.startsWith(currentArg)) output.add(x);
            Collections.sort(output);
            return output;
        }
    }
}
