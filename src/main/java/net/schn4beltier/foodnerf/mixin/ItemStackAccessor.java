package net.schn4beltier.foodnerf.mixin;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import net.schn4beltier.foodnerf.Config;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(ItemStack.class)
public class ItemStackAccessor implements IItemStackExtension {

    @Override
    public @Nullable FoodProperties getFoodProperties(@Nullable LivingEntity entity) {
        var self = (ItemStack) (Object)this;
        String namespace = self.getItem().getCreatorModId(self);
        if(Config.namespaces.contains(namespace) && !Config.item_blacklist.contains(self.getItem())) {
            FoodProperties fp = (FoodProperties) self.get(DataComponents.FOOD);
            if(fp == null) return null;;
            FoodProperties newFP = new FoodProperties(
                    (int) (fp.nutrition() * Config.nutritionMultiplier),
                    (float) (fp.saturation() * Config.saturationMultiplier),
                    fp.canAlwaysEat(),
                    fp.eatSeconds(),
                    fp.usingConvertsTo(),
                    fp.effects()
            );
            return newFP;
        }
        return (FoodProperties) self.get(DataComponents.FOOD);
    }
}
