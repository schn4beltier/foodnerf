package net.schn4beltier.foodnerf.compat;

import de.cadentem.quality_food.util.FoodUtils;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;

public class QualityFoodCompat {

    public static FoodProperties getFoodProperties(ItemStack stack, FoodProperties properties) {
        return FoodUtils.handleFoodProperties(stack, properties);
    }
}
