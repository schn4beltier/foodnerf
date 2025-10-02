package net.schn4beltier.foodnerf;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@EventBusSubscriber
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.DoubleValue NUTRITION_MULTIPLIER = BUILDER.comment("Multiplier for the nutrition value").defineInRange("nutrition_multiplier", 0.5, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.DoubleValue SATURATION_MULTIPLIER = BUILDER.comment("Multiplier for the saturation value").defineInRange("saturation_multiplier", 0.5, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.ConfigValue<List<? extends  String>> NAMESPACES = BUILDER.comment("Namespaces to be included in the mod").defineListAllowEmpty("namespaces", List.of("minecraft"), Config::validateNamespace);
    private static final ModConfigSpec.ConfigValue<List<? extends  String>> ITEM_BLACKLIST = BUILDER.comment("Items that should not be affected by this mod").defineListAllowEmpty("item_blacklist", List.of("minecraft:golden_apple"), Config::validateItemName);

    static final ModConfigSpec SPEC = BUILDER.build();



    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    private static boolean validateNamespace(final Object obj) {
        return true;
    }

    public static List<String> namespaces;
    public static double nutritionMultiplier;
    public static double saturationMultiplier;
    public static Set<Item> item_blacklist;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {

        namespaces = new ArrayList<>(NAMESPACES.get());
        nutritionMultiplier = NUTRITION_MULTIPLIER.get();
        saturationMultiplier = SATURATION_MULTIPLIER.get();
        item_blacklist = ITEM_BLACKLIST.get().stream().map(itemName -> BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemName))).collect(Collectors.toSet());
    }
}
