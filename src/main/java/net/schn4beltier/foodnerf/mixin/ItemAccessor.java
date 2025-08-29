package net.schn4beltier.foodnerf.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItem;
import net.schn4beltier.foodnerf.Config;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemAccessor implements IForgeItem {

    @Final
    @Shadow
    private FoodProperties foodProperties;

    @Final
    @Shadow
    protected boolean canRepair;



    @Override
    public @Nullable FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        var self = (Item) (Object) this;
        FoodProperties fp =  foodProperties;
        String namespace = self.getCreatorModId(self.getDefaultInstance());
        if (fp != null) {
            if(Config.namespaces.contains(namespace) && !Config.blacklist.contains(self)) {
                FoodProperties.Builder newFP = new FoodProperties.Builder();

                newFP.nutrition((int) (fp.getNutrition() * Config.nutritionMultiplier));
                newFP.saturationMod((float) (fp.getSaturationModifier() * Config.saturationMultiplier));

                for (Pair<MobEffectInstance, Float> instancePair : fp.getEffects()) {
                    newFP.effect(instancePair.getFirst(), instancePair.getSecond());
                }
                if(fp.isFastFood()) newFP.fast();
                if(fp.isMeat()) newFP.meat();
                if(fp.canAlwaysEat()) newFP.alwaysEat();

                FoodProperties newFP2 = newFP.build();
                return newFP2;
            }
            return fp;
        }
        return null;
    }

    @Override
    public boolean isRepairable(ItemStack itemStack) {
        return canRepair;
    }
}
