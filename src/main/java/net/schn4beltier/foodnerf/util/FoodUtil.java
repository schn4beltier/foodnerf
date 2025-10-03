package net.schn4beltier.foodnerf.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.schn4beltier.foodnerf.Config;
import net.schn4beltier.foodnerf.compat.QualityFoodCompat;

import java.util.Set;

public class FoodUtil {

    public static FoodProperties calculateFoodProperties(FoodProperties foodProperties, ItemStack stack) {
        FoodProperties fp = foodProperties;
        if(ModList.get().isLoaded("quality_food")) {
            fp = QualityFoodCompat.getFoodProperties(stack, foodProperties);
        }
        return calculateFinalFoodProperties(fp, stack);
    }

    private static FoodProperties calculateFinalFoodProperties(FoodProperties foodProperties, ItemStack itemStack) {

        String namespace = itemStack.getItem().getCreatorModId(itemStack);
        Set<String> namespaces = Set.copyOf(Config.namespaces);
        Set<Item> blacklist = Config.item_blacklist;
        if (foodProperties != null && namespaces != null && blacklist != null) {
            if(namespaces.contains(namespace) && !blacklist.contains(itemStack.getItem())) {
                FoodProperties newFP = new FoodProperties(
                        (int) (foodProperties.nutrition() * Config.nutritionMultiplier),
                        (float) (foodProperties.saturation() * Config.saturationMultiplier),
                        foodProperties.canAlwaysEat(),
                        foodProperties.eatSeconds(),
                        foodProperties.usingConvertsTo(),
                        foodProperties.effects()
                );
                return newFP;
            }
            return foodProperties;
        }
        return null;
    }

}
