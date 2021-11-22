package dev.ricecx.frostygamerzone.minigameapi.regions.impl;

import com.google.gson.*;
import dev.ricecx.frostygamerzone.bukkitapi.LazyLocation;
import dev.ricecx.frostygamerzone.minigameapi.regions.Region;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.lang.reflect.Type;
import java.util.Iterator;

public class CuboidRegion extends Region<CuboidRegion> {
    private final int x1;
    private final int y1;
    private final int z1;

    //
    private final int x2;
    private final int y2;
    private final int z2;


    public CuboidRegion(String name, String world, int x1, int y1, int z1, int x2, int y2, int z2) {
        super(world, name);
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }

    @Override
    public boolean contains(Block block) {
        return false;
    }

    @Override
    public boolean contains(Location location) {
        return this.contains(location.getX(), location.getY(), location.getZ());
    }

    @Override
    public boolean contains(LazyLocation location) {
        return false;
    }

    @Override
    public boolean contains(double x, double y, double z) {
        x = x < 0.0D ? Math.ceil(x) : Math.floor(x);
        y = Math.floor(y);
        z = z < 0.0D ? Math.ceil(z) : Math.floor(z);

        return (this.x1 <= x && x <= this.x2) && (this.y1 <= y && y <= this.y2) && (this.z1 <= z && z <= this.z2);
    }


    @Override
    public Iterator<Block> blockIterator() {
        return new CuboidIterator(Bukkit.getWorld(this.world), this.x1, this.y1, this.z1, this.x2, this.y2, this.z2);
    }

    @Override
    public CuboidRegion deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        return new CuboidRegion(obj.get("name").getAsString(), obj.get("world").getAsString(), obj.get("x1").getAsInt(), obj.get("y1").getAsInt(), obj.get("z1").getAsInt(), obj.get("x2").getAsInt(), obj.get("y2").getAsInt(), obj.get("z2").getAsInt());
    }

    @Override
    public JsonElement serialize(CuboidRegion src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();

        obj.addProperty("world", this.world);
        obj.addProperty("x1", this.x1);
        obj.addProperty("y1", this.y1);
        obj.addProperty("z1", this.z1);
        obj.addProperty("x2", this.x2);
        obj.addProperty("y2", this.y2);
        obj.addProperty("z2", this.z2);
        obj.addProperty("name", this.name);

        return obj;
    }

    private class CuboidIterator implements Iterator<Block> {
        private final World w;
        private final int baseX;
        private final int baseY;
        private final int baseZ;
        private final int sizeX;
        private final int sizeY;
        private final int sizeZ;
        private int x;
        private int y;
        private int z;

        private CuboidIterator(World w, int x1, int y1, int z1, int x2, int y2, int z2) {
            this.w = w;
            this.baseX = x1;
            this.baseY = y1;
            this.baseZ = z1;
            this.sizeX = Math.abs(x2 - x1) + 1;
            this.sizeY = Math.abs(y2 - y1) + 1;
            this.sizeZ = Math.abs(z2 - z1) + 1;
            this.x = this.y = this.z = 0;
        }

        public boolean hasNext() {
            return this.x < this.sizeX && this.y < this.sizeY && this.z < this.sizeZ;
        }

        public Block next() {
            Block b = this.w.getBlockAt(this.baseX + this.x, this.baseY + this.y, this.baseZ + this.z);
            if (++this.x >= this.sizeX) {
                this.x = 0;
                if (++this.y >= this.sizeY) {
                    this.y = 0;
                    ++this.z;
                }
            }

            return b;
        }

        public void remove() {
        }
    }

}
