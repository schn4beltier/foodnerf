package net.schn4beltier.foodnerf.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
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
        Set<String> namespaces = Config.namespaces;
        Set<Item> blacklist = Config.blacklist;
        if (foodProperties != null && namespaces != null && blacklist != null) {
            if(namespaces.contains(namespace) && !blacklist.contains(itemStack.getItem())) {
                FoodProperties.Builder newFP = new FoodProperties.Builder();

                newFP.nutrition((int) (foodProperties.getNutrition() * Config.nutritionMultiplier));
                newFP.saturationMod((float) (foodProperties.getSaturationModifier() * Config.saturationMultiplier));

                for (Pair<MobEffectInstance, Float> instancePair : foodProperties.getEffects()) {
                    newFP.effect(instancePair.getFirst(), instancePair.getSecond());
                }
                if(foodProperties.isFastFood()) newFP.fast();
                if(foodProperties.isMeat()) newFP.meat();
                if(foodProperties.canAlwaysEat()) newFP.alwaysEat();

                FoodProperties newFP2 = newFP.build();
                return newFP2;
            }
            return foodProperties;
        }
        return null;
    }
}
