package io.github.nmahdi.JunoCore.dependencies;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.stats.Rune;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.combat.EquipmentListener;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import io.github.nmahdi.JunoCore.utils.JLogger;
import io.github.nmahdi.JunoCore.utils.JunoManager;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.Map;

public class SQLManager implements JunoManager {

    private final String connectionURL = "jdbc:mysql://localhost:3306/JunoCore";
    private final String user = "JunoCore";
    private final String pass = "JunoCore12!";

    private final String DATA_TABLE = "PlayerData";
    private final String INVENTORY_TABLE = "PlayerInventory";

    private final String EMPTY = "EMPTY";

    public SQLManager(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            JLogger.log("MySQL driver not found.");
            throw new RuntimeException(e);
        }
    }

    public void loadPlayerData(GamePlayer player){
        Connection connection = establishConnection();
        if(connection == null){
            debug("Connection is null.");
            return;
        }

        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(existQuery(player));

            if(set.next()){
                debug("Found UUID matching " + player.getName() + "'s UUID. Attempting to load...");

                String invQuery = "SELECT * FROM " + INVENTORY_TABLE + " WHERE UUID='" + player.getUniqueId().toString()+"';";
                ResultSet inv = statement.executeQuery(invQuery);

                while(inv.next()){
                    if(!inv.getString("Helmet").equals(EMPTY)) player.getInventory().setHelmet(unserialize(inv.getString("Helmet")));
                    if(!inv.getString("Chestplate").equals(EMPTY)) player.getInventory().setChestplate(unserialize(inv.getString("Chestplate")));
                    if(!inv.getString("Leggings").equals(EMPTY)) player.getInventory().setLeggings(unserialize(inv.getString("Leggings")));
                    if(!inv.getString("Boots").equals(EMPTY)) player.getInventory().setBoots(unserialize(inv.getString("Boots")));

                    if(!inv.getString("Cape").equals(EMPTY)) player.equipment.put(EquipmentListener.CAPE_SLOT, unserialize(inv.getString("Cape")));
                    if(!inv.getString("Bracelet").equals(EMPTY)) player.equipment.put(EquipmentListener.BRACELET_SLOT, unserialize(inv.getString("Bracelet")));
                    if(!inv.getString("Ring").equals(EMPTY)) player.equipment.put(EquipmentListener.RING_SLOT, unserialize(inv.getString("Ring")));
                    if(!inv.getString("Headband").equals(EMPTY)) player.equipment.put(EquipmentListener.HEADBAND_SLOT, unserialize(inv.getString("Headband")));
                    if(!inv.getString("Necklace").equals(EMPTY)) player.equipment.put(EquipmentListener.NECKLACE_SLOT, unserialize(inv.getString("Necklace")));

                    for(int i = 0; i < 36; i++){
                        if(!inv.getString("Slot"+(i+1)).equals(EMPTY))
                            player.getInventory().setItem(i, unserialize(inv.getString("Slot"+(i+1))));
                    }
                }

                String dataQuery = "SELECT * FROM " + DATA_TABLE + " WHERE UUID='" + player.getUniqueId().toString()+"';";
                ResultSet rs = statement.executeQuery(dataQuery);

                while(rs.next()) {
                    player.loadData(rs);
                }


                debug("Loaded " + player.getName() + "'s data.");
            }else{
                debug("Failed to find " + player.getName() + "'s UUID in the database. Setting default parameters.");
                player.newPlayer();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void savePlayerData(GamePlayer player) {
        Connection connection = establishConnection();
        if (connection == null) {
            debug("Connection is null.");
            return;
        }

        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(existQuery(player));

            if (set.next()) {
                debug("Found UUID matching " + player.getName() + "'s UUID. Attempting to save...");

                String upDataQuery = "UPDATE " + DATA_TABLE + " SET Name='" + player.getName() + "',Coins=" + player.getCoins() + ",CombatXP=" + player.getCombat().getXP()
                        + ",MiningXP=" + player.getMining().getXP() + ",ForagingXP=" + player.getForaging().getXP() + ",FishingXP=" + player.getFishing().getXP()
                        + ",WoodcuttingXP=" + player.getWoodcutting().getXP() + ",FarmingXP=" + player.getFarming().getXP() + ",MetalDetectingXP=" + player.getMetalDetecting().getXP() +
                        " WHERE UUID='" + player.getUniqueId().toString() + "';";
                statement.execute(upDataQuery);

                String upInvQuery = "UPDATE " + INVENTORY_TABLE + " SET " +
                        "Helmet='" + serialize(player.getNBTHelmet()) +
                        "',Chestplate='" + serialize(player.getNBTChestplate()) +
                        "',Leggings='" + serialize(player.getNBTLeggings()) +
                        "',Boots='" + serialize(player.getNBTBoots()) +
                        "',Cape='" + serialize(player.getNBTCape()) +
                        "',Bracelet='" + serialize(player.getNBTBracelet()) +
                        "',Ring='" + serialize(player.getNBTRing()) +
                        "',Headband='" + serialize(player.getNBTHeadband()) +
                        "',Necklace='" + serialize(player.getNBTNecklace()) + "',";

                for(int i = 0; i < 36; i++) {
                    ItemStack stack = player.getInventory().getItem(i);
                    String item = "EMPTY";
                    if (!InventoryHelper.isAirOrNull(stack)) {
                        item = serialize(new NBTGameItem(stack));
                    }
                    upInvQuery += "Slot" + (i+1) + "='" + item + "'";
                    if (i != 35) {
                        upInvQuery += ",";
                    }
                }

                upInvQuery+= " WHERE UUID='" + player.getUniqueId().toString() + "';";

                statement.execute(upInvQuery);

                debug("Saved " + player.getName() + "'s data.");
            } else {
                String dataQuery = "INSERT INTO " + DATA_TABLE + " (UUID, Name, Coins, CombatXP, MiningXP, ForagingXP, FishingXP, WoodcuttingXP, FarmingXP, MetalDetectingXP) " +
                        "VALUES('" + player.getUniqueId().toString() +"','" + player.getName() + "'," + player.getCoins() + "," +
                        player.getCombat().getXP() + "," +
                        player.getMining().getXP() + "," +
                        player.getForaging().getXP() + "," +
                        player.getFishing().getXP() + "," +
                        player.getWoodcutting().getXP() + "," +
                        player.getFishing().getXP() + "," +
                        player.getMetalDetecting().getXP() + ");";
                statement.execute(dataQuery);

                String invQuery = "INSERT INTO " + INVENTORY_TABLE + "(UUID, Helmet, Chestplate, Leggings, Boots, Cape, Bracelet, Ring, Headband, Necklace, ";
                for(int i = 0; i < 36; i++){
                    invQuery+="Slot" + (i+1);
                    if (i != 35) {
                        invQuery += ",";
                    }
                }
                invQuery+=") VALUES('" + player.getUniqueId().toString() + "'" +
                        ",'" + serialize(player.getNBTHelmet()) +
                        "','" + serialize(player.getNBTChestplate()) +
                        "','" + serialize(player.getNBTLeggings()) +
                        "','" + serialize(player.getNBTBoots()) +
                        "','" + serialize(player.getNBTCape()) +
                        "','" + serialize(player.getNBTBracelet()) +
                        "','" + serialize(player.getNBTRing()) +
                        "','" + serialize(player.getNBTHeadband()) +
                        "','" + serialize(player.getNBTNecklace()) + "',";
                for(int i = 0; i < 36; i++) {
                    ItemStack stack = player.getInventory().getItem(i);
                    String item = "EMPTY";
                    if (!InventoryHelper.isAirOrNull(stack)) {
                        item = serialize(new NBTGameItem(stack));
                    }
                    invQuery += "'" + item + "'";
                    if (i != 35) {
                        invQuery += ",";
                    }
                }
                invQuery += ");";

                statement.execute(invQuery);

                debug("Inserted " + player.getName() + "'s info to " + DATA_TABLE + ". UUID: " + player.getUniqueId().toString() + ". Coins: " + player.getCoins());
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

    private String existQuery(GamePlayer player){
        return "SELECT * FROM " + DATA_TABLE + " WHERE UUID='" + player.getUniqueId().toString() + "';";
    }

    private String serialize(NBTGameItem item){
        if(item == null) return EMPTY;
        if(!item.hasID()) return EMPTY;
        String s = item.getID();
        if(item.hasRunes()){
            s+=",";
            for(Map.Entry<Rune, Integer> runes : item.getRunes().entrySet()){
                s+=runes.getKey().toString()+":"+runes.getValue()+"/";
            }
        }
        return s;
    }

    private ItemStack unserialize(String s){
        if(s.equals(EMPTY)) return null;
        String[] string = s.split(",");

        GameItem gameItem = GameItem.getItem(string[0]);

        if(gameItem == null) return null;
        NBTGameItem item = new NBTGameItem(ItemBuilder.buildGameItem(gameItem));

        if(gameItem.canApplyRunes() && string.length > 1){

            String[] runes = string[1].split("/");

            for (String value : runes) {
                String[] r = value.split(":");

                String rune = r[0];
                int level = Integer.parseInt(r[1]);

                for (int j = 0; j < level; j++) {
                    item.addRune(Rune.valueOf(rune));
                }

            }

        }

        item.getItem().setItemMeta(ItemBuilder.updateMeta(gameItem, item));

        return item.getItem();
    }

    @Override
    public boolean isDebugging() {
        return false;
    }

    @Override
    public void debug(String s) {
        JLogger.debug(this, s);
    }

    @Override
    public void onDisable() {

    }

}
