package net.schn4beltier.foodnerffabric.mixin;

import blue.endless.jankson.annotation.Nullable;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.Set;

import static net.schn4beltier.foodnerffabric.Foodnerffabric.CONFIG;

@Mixin(Item.class)
public class ItemAccessor {

    @Final
    @Shadow
    private @Nullable FoodComponent foodComponent;

    @Inject(method = "getFoodComponent", at = @At("HEAD"), cancellable = true)
    public void getFoodComponent(CallbackInfoReturnable<FoodComponent> cir) {
        var self = (Item)(Object)this;
        String namespace = Registries.ITEM.getId(self).getNamespace();
        float nutritionMultiplier = CONFIG.nutritionMultiplier();
        float saturationMultiplier = CONFIG.saturationMultiplier();
        Set<String> namespaces = CONFIG.namespaces();
        Set<String> blacklistStrings = CONFIG.blacklist();
        Set<Item> blacklist = itemFromString(blacklistStrings);
        FoodComponent fc = foodComponent;
        if(namespaces != null && blacklist != null && fc != null) {
            if(namespaces.contains(namespace)) {

                FoodComponent.Builder fcBuilder = new FoodComponent.Builder();
                fcBuilder.hunger((int)(fc.getHunger() * nutritionMultiplier));
                fcBuilder.saturationModifier(fc.getSaturationModifier() * saturationMultiplier);
                if(fc.isAlwaysEdible())fcBuilder.alwaysEdible();
                if(fc.isMeat())fcBuilder.meat();
                if(fc.isSnack())fcBuilder.snack();
                for(Pair<StatusEffectInstance, Float> pair : fc.getStatusEffects()) {
                    fcBuilder.statusEffect(pair.getFirst(), pair.getSecond());
                }
                fc = fcBuilder.build();
            }
        }
        cir.setReturnValue(fc);
    }

    @Unique
    private Set<Item> itemFromString(Set<String> strings) {
        Set<Item> items = new HashSet<>();
        for(String s : strings) {
            Identifier id = new Identifier(s);
            items.add(Registries.ITEM.get(id));
        }
        return items;
    }
}
