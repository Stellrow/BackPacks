package me.Stellrow.BackPacks.gui;

import me.Stellrow.BackPacks.BackPacks;
import me.Stellrow.BackPacks.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class BackPackGUI implements Listener {
    private final BackPacks pl;


    private Set<Inventory> opened = new HashSet<Inventory>();



    public BackPackGUI(BackPacks pl) {
        this.pl = pl;
    }

    public void openBackPack(Player toOpen, ItemStack backPack){
        ItemMeta im = backPack.getItemMeta();
        Integer uuid = im.getPersistentDataContainer().get(pl.uuid, PersistentDataType.INTEGER);
        Inventory inv = Bukkit.createInventory(null,9, Utils.asColor("&aBackPack - "+uuid));
        List<ItemStack> itemsInside = new ArrayList<>();
        itemsInside = im.getPersistentDataContainer().get(pl.items,pl.bpi);
        for(int x  =0;x<9;x++){
            inv.setItem(x,itemsInside.get(x));
        }
        opened.add(inv);

        toOpen.openInventory(inv);

    }

    @EventHandler
    public void onBackPackClick(InventoryClickEvent event){
        if(opened.contains(event.getView().getTopInventory())){
            Player p = (Player) event.getWhoClicked();
            Integer slot = event.getSlot();
            List<ItemStack> items = new ArrayList<>();
            ItemStack[] inside = event.getView().getTopInventory().getContents();
            for(int x = 0;x<9;x++){
                if(x==slot){
                    items.add(new ItemStack(Material.AIR));
                }else{

                if(inside[x]==null){
                    items.add(new ItemStack(Material.AIR));
                }else{
                    items.add(inside[x]);
                }
                }
            }
            //Main hand
            if(p.getInventory().getItemInMainHand().hasItemMeta()){
                ItemMeta im = p.getInventory().getItemInMainHand().getItemMeta();
                if(im.getPersistentDataContainer().has(pl.claimed,PersistentDataType.STRING)){
                    im.getPersistentDataContainer().remove(pl.items);
                    im.getPersistentDataContainer().set(pl.items,pl.bpi,items);
                    ItemStack item = p.getInventory().getItemInMainHand();
                    item.setItemMeta(im);
                    p.getInventory().setItemInMainHand(item);
                    return;
                }
            }
            //Off hand
            if(p.getInventory().getItemInOffHand().hasItemMeta()){
                ItemMeta im = p.getInventory().getItemInOffHand().getItemMeta();
                if(im.getPersistentDataContainer().has(pl.claimed,PersistentDataType.STRING)){
                    im.getPersistentDataContainer().remove(pl.items);
                    im.getPersistentDataContainer().set(pl.items,pl.bpi,items);
                    ItemStack item = p.getInventory().getItemInMainHand();
                    item.setItemMeta(im);
                    p.getInventory().setItemInMainHand(item);
                    return;
                }
            }


        }
        if(event.getCurrentItem()==null){
            return;
        }
        if(!event.getCurrentItem().hasItemMeta()){
            return;
        }


        if(event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(pl.claimed,PersistentDataType.STRING)){
            if(opened.contains(event.getInventory())){
                event.setCancelled(true);
            }
        }

    }


    @EventHandler
    public void onClose(InventoryCloseEvent event){
        if(opened.contains(event.getInventory())){
            opened.remove(event.getInventory());
            List<ItemStack> items = new ArrayList<>();
            ItemStack[] inside = event.getInventory().getContents();
            for(int x = 0;x<9;x++){
                if(inside[x]==null){
                    items.add(new ItemStack(Material.AIR));
                }else{
                    items.add(inside[x]);
                }
            }
            //Main hand
            if(event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()){
                ItemMeta im = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();
                if(im.getPersistentDataContainer().has(pl.claimed,PersistentDataType.STRING)){
                    im.getPersistentDataContainer().remove(pl.items);
                    im.getPersistentDataContainer().set(pl.items,pl.bpi,items);
                    event.getPlayer().getInventory().getItemInMainHand().setItemMeta(im);
                    return;
                }
            }
            //Off hand
            if(event.getPlayer().getInventory().getItemInOffHand().hasItemMeta()){
                ItemMeta im = event.getPlayer().getInventory().getItemInOffHand().getItemMeta();
                if(im.getPersistentDataContainer().has(pl.claimed,PersistentDataType.STRING)){
                    im.getPersistentDataContainer().remove(pl.items);
                    im.getPersistentDataContainer().set(pl.items,pl.bpi,items);
                    event.getPlayer().getInventory().getItemInOffHand().setItemMeta(im);
                    return;
                }
            }
        }

    }


}
