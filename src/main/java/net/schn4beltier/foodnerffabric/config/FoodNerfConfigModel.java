package net.schn4beltier.foodnerffabric.config;

import blue.endless.jankson.Comment;
import io.wispforest.owo.config.annotation.Config;

import java.util.Set;
@Config(name="foodnerfconfig", wrapperName = "FoodNerfConfig")
public class FoodNerfConfigModel {
    @Comment("Nutrition Multiplier" +
            "This value will be multiplied with the nutrition value of all selected food items." +
            "Default 0.5")
    public float nutritionMultiplier = (float) 0.5;
    @Comment("Saturation Multiplier" +
            "This value will be multiplied with the saturation value of all selected food items." +
            "Default 0.5")
    public float saturationMultiplier = (float) 0.5;
    @Comment("List of all namespaces to be affected by the mod." +
            "Example: [\"minecraft\", \"foodnerf\"]" +
            "Default [\"minecraft\"]")
    public Set<String> namespaces = Set.of("minecraft");
    @Comment("List of all items to not be affected by the mod." +
            "Example: [\"minecraft:golden_apple\"" +
            "Default [\"minecraft:golden_apple\"")
    public Set<String> blacklist = Set.of("minecraft:golden_apple");
}


