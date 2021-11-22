package dev.ricecx.frostygamerzone.minigameapi.regions;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import dev.ricecx.frostygamerzone.bukkitapi.LazyLocation;
import org.bukkit.Location;
import org.bukkit.block.Block;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Region<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    
    protected final String world;
    protected final String name;
    protected List<String> flags;
    
    
    public Region(String world, String name) {
        this.world = world;
        this.name = name;
    }
    
    public abstract boolean contains(Block block);
    public abstract boolean contains(Location location);
    public abstract boolean contains(LazyLocation location);
    public abstract boolean contains(double x, double y, double z);
    public abstract Iterator<Block> blockIterator();
}
