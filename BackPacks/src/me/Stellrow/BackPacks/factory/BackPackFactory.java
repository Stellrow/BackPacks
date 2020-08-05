package me.Stellrow.BackPacks.factory;

import me.Stellrow.BackPacks.BackPackItem;
import me.Stellrow.BackPacks.BackPacks;
import me.Stellrow.BackPacks.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BackPackFactory {
    private BackPackItem bpi;
    private final BackPacks pl;
    private ItemStack backPack;


    public BackPackFactory(BackPacks pl) {
        this.pl = pl;
        bpi = pl.bpi;
    }


    public void createItem(){
        backPack = new ItemStack(Material.valueOf(pl.getConfig().getString("BackPackItemConfig.type")));
        ItemMeta backPackMeta = backPack.getItemMeta();
        backPackMeta.setDisplayName(Utils.asColor(pl.getConfig().getString("BackPackItemConfig.name")));
        List<String> lore = Utils.loreAsColor(pl.getConfig().getStringList("BackPackItemConfig.lore"));
        lore.add(Utils.asColor("&a+"));
        backPackMeta.setLore(lore);
        backPackMeta.setCustomModelData(Integer.valueOf(pl.getConfig().getInt("BackPackItemConfig.custommodeldata")));
        backPackMeta.getPersistentDataContainer().set(pl.unclaimed, PersistentDataType.STRING,"unclaimed");
        backPack.setItemMeta(backPackMeta);

    }

    public ItemStack returnNewItem(){
        return backPack;
    }

    public ItemStack returnClaimedBackPack(){
        ItemStack cbackPack = new ItemStack(Material.valueOf(pl.getConfig().getString("BackPackItemConfig.type")));
        ItemMeta backPackMeta = cbackPack.getItemMeta();
        backPackMeta.setDisplayName(Utils.asColor(pl.getConfig().getString("BackPackItemConfig.name")));
        List<String> lore = Utils.loreAsColor(pl.getConfig().getStringList("BackPackItemConfig.lore"));
        backPackMeta.setLore(lore);
        backPackMeta.setCustomModelData(Integer.valueOf(pl.getConfig().getInt("BackPackItemConfig.custommodeldata")));
        backPackMeta.getPersistentDataContainer().set(pl.claimed, PersistentDataType.STRING,"claimed");
        backPackMeta.getPersistentDataContainer().set(pl.uuid,PersistentDataType.INTEGER,returnUUID());
        List<ItemStack> items = new ArrayList<>();
        for(int x = 0;x<9;x++){
            items.add(new ItemStack(Material.AIR));
        }
        backPackMeta.getPersistentDataContainer().set(pl.items,bpi,items);



        cbackPack.setItemMeta(backPackMeta);
        return  cbackPack;
    }
    private Random random = new Random();
    private int returnUUID(){
        return  random.nextInt(1000000)+1;
    }




}
