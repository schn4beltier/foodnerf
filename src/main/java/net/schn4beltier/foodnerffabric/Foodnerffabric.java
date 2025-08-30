package net.schn4beltier.foodnerffabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.schn4beltier.foodnerffabric.config.FoodNerfConfig;

import java.util.HashSet;
import java.util.Set;

public class Foodnerffabric implements ModInitializer {

    public static final FoodNerfConfig CONFIG = FoodNerfConfig.createAndLoad();

    @Override
    public void onInitialize() {
        Log.info(LogCategory.LOG, "Initializing FoodNerf");
        Set<String> strings= CONFIG.blacklist();
        Set<Item> items = new HashSet<>();
        for(String s : strings) {
            Identifier id = new Identifier(s);
            items.add(Registries.ITEM.get(id));

            Log.info(LogCategory.LOG, "Added item: " + s);
        }
    }
}
