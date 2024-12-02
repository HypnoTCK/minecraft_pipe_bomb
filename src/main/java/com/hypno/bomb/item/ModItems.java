package com.hypno.bomb.item;

import com.hypno.bomb.Bomb;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item PIPE_BOMB = registerItem("pipe_bomb", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(Bomb.MOD_ID, name), item);
    }

    public static void registerModItems(){
        Bomb.LOGGER.info("Registering mod Item for " + Bomb.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(PIPE_BOMB);
        });
    }
}
