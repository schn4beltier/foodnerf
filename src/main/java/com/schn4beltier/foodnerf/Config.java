package com.schn4beltier.foodnerf;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = FoodNerf.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue PERCENT_NUTRITION = BUILDER
            .comment("Percent of original hunger restored.")
            .comment("0 meaning nothing and 100 meaning original value")
            .comment("Default 50")
            .defineInRange("percentNutrition", 50, 0, 100);

    private static final ForgeConfigSpec.IntValue PERCENT_SATURATION = BUILDER
            .comment("Percent of original saturation restored.")
            .comment("0 meaning nothing and 100 meaning original value")
            .comment("Default 50")
            .defineInRange("percentSaturation", 50, 0, 100);

    // a list of strings that are treated as resource locations for items
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> NAMESPACE_STRINGS = BUILDER
            .comment("A list of namespaces to nerf food items from")
            .comment("Example: '[\"minecraft\", \"farmersdelight\", \"pamshc2\"]'")
            .defineListAllowEmpty("namespaces", List.of("minecraft"), obj -> obj instanceof String);


    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int percentNutrition;
    public static int percentSaturation;
    public static Set<String> namespaces;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        percentNutrition = PERCENT_NUTRITION.get();
        percentSaturation = PERCENT_SATURATION.get();

        namespaces = new HashSet<>(NAMESPACE_STRINGS.get());
    }
}
