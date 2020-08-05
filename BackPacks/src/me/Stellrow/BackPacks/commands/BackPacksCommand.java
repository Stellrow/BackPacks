package me.Stellrow.BackPacks.commands;

import me.Stellrow.BackPacks.BackPacks;
import me.Stellrow.BackPacks.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;


public class BackPacksCommand implements CommandExecutor {
    private final BackPacks pl;




    public BackPacksCommand(BackPacks pl) {
        this.pl = pl;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String ar, String[] args) {


        if(args.length==3&&args[0].equalsIgnoreCase("give")){
            if(sender.hasPermission("backpacks.give")){
                Player target = Bukkit.getPlayer(args[1]);
                if(target==null){
                    sender.sendMessage(Utils.asColor("&cNo player found with this name! "+args[1]));
                    return true;
                }
                try{
                    Integer amount = Integer.parseInt(args[2]);
                    ItemStack toGive = pl.getBackPackFactory().returnNewItem();
                    toGive.setAmount(amount);
                    givePlayer(target,toGive);
                    sender.sendMessage(Utils.asColor("&aGave that player a backpack!"));
                    return true;

                }catch (IllegalArgumentException exception){
                    sender.sendMessage(Utils.asColor("&c"+args[2]+" is not a number!"));
                    return true;
                }



            }else{
                sender.sendMessage(Utils.asColor("&cNo permission to use this command!"));
                return true;
            }
        }
        sender.sendMessage(Utils.asColor("&aUsage: /bp give <player> <amount>"));

        return true;
    }

    private void givePlayer(Player target,ItemStack item){
        HashMap<Integer,ItemStack> remaining = target.getInventory().addItem(item);
        for(Integer key : remaining.keySet()){
            target.getWorld().dropItemNaturally(target.getLocation(),remaining.get(key));
        }
    }
}
