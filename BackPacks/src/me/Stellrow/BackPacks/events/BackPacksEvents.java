package me.Stellrow.BackPacks.events;

import me.Stellrow.BackPacks.BackPacks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class BackPacksEvents implements Listener {
    private final BackPacks pl;

    public BackPacksEvents(BackPacks pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if(!(event.getAction()==Action.RIGHT_CLICK_AIR||event.getAction()==Action.RIGHT_CLICK_BLOCK)){
            return;
        }
        ItemStack i = event.getItem();
        if(i==null){
            return;
        }
        if(i.getItemMeta()==null){
            return;
        }

        if(i.getItemMeta().getPersistentDataContainer().has(pl.unclaimed, PersistentDataType.STRING)){
            event.setCancelled(true);
            event.getItem().setAmount(event.getItem().getAmount()-1);
            addItem(pl.getBackPackFactory().returnClaimedBackPack(),event.getPlayer());
            return;
        }
        if(i.getItemMeta().getPersistentDataContainer().has(pl.claimed,PersistentDataType.STRING)){
            event.setCancelled(true);
            pl.getBackPackGUI().openBackPack(event.getPlayer(),i);
            return;
        }



    }

    private void addItem(ItemStack toAdd, Player target){
        HashMap<Integer,ItemStack> left = target.getInventory().addItem(toAdd);
        for(Integer key : left.keySet()){
            target.getWorld().dropItemNaturally(target.getLocation(),left.get(key));
        }
    }

}
