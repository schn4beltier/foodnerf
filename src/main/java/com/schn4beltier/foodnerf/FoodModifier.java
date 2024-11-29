package com.schn4beltier.foodnerf;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

import com.schn4beltier.foodnerf.Config;

@Mod.EventBusSubscriber(modid = "foodnerf", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FoodModifier {

    @SubscribeEvent
    public static void onFoodConsumed(LivingEntityUseItemEvent.Finish event) {
        ItemStack stack = event.getItem();
        Item item = stack.getItem();
        LivingEntity entity = event.getEntity();
        FoodNerf.modLog("Food Consumed", "debug");

        Set<String> namespaces = Config.namespaces;
        float nutritionMultiplier = (float) Config.percentNutrition / 100;
        float saturationMultiplier = (float) Config.percentSaturation / 100;

        if (entity instanceof Player player) {
            ResourceLocation itemKey = ForgeRegistries.ITEMS.getKey(item);
            if (item.isEdible() && itemKey != null && namespaces.contains(itemKey.getNamespace())) {
                int foodLevel = player.getFoodData().getFoodLevel();
                float saturationLevel = player.getFoodData().getSaturationLevel();
                FoodNerf.modLog("Nerfed Food Item Consumed. Nutrition Value Nerfed", "debug");
                int nutrition = item.getFoodProperties().getNutrition();
                float saturation = item.getFoodProperties().getSaturationModifier();
                player.getFoodData().setFoodLevel((int) ((foodLevel-nutrition) + nutrition * nutritionMultiplier));
                player.getFoodData().setSaturation((int) ((saturationLevel - saturation) + saturation * saturationMultiplier));
            }
            if (item.isEdible() && itemKey != null && "foodnerf".equals(itemKey.getNamespace())) {
                FoodNerf.modLog("Anti Food Consumed", "debug");
                player.getFoodData().setFoodLevel(Math.max(0, player.getFoodData().getFoodLevel() - 4));
            }
        }
    }
}

