package net.addcycle.globalmeals;

import net.addcycle.globalmeals.init.ModBlocks;
import net.addcycle.globalmeals.init.ModItemGroups;
import net.addcycle.globalmeals.init.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalMeals implements ModInitializer {
    public static final String MODID = "globalmeals";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        LOGGER.info("Hello fabric from GlobalMeals MOD");
        ModItems.initialize();
        ModBlocks.initialize();
        ModItemGroups.registerItemGroups();
    }
}
