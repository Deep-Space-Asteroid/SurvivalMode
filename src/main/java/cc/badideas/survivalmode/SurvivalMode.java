package cc.badideas.survivalmode;

import cc.badideas.survivalmode.events.BlockEventActionPressurize;
import dev.crmodders.flux.tags.Identifier;
import dev.crmodders.flux.util.BlockBuilderUtils;
import finalforeach.cosmicreach.world.blockevents.BlockEvents;
import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class SurvivalMode implements ModInitializer {
    public static final String MOD_ID = "survivalmode";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    public static final String[] BLOCKS = {
        "block_oxygen_vent"
    };

    @Override
    public void onInitialize() {
        LOGGER.info("Survival Mode is loaded.");
    }
}