package net.addcycle.globalmeals.items;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class ItemKnife extends SwordItem {

    public ItemKnife(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    // FIXME : find a way to right-click a block and prioritize the item right-click actions rather than the block itself...
}