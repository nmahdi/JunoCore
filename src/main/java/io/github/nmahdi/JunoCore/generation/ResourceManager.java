package io.github.nmahdi.JunoCore.generation;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.generation.foraging.PlantGenerator;
import io.github.nmahdi.JunoCore.generation.woodcutting.TreeGenerator;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.player.stats.Skill;
import io.github.nmahdi.JunoCore.utils.JLogger;
import io.github.nmahdi.JunoCore.utils.JunoManager;
import io.github.nmahdi.JunoCore.dependencies.WorldEditManager;
import io.github.nmahdi.JunoCore.generation.mining.OreGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ResourceManager implements JunoManager {

    private boolean debugMode;
    private JCore main;
    private ItemManager itemManager;
    private Random random;
    private File folder;
    private ArrayList<ResourceType> resourceTypes = new ArrayList<>();
    private ArrayList<ResourceGenerator> generators = new ArrayList<>();
    private ArrayList<Integer> ticking = new ArrayList<>();

    public ResourceManager(JCore main){
        debugMode = main.getConfig().getBoolean("debug-mode.resource");
        this.main = main;
        this.itemManager = main.getItemManager();
        this.random = main.getRandom();
        this.folder = new File(main.getDataFolder(), "resources");
        if(!folder.exists()){
            folder.mkdirs();
        }
        loadResources();
        loadGenerators();
        startGenerators();
        tickGenerators();
        JLogger.log("Resource Manager has been enabled.");
    }

    public void createGenerator(Player player, String name, GeneratorType generatorType, ResourceType resourceType){
        File file = new File(folder, name+".yml");
        if(!file.exists()){
            try {
                ResourceGenerator generator = null;
                ArrayList<Location> locations = null;
                if(generatorType.equals(GeneratorType.Ore)) {
                    locations = (WorldEditManager.getLocationsFromSelection(player, Material.SPONGE));
                    generator = new OreGenerator(main, name, resourceType, STONE, player.getWorld(), locations, random);
                }
                if(generatorType.equals(GeneratorType.Tree)){
                    generator = new TreeGenerator(main, name, resourceType, player.getWorld(), random);
                }
                if(generatorType.equals(GeneratorType.Plant)){
                    locations = WorldEditManager.getLocationsFromSelection(player, Material.SPONGE);
                    generator = new PlantGenerator(name, resourceType, player.getWorld(), locations, random);
                }
                if(generator == null) return;
                generators.add(generator);
                file.createNewFile();
                save(file, generator.getName(), generator.getGeneratorType(), generator.getResourceType(), locations);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void loadGenerators(){
        debug("Loading generators...");

            for(File f : folder.listFiles()){
                if(f.getName().endsWith(".yml")) {

                    FileConfiguration c = YamlConfiguration.loadConfiguration(f);
                    debug(f.getName() + " located. Attempting to parse.");

                    if (c.contains("name") && c.contains("generator-type") && c.contains("resource-type")) {

                        debug("Attempting to load generator " + c.getString("name"));

                        ResourceGenerator generator = null;

                        debug("Generator type '" + c.getString("generator-type") + "' detected.");

                        ResourceType resourceType = getResourceType(c.getString("resource-type"));

                        ArrayList<Location> locations = new ArrayList<>();

                        if(c.contains("locations")) {
                            for (String s : c.getStringList("locations")) {
                                String[] loc = s.split(",");
                                locations.add(new Location(main.getPrimaryWorld(), Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2])));
                            }
                        }

                        if (resourceType != null) {

                            if (c.getString("generator-type").equals(GeneratorType.Ore.getId())) {
                                generator = new OreGenerator(main, c.getString("name"), resourceType, STONE, main.getPrimaryWorld(), locations, random);
                            }

                            if(c.getString("generator-type").equals(GeneratorType.Tree.getId())){
                                generator = new TreeGenerator(main, c.getString("name"), resourceType, main.getPrimaryWorld(), random);
                            }

                            if(c.getString("generator-type").equals(GeneratorType.Plant.getId())){
                                generator = new PlantGenerator(c.getString("name"), resourceType, main.getPrimaryWorld(), locations, random);
                            }

                        }

                        if (generator != null) {
                            generators.add(generator);
                            debug("Generator " + generator.getName() + " has been enabled. Type: " + generator.getGeneratorType().getId() + " Resource: " + generator.getResourceType().getId());
                        }
                    }
                }
            }
    }

    private void save(File file, String name, GeneratorType generatorType, ResourceType resourceType, ArrayList<Location> locations) throws IOException {
        FileConfiguration c = YamlConfiguration.loadConfiguration(file);
        c.set("name", name);
        c.set("generator-type", generatorType.getId());
        c.set("resource-type", resourceType.getId());
        if(locations != null) {
            ArrayList<String> locs = new ArrayList<>();
            for (Location location : locations) {
                locs.add(location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ());
            }
            c.set("locations", locs);
        }
        c.save(file);
    }

    private void startGenerators(){
        for(ResourceGenerator generator : generators){
            generator.start(main);
        }
    }

    private void tickGenerators(){
        for(ResourceGenerator generator : generators){
            ticking.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
                @Override
                public void run() {
                    generator.run(main);
                }
            }, 20, generator.getTickDelay()));
        }
    }




    @Override
    public void onDisable() {
        for(ResourceGenerator generator : generators){
            generator.end(main);
        }
        for(int i : ticking) {
            Bukkit.getScheduler().cancelTask(i);
        }
    }

    public ResourceType getResourceType(String id){
        for(ResourceType type : resourceTypes){
            if(type.getId().equals(id)) return type;
        }
        return null;
    }

    public ResourceType getResourceType(Block block){
        for(ResourceType type : resourceTypes){
            if(type.getMaterial() == block.getType()) return type;
        }
        return null;
    }

    public void addResource(ResourceType resourceType){
        this.resourceTypes.add(resourceType);
    }

    public ArrayList<ResourceType> getResourceTypes() {
        return resourceTypes;
    }

    public File getFolder() {
        return folder;
    }

    @Override
    public void debug(String s){
        JLogger.debug(this, s);
    }

    @Override
    public void setDebugMode(boolean mode) {
        debugMode = mode;
    }

    @Override
    public boolean isDebugging() {
        return debugMode;
    }

    public ResourceType COBBLESTONE;
    public ResourceType STONE;
    public ResourceType ANDESITE;
    public ResourceType CLAY;
    public ResourceType FLINT;
    public ResourceType COAL;
    public ResourceType IRON;
    public ResourceType GOLD;
    public ResourceType DIAMOND;
    public ResourceType EMERALD;
    public ResourceType OBSIDIAN;
    public ResourceType ETERNITE;

    public ResourceType OAK;
    public ResourceType SPRUCE;
    public ResourceType BIRCH;
    public ResourceType JUNGLE;
    public ResourceType ACACIA;
    public ResourceType DARK_OAK;
    public ResourceType MANGROVE;
    public ResourceType POPPY;

    private void loadResources(){
        COBBLESTONE = new ResourceType(this, "cobblestone", Material.COBBLESTONE, Skill.Mining, 1,
                itemManager.COBBLESTONE, itemManager.STONE, itemManager.COBBLESTONE, 1,
                ItemType.Pickaxe, 450, Material.BEDROCK);

        STONE = new ResourceType(this, "stone", Material.STONE, Skill.Mining, 1,
                itemManager.COBBLESTONE, itemManager.STONE, itemManager.STONE, 1,
                ItemType.Pickaxe, 500, Material.BEDROCK);

        OAK = new ResourceType(this, "oak", Material.OAK_LOG, Skill.Woodcutting, 10,
                itemManager.OAK_WOOD, itemManager.COAL, itemManager.OAK_WOOD, 1,
                ItemType.Axe, 300, Material.AIR);
    }

}
