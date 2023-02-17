package io.github.nmahdi.JunoCore.dependencies;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.modifiers.stats.Runeable;
import io.github.nmahdi.JunoCore.item.stats.Rune;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.listeners.PlayerInventoryListener;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import io.github.nmahdi.JunoCore.utils.JLogger;
import io.github.nmahdi.JunoCore.utils.JunoManager;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.Map;

public class SQLManager implements JunoManager {

    private boolean debugMode = false;

    private final String connectionURL = "jdbc:mysql://localhost:3306/JunoCore";
    private final String user = "JunoCore";
    private final String pass = "JunoCore12!";

    private final String DATA_TABLE = "PlayerData";
    private final String INVENTORY_TABLE = "PlayerInventory";
    private final String STORAGE_TABLE = "PlayerStorage";

    private final String EMPTY = "EMPTY";

    private ItemManager itemManager;

    public SQLManager(JCore main){
        debugMode = main.getConfig().getBoolean("debug-mode.sql");
        this.itemManager = main.getItemManager();
        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            JLogger.log("MySQL driver not found.");
            throw new RuntimeException(e);
        }

        Connection connection = establishConnection();
        if(connection == null){
            debug("Establishing connection on startup failed.");
            return;
        }

        Statement statement = null;

        try {
            statement = connection.createStatement();

            if(!statement.executeQuery(tableExists(DATA_TABLE)).next()){

                debug(DATA_TABLE + " not found. Creating...");

                statement.execute("CREATE TABLE " + DATA_TABLE + " (UUID char(255) PRIMARY KEY, Name char(16) NOT NULL, " +
                        "Coins BIGINT NOT NULL, CombatXP BIGINT NOT NULL, MiningXP BIGINT NOT NULL, ForagingXP BIGINT NOT NULL, FishingXP BIGINT NOT NULL," +
                        "WoodcuttingXP BIGINT NOT NULL, FarmingXP BIGINT NOT NULL, MetalDetectingXP BIGINT NOT NULL);");

                debug("Created " + DATA_TABLE + ".");
            }

            if(!statement.executeQuery(tableExists(INVENTORY_TABLE)).next()){
                debug(INVENTORY_TABLE + " not found. Creating...");

                String inventoryCreate = "CREATE TABLE " + INVENTORY_TABLE + " (UUID char(255) PRIMARY KEY, Name char(16) NOT NULL," +
                        "Helmet char(255) NOT NULL, Chestplate char(255) NOT NULL, Leggings char(255) NOT NULL, Boots char(255) NOT NULL," +
                        "Cape char(255) NOT NULL, Bracelet char(255) NOT NULL, Ring char(255) NOT NULL, Headband char(255) NOT NULL, Necklace char(255) NOT NULL,";

                for(int i =0 ; i < 36; i++){
                    if(i != 35){
                        inventoryCreate += "Slot"+ i + " char(255) NOT NULL,";
                    }else{
                        inventoryCreate +="Slot"+i+" char(255) NOT NULL);";
                    }
                }

                statement.execute(inventoryCreate);

                debug("Created " + INVENTORY_TABLE + ".");
            }

            if(!statement.executeQuery(tableExists(STORAGE_TABLE)).next()){

                debug(STORAGE_TABLE + " not found. Creating...");

                String storageCreate = "CREATE TABLE " + STORAGE_TABLE + " (UUID char(255) PRIMARY KEY, Name char(16) NOT NULL,";

                for(int i = 0; i < 54; i++){
                    if(i != 53) {
                        storageCreate += "Slot" + i + " char(255) NOT NULL,";
                    }else{
                        storageCreate += "Slot" + i + " char(255) NOT NULL);";
                    }
                }

                statement.execute(storageCreate);

                debug("Created " + STORAGE_TABLE + ".");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            statement.close();
            connection.close();
            debug("Connection to database closed.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadPlayerData(GamePlayer player){
        Connection connection = establishConnection();
        if(connection == null){
            debug("Connection is null");
            return;
        }

        Statement statement = null;
        try{
            statement = connection.createStatement();

            String loadInventoryQuery = "SELECT * FROM " + INVENTORY_TABLE + " WHERE UUID='" + player.getUniqueId().toString() + "';";
            ResultSet inventory = statement.executeQuery(loadInventoryQuery);

            while(inventory.next()){
                if(!inventory.getString("Helmet").equals(EMPTY)) player.getInventory().setHelmet(deserializeItem(player, inventory.getString("Helmet")));
                if(!inventory.getString("Chestplate").equals(EMPTY)) player.getInventory().setChestplate(deserializeItem(player, inventory.getString("Chestplate")));
                if(!inventory.getString("Leggings").equals(EMPTY)) player.getInventory().setLeggings(deserializeItem(player, inventory.getString("Leggings")));
                if(!inventory.getString("Boots").equals(EMPTY)) player.getInventory().setBoots(deserializeItem(player, inventory.getString("Boots")));

                if(!inventory.getString("Cape").equals(EMPTY)) player.equipment.put(PlayerInventoryListener.CAPE_SLOT, deserializeItem(player, inventory.getString("Cape")));
                if(!inventory.getString("Bracelet").equals(EMPTY)) player.equipment.put(PlayerInventoryListener.BRACELET_SLOT, deserializeItem(player, inventory.getString("Bracelet")));
                if(!inventory.getString("Ring").equals(EMPTY)) player.equipment.put(PlayerInventoryListener.RING_SLOT, deserializeItem(player, inventory.getString("Ring")));
                if(!inventory.getString("Headband").equals(EMPTY)) player.equipment.put(PlayerInventoryListener.HEADBAND_SLOT, deserializeItem(player, inventory.getString("Headband")));
                if(!inventory.getString("Necklace").equals(EMPTY)) player.equipment.put(PlayerInventoryListener.NECKLACE_SLOT, deserializeItem(player, inventory.getString("Necklace")));

                for(int i = 0; i < 36; i++){
                    if(!inventory.getString("Slot"+i).equals(EMPTY))
                        player.getInventory().setItem(i, deserializeItem(player, inventory.getString("Slot"+i)));
                }
            }

            String loadDataQuery = "SELECT * FROM " + DATA_TABLE + " WHERE UUID='" + player.getUniqueId().toString() + "';";
            ResultSet data = statement.executeQuery(loadDataQuery);

            boolean exists = false;

            while(data.next()){
                exists = true;
                player.loadData(data);
            }
            if(!exists){
                player.newPlayer();
            }

            String loadStorageQuery = "SELECT * FROM " + STORAGE_TABLE + " WHERE UUID='" + player.getUniqueId().toString() + "';";
            ResultSet storage = statement.executeQuery(loadStorageQuery);

            while(storage.next()){
                for(int i = 0; i < player.getStorage().getSize(); i++){
                    player.getStorage().setItem(i, deserializeItem(player, storage.getString("Slot"+i)));
                }
            }

            debug("Loaded " + player.getName() + "'s data.");

        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        try {
            statement.close();
            connection.close();
            debug("Connection to database closed.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void savePlayerData(GamePlayer player){
        Connection connection = establishConnection();

        if (connection == null) {
            debug("Connection is null.");
            return;
        }

        Statement statement = null;

        try {

            statement = connection.createStatement();

            debug("Saving " + player.getName() + "'s data...");

            String[] inventory = new String[36];

            for(int i = 0; i < 36; i++) {
                ItemStack stack = player.getInventory().getItem(i);
                String item = "EMPTY";
                if (!InventoryHelper.isAirOrNull(stack)) {
                    item = serializeItem(new NBTGameItem(stack));
                }
                inventory[i] = item;
            }

            String inventorySave = "INSERT INTO " + INVENTORY_TABLE + " VALUES(" +
                    inSingleQuotation(player.getUniqueId().toString()) + "," + inSingleQuotation(player.getName()) + "," +
                    inSingleQuotation(serializeItem(player.getNBTHelmet())) + "," +
                    inSingleQuotation(serializeItem(player.getNBTChestplate())) + "," +
                    inSingleQuotation(serializeItem(player.getNBTLeggings())) + "," +
                    inSingleQuotation(serializeItem(player.getNBTBoots())) + "," +
                    inSingleQuotation(serializeItem(player.getNBTCape())) + "," +
                    inSingleQuotation(serializeItem(player.getNBTBracelet())) + "," +
                    inSingleQuotation(serializeItem(player.getNBTRing())) + "," +
                    inSingleQuotation(serializeItem(player.getNBTHeadband())) + "," +
                    inSingleQuotation(serializeItem(player.getNBTNecklace()));

            for(int i = 0; i < inventory.length; i++) {
                inventorySave += "," +  inSingleQuotation(inventory[i]);
            }

            inventorySave +=") ON DUPLICATE KEY UPDATE " +
                    "Name=" + inSingleQuotation(player.getName()) +
                    ",Helmet=" + inSingleQuotation(serializeItem(player.getNBTHelmet())) +
                    ",Chestplate=" + inSingleQuotation(serializeItem(player.getNBTChestplate())) +
                    ",Leggings=" + inSingleQuotation(serializeItem(player.getNBTLeggings())) +
                    ",Boots=" + inSingleQuotation(serializeItem(player.getNBTBoots())) +
                    ",Cape=" + inSingleQuotation(serializeItem(player.getNBTCape())) +
                    ",Bracelet=" + inSingleQuotation(serializeItem(player.getNBTBracelet())) +
                    ",Ring=" + inSingleQuotation(serializeItem(player.getNBTRing())) +
                    ",Headband=" + inSingleQuotation(serializeItem(player.getNBTHeadband())) +
                    ",Necklace=" + inSingleQuotation(serializeItem(player.getNBTNecklace()));

            for(int i = 0; i < inventory.length; i++) {
                inventorySave += ",Slot" + i + "=" + inSingleQuotation(inventory[i]);
            }

            inventorySave += ";";
            statement.execute(inventorySave);

            debug("Inventory saved.");

            String dataSave = "INSERT INTO " + DATA_TABLE + " VALUES(" +
                    inSingleQuotation(player.getUniqueId().toString()) + "," + inSingleQuotation(player.getName()) + "," +
                    player.getCoins() + "," +
                    player.getCombat().getXP() + "," +
                    player.getMining().getXP() + "," +
                    player.getForaging().getXP() + "," +
                    player.getFishing().getXP() + "," +
                    player.getWoodcutting().getXP() + "," +
                    player.getFarming().getXP() + "," +
                    player.getMetalDetecting().getXP() + ") ON DUPLICATE KEY UPDATE " +
                    "Name='" + player.getName() + "',Coins=" + player.getCoins() + ",CombatXP=" + player.getCombat().getXP()
                    + ",MiningXP=" + player.getMining().getXP() + ",ForagingXP=" + player.getForaging().getXP() + ",FishingXP=" + player.getFishing().getXP()
                    + ",WoodcuttingXP=" + player.getWoodcutting().getXP() + ",FarmingXP=" + player.getFarming().getXP() + ",MetalDetectingXP=" + player.getMetalDetecting().getXP() + ";";
            statement.execute(dataSave);

            debug("Data saved.");

            String[] storage = new String[player.getStorage().getSize()];

            for(int i = 0; i < player.getStorage().getSize(); i++){
                ItemStack stack = player.getStorage().getItem(i);
                String item = "EMPTY";
                if(!InventoryHelper.isAirOrNull(stack)){
                    item = serializeItem(new NBTGameItem(stack));
                }
                storage[i] = item;
            }

            String storageSave = "INSERT INTO " + STORAGE_TABLE + " VALUES(" +
                    inSingleQuotation(player.getUniqueId().toString()) + ", " + inSingleQuotation(player.getName());

            for(int i = 0; i < storage.length; i++){
                storageSave+= "," + inSingleQuotation(storage[i]);
            }

            storageSave += ") ON DUPLICATE KEY UPDATE " +
            "Name=" + inSingleQuotation(player.getName());

            for(int i = 0; i < storage.length; i++){
                storageSave+=",Slot" + i + "=" + inSingleQuotation(storage[i]);
            }

            statement.execute(storageSave + ";");

            debug("Storage saved.");


            debug("Saved " + player.getName() + "'s data.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            statement.close();
            connection.close();
            debug("Connection to database closed.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private Connection establishConnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(connectionURL, user, pass);
            debug("Established connection with database.");
        } catch (SQLException e) {
            debug("Error establishing connection.");
            throw new RuntimeException(e);
        }
        return connection;
    }

    private String serializeItem(NBTGameItem item){
        if(item == null) return EMPTY;
        if(!item.hasID()) return EMPTY;
        String s = item.getID() + "," + item.getItem().getAmount();
        if(item.hasRunes()){
            s+=",";
            for(Map.Entry<Rune, Integer> runes : item.getRunes().entrySet()){
                s+=runes.getKey().toString()+":"+runes.getValue()+"/";
            }
        }
        return s;
    }

    private ItemStack deserializeItem(GamePlayer gamePlayer, String s){
        if(s.equals(EMPTY)) return null;
        String[] string = s.split(",");

        GameItem gameItem = itemManager.getItem(string[0]);

        if(gameItem == null) return null;
        NBTGameItem item = new NBTGameItem(ItemBuilder.buildGameItem(gameItem, Integer.parseInt(string[1])));

        if(gameItem instanceof Runeable && string.length > 2){

            String[] runes = string[2].split("/");

            for (String value : runes) {
                String[] r = value.split(":");

                String rune = r[0];
                int level = Integer.parseInt(r[1]);

                for (int j = 0; j < level; j++) {
                    item.addRune(Rune.valueOf(rune));
                }

            }

        }

        item.getItem().setItemMeta(ItemBuilder.updateMeta(gamePlayer, gameItem, item));

        return item.getItem();
    }

    private String inSingleQuotation(String s){
        return "'" + s + "'";
    }

    private String tableExists(String tableName){
        return "SELECT * FROM information_schema.tables WHERE table_schema='junocore' AND table_name='" + tableName + "' LIMIT 1;";
    }

    private String playerExists(String tableName, String uuid){
        return "SELECT * FROM " + tableName + " WHERE UUID='" + uuid+ "';";
    }

    @Override
    public boolean isDebugging() {
        return debugMode;
    }

    @Override
    public void debug(String s) {
        JLogger.debug(this, s);
    }

    @Override
    public void setDebugMode(boolean mode) {
        debugMode = mode;
    }

    @Override
    public void onDisable() {

    }

}
