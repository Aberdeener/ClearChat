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

import me.aberdeener.clearchat.ClearChat;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A metadata value implementation that is more flexible.
 * @author yangyang200
 */
public class MetadataValue implements org.bukkit.metadata.MetadataValue {
    private Object value = null;
    private int _int = 0;
    private float _float = 0;
    private double _double = 0;
    private long _long = 0;
    private short _short = 0;
    private byte _byte = 0;
    private boolean _boolean = false;
    private Plugin plugin = ClearChat.getInstance();
    private String string = "MetadataValue";

    /**
     * Create a default fixed metadata value.
     */
    public MetadataValue() { }

    /**
     * Create a metadata value full of values.
     * @param values All the values
     */
    public MetadataValue(Object... values) {
        for (Object x : values) {
            if (x instanceof Integer)      _int = (Integer) x;
            else if (x instanceof Float)   _float = (Float) x;
            else if (x instanceof Double)  _double = (Double) x;
            else if (x instanceof Long)    _long = (Long) x;
            else if (x instanceof Short)   _short = (Short) x;
            else if (x instanceof Byte)    _byte = (Byte) x;
            else if (x instanceof Boolean) _boolean = (Boolean) x;
            else if (x instanceof String)  string = (String) x;
            else if (x instanceof Plugin)  plugin = (Plugin) x;
            else                           value = x;
        }
    }

    @Nullable
    @Override
    public Object value() {
        return value;
    }

    @Override
    public int asInt() {
        return _int;
    }

    @Override
    public float asFloat() {
        return _float;
    }

    @Override
    public double asDouble() {
        return _double;
    }

    @Override
    public long asLong() {
        return _long;
    }

    @Override
    public short asShort() {
        return _short;
    }

    @Override
    public byte asByte() {
        return _byte;
    }

    @Override
    public boolean asBoolean() {
        return _boolean;
    }

    @NotNull
    @Override
    public String asString() {
        return string;
    }

    @Nullable
    @Override
    public Plugin getOwningPlugin() {
        return plugin;
    }

    @Override
    public void invalidate() {
        value = null;
        _int = 0;
        _byte = 0;
        _double = 0;
        _float = 0;
        _short = 0;
        _long = 0;
        _boolean = false;
        string = "MetadataValue";
    }
}
