package cc.badideas.survivalmode;

import com.github.repletsin5.lunar.api.blocks.BlockEvents;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.world.BlockPosition;
import finalforeach.cosmicreach.world.World;
import finalforeach.cosmicreach.world.blocks.BlockState;
import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class SurvivalMode implements ModInitializer {
    public static final String MOD_ID = "survivalmode";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
//        BlockEvents.BEFORE_BLOCK_BREAK.register((world, blockPosition, v) -> {
//            BlockState blockBroken = InGame.world.getBlockState(blockPosition.getGlobalX(), blockPosition.getGlobalY(), blockPosition.getGlobalZ());
//            LOGGER.info(blockBroken.getBlockId());
//        });
    }
}