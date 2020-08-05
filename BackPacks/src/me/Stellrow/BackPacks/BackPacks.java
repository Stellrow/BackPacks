package me.Stellrow.BackPacks;

import me.Stellrow.BackPacks.commands.BackPacksCommand;
import me.Stellrow.BackPacks.events.BackPacksEvents;
import me.Stellrow.BackPacks.factory.BackPackFactory;
import me.Stellrow.BackPacks.gui.BackPackGUI;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class BackPacks extends JavaPlugin {
    public NamespacedKey recipeKey = new NamespacedKey(this,"bpkey");
    public BackPackItem bpi = new BackPackItem();
    private BackPackFactory backPackFactory = new BackPackFactory(this);
    private BackPackGUI backPackGUI = new BackPackGUI(this);

    public BackPackFactory getBackPackFactory() {
        return backPackFactory;
    }

    //Important namespacedkeys
    public NamespacedKey unclaimed = new NamespacedKey(this,"unclaimed-backpack");
    public NamespacedKey claimed = new NamespacedKey(this,"claimed-backpack");
    public NamespacedKey uuid = new NamespacedKey(this,"backpack-uuid");

    //Inventory nmsk
    public NamespacedKey items = new NamespacedKey(this,"items");

    public void onEnable(){
        loadConfig();
        backPackFactory.createItem();
        getCommand("bp").setExecutor(new BackPacksCommand(this));
        registerEvents();
        registerRecipe();


    }
    public void registerEvents(){
        getServer().getPluginManager().registerEvents(new BackPacksEvents(this),this);
        getServer().getPluginManager().registerEvents(backPackGUI,this);

    }
    private void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
    public void reload(){
        reloadConfig();
        backPackFactory.createItem();
    }

    //Namespacedkey creator

    public NamespacedKey getNewKey(String keyValue){
        return new NamespacedKey(this,keyValue);
    }

    public BackPackGUI getBackPackGUI() {
        return backPackGUI;
    }
    private void registerRecipe(){
        if(getConfig().getBoolean("General.allow-crafting")){
            ShapelessRecipe recipe = new ShapelessRecipe(recipeKey,getBackPackFactory().returnNewItem());
            recipe.addIngredient(7,Material.LEATHER);
            recipe.addIngredient(1,Material.CHEST);
            recipe.addIngredient(1,Material.ENDER_EYE);
            getServer().addRecipe(recipe);
        }
    }

}
