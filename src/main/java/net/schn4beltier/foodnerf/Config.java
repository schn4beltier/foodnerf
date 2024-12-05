package net.schn4beltier.foodnerf;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = Foodnerf.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.DoubleValue NUTRITION_MULTIPLIER = BUILDER
                                .comment("Nutrition Multiplier")
                                .comment("This value will be multiplied with the nutrition value of all selected food items.")
                                .comment("Default 0.5")
                                .defineInRange("nutritionMultiplier", 0.5, 0.0, Double.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue SATURATION_MULTIPLIER = BUILDER
            .comment("Saturation Multiplier")
            .comment("This value will be multiplied with the saturation value of all selected food items.")
            .comment("Default 0.5")
            .defineInRange("saturationMultiplier", 0.5, 0.0, Double.MAX_VALUE);


    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> NAMESPACES = BUILDER
            .comment("List of all namespaces to be affected by the mod.")
            .comment("Example: [\"minecraft\", \"foodnerf\"]")
            .comment("Default [\"minecraft\"]")
            .defineListAllowEmpty("namespaces", List.of("minecraft"), obj -> obj instanceof String);

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> BLACKLIST = BUILDER
            .comment("List of all items to not be affected by the mod.")
            .comment("Example: [\"minecraft:golden_apple\"")
            .defineListAllowEmpty("blacklist", List.of(), obj -> obj instanceof String);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static double nutritionMultiplier;
    public static double saturationMultiplier;
    public static Set<String> namespaces;
    public static Set<String> blacklist;



    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        nutritionMultiplier = NUTRITION_MULTIPLIER.get();
        saturationMultiplier = SATURATION_MULTIPLIER.get();
        namespaces = new HashSet<>(NAMESPACES.get());
        blacklist = new HashSet<>(BLACKLIST.get());
    }
}
