package cc.badideas.survivalmode;

import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class SurvivalMode implements ModInitializer {
    public static final String MOD_ID = "survivalmode";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Survival Mode is loaded.");
    }
}