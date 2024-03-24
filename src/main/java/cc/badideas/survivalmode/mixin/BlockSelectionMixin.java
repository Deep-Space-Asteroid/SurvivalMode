package cc.badideas.survivalmode.mixin;

import cc.badideas.survivalmode.SurvivalMode;
import cc.badideas.survivalmode.api.SurvivalModePlayer;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.items.Item;
import finalforeach.cosmicreach.items.ItemBlock;
import finalforeach.cosmicreach.items.ItemSlot;
import finalforeach.cosmicreach.items.ItemStack;
import finalforeach.cosmicreach.ui.UI;
import finalforeach.cosmicreach.world.BlockPosition;
import finalforeach.cosmicreach.world.BlockSelection;
import finalforeach.cosmicreach.world.World;
import finalforeach.cosmicreach.world.blocks.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockSelection.class)
public class BlockSelectionMixin {
    @Inject(method = "breakBlock", at = @At("HEAD"), cancellable = true)
    private void breakBlockInject(World world, BlockPosition blockPos, double timeSinceLastInteract, CallbackInfo info) {
        SurvivalModePlayer smPlayer = (SurvivalModePlayer) InGame.getLocalPlayer();

        if (smPlayer.isDead()) {
            info.cancel();
            return;
        }

        if (smPlayer.isCreative()) {
            return;
        }

        BlockState brokenBlock = blockPos.getBlockState();
        for (ItemSlot slot : UI.hotbar.slots) {
            if (slot.itemStack != null) {
                Item item = slot.itemStack.item;
                if (item instanceof ItemBlock && ((ItemBlock) item).blockState == brokenBlock) {
                    slot.itemStack.amount++;
                    return;
                }
            }
        }

        // We couldn't find a matching ItemStack
        boolean added = UI.hotbar.addItemStack(new ItemStack(new ItemBlock(brokenBlock), 1));
        if (!added) {
            // TODO: drop item? no item entities yet, what do?
            SurvivalMode.LOGGER.info("You fool, it's gone forever.");
        }
    }

    @Inject(method = "placeBlock", at = @At("RETURN"), cancellable = true)
    private void placeBlockInject(World world, BlockState targetBlockState, BlockPosition blockPos, double timeSinceLastInteract, CallbackInfo info) {
        SurvivalModePlayer smPlayer = (SurvivalModePlayer) InGame.getLocalPlayer();

        if (smPlayer.isDead()) {
            info.cancel();
            return;
        }

        if (smPlayer.isCreative()) {
            return;
        }

        UI.hotbar.getSelectedItemStack().amount--;
        if (UI.hotbar.getSelectedItemStack().amount <= 0) {
            UI.hotbar.getSelectedSlot().itemStack = null;
        }
    }

}
