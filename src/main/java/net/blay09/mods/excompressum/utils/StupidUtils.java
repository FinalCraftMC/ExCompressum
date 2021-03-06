package net.blay09.mods.excompressum.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import javax.annotation.Nullable;

public class StupidUtils {

    /**
     * Removed from Vanilla's EnchantmentHelper for some stupid reason.
     */
    public static boolean hasSilkTouchModifier(LivingEntity entity) {
        ItemStack heldItem = entity.getHeldItemMainhand();
        return !heldItem.isEmpty() && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, heldItem) > 0;
    }

    /**
     * Wish this would just be part of ItemBlock itself.
     */
    @Nullable
    public static BlockState getStateFromItemStack(ItemStack itemStack) {
        if (itemStack.getItem() instanceof BlockItem) {
            Block block = ((BlockItem) itemStack.getItem()).getBlock();
            try {
                return block.getDefaultState();
            } catch (Exception e) {
                // In case of weirdness, don't crash! Just fallback to default.
            }
            return block.getDefaultState();
        }
        return null;
    }

    public static ItemStack getItemStackFromState(BlockState state) {
        Item item = Item.getItemFromBlock(state.getBlock());
        if (item != Items.AIR) {
            return new ItemStack(item);
        }
        return ItemStack.EMPTY;
    }

}
