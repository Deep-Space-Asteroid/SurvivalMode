package cc.badideas.survivalmode.events;

import cc.badideas.survivalmode.SurvivalMode;
import com.badlogic.gdx.math.Vector3;
import finalforeach.cosmicreach.world.BlockPosition;
import finalforeach.cosmicreach.world.World;
import finalforeach.cosmicreach.world.blockevents.BlockEventTrigger;
import finalforeach.cosmicreach.world.blockevents.IBlockEventAction;
import finalforeach.cosmicreach.world.blocks.BlockState;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BlockEventActionPressurize implements IBlockEventAction {
    public String getActionId() {
        return "survivalmode:pressurize";
    }

    public void act(BlockState blockState, BlockEventTrigger blockEventTrigger, World world, Map<String, Object> map) {
        this.act(blockState, blockEventTrigger, world, (BlockPosition) map.get("blockPos"));
    }

    private boolean isAir(BlockState blockState) {
        return blockState.getBlock().getStringId().equals("base:air");
    }

    public void act(BlockState blockState, BlockEventTrigger blockEventTrigger, World world, BlockPosition blockPos) {
        int volume = Math.round((float) blockEventTrigger.getParams().get("volume"));

        // BFS
        ArrayList<Vector3> volumeToPressurize = new ArrayList<>();
        ArrayList<Vector3> seen = new ArrayList<>();
        Queue<Vector3> queue = new LinkedList<>();

        queue.add(new Vector3(blockPos.getGlobalX(), blockPos.getGlobalY() + 1, blockPos.getGlobalZ()));

        while (volumeToPressurize.size() < volume) {
            Vector3 current = queue.poll();
            if (current == null) {
                break;
            }

            BlockState currentBlockState = world.getBlockState(current);
            if (isAir(currentBlockState)) {
                volumeToPressurize.add(current);
                Vector3 up = new Vector3(current.x, current.y + 1, current.z);
                Vector3 down = new Vector3(current.x, current.y - 1, current.z);
                Vector3 left = new Vector3(current.x - 1, current.y, current.z);
                Vector3 right = new Vector3(current.x + 1, current.y, current.z);
                Vector3 front = new Vector3(current.x, current.y, current.z - 1);
                Vector3 back = new Vector3(current.x, current.y, current.z + 1);

                if (!seen.contains(up)) {
                    queue.add(up);
                    seen.add(up);
                }

                if (!seen.contains(down)) {
                    queue.add(down);
                    seen.add(down);
                }

                if (!seen.contains(left)) {
                    queue.add(left);
                    seen.add(left);
                }

                if (!seen.contains(right)) {
                    queue.add(right);
                    seen.add(right);
                }

                if (!seen.contains(front)) {
                    queue.add(front);
                    seen.add(front);
                }

                if (!seen.contains(back)) {
                    queue.add(back);
                    seen.add(back);
                }
            }
        }

        SurvivalMode.LOGGER.info("Pressurizing " + volumeToPressurize.size() + " blocks.");
    }
}
