package mtr.block;

import mtr.Items;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class BlockAPGDoor extends BlockPSDAPGDoorBase {

	public static final BooleanProperty GLASS = BooleanProperty.of("glass");
	private final boolean hsr;
	private final boolean toLeft;

	public BlockAPGDoor(boolean hsr, boolean toLeft) {
		super();
		this.hsr = hsr;
		this.toLeft = toLeft;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
		final BlockState superState = super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
		if (superState.getBlock() == Blocks.AIR) {
			return superState;
		}

		final Direction facing = IBlock.getStatePropertySafe(superState, FACING);
		final EnumSide side = IBlock.getStatePropertySafe(superState, SIDE);

		if (side == EnumSide.LEFT && facing.rotateYCounterclockwise() == direction || side == EnumSide.RIGHT && facing.rotateYClockwise() == direction) {
			return superState.with(GLASS, newState.getBlock() instanceof BlockAPGGlass || newState.getBlock() instanceof BlockAPGGlassEnd);
		} else {
			return superState;
		}
	}

	@Override
	public Item asItem() {
		return hsr ? (toLeft ? Items.APG_DOOR_HSR_LEFT : Items.APG_DOOR_HSR_LEFT) : Items.APG_DOOR;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(END, FACING, GLASS, HALF, OPEN, SIDE);
	}
}
