package me.Stellrow.BackPacks;


import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BackPackItem implements PersistentDataType<byte[], List<ItemStack>> {

    private byte[] convertToBytes(Object object) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (
                ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bos.toByteArray();
    }

    private Object convertFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        try (
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInput in = new ObjectInputStream(bis))
            {
                return in.readObject();

            }
        }


    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public Class<List<ItemStack>> getComplexType() {
        return null;
    }


    @Override
    public byte[] toPrimitive(List<ItemStack> itemStacks, PersistentDataAdapterContext persistentDataAdapterContext) {
        List<Map<String,Object>> raw = new ArrayList<>();
        for(ItemStack item : itemStacks){
            raw.add(item.serialize());
        }
        return convertToBytes(raw);
    }

    @Override
    public List<ItemStack> fromPrimitive(byte[] bytes, PersistentDataAdapterContext persistentDataAdapterContext) {
        List<Map<String,Object>> raw = new ArrayList<>();
        List<ItemStack> toReturn = new ArrayList<>();
        try {
            raw = (List<Map<String, Object>>) convertFromBytes(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(Map<String,Object> itemRaw: raw){
            toReturn.add(ItemStack.deserialize(itemRaw));
        }
        return toReturn;
    }
}