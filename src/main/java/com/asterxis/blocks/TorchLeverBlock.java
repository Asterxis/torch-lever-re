package com.asterxis.blocks;

import java.util.Map;
import java.util.Random;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TorchLeverBlock extends LeverBlock{
	
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public static final EnumProperty<AttachFace> FACE = BlockStateProperties.FACE;
	public static final DirectionProperty HORIZONTAL_FACING = HorizontalBlock.HORIZONTAL_FACING;
	protected static final VoxelShape FLOOR_SHAPE = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D);
	protected static final VoxelShape CEILING_SHAPE = Block.makeCuboidShape(6.0D, 6.0D, 6.0D, 10.0D, 16.0D, 10.0D);
	private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.makeCuboidShape(5.5D, 3.0D, 11.0D, 10.5D, 13.0D, 16.0D), Direction.SOUTH, Block.makeCuboidShape(5.5D, 3.0D, 0.0D, 10.5D, 13.0D, 5.0D), Direction.WEST, Block.makeCuboidShape(11.0D, 3.0D, 5.5D, 16.0D, 13.0D, 10.5D), Direction.EAST, Block.makeCuboidShape(0.0D, 3.0D, 5.5D, 5.0D, 13.0D, 10.5D)));

	public TorchLeverBlock(Block.Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACE, AttachFace.WALL).with(HORIZONTAL_FACING, Direction.NORTH).with(POWERED, Boolean.valueOf(false)));
	}
	/**
	 * TORCH STUFF
	 */
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
	      if((AttachFace)state.get(FACE) == AttachFace.FLOOR){
	    	  return FLOOR_SHAPE;
	      }else if((AttachFace)state.get(FACE) == AttachFace.CEILING){
	    	  return CEILING_SHAPE;
	      }else{
	    	  return func_220289_j(state);
	      }
	}
	
	public static VoxelShape func_220289_j(BlockState p_220289_0_) {
	      return SHAPES.get(p_220289_0_.get(HORIZONTAL_FACING));
	}
	/**
	 * BOTH
	 */
	@OnlyIn(Dist.CLIENT) @Override
	public void animateTick(BlockState state, World worldIn, BlockPos pos, Random rand) {
		Direction direction = state.get(HORIZONTAL_FACING);
		double d0 = (double)pos.getX() + 0.5D;
		double d1 = (double)pos.getY() + 0.7D;
		double d2 = (double)pos.getZ() + 0.5D;
		double d3 = 0.22D;
		double d4 = 0.27D;
		Direction direction1 = direction.getOpposite();
		if (state.get(POWERED)) {
			if((AttachFace)state.get(FACE) == AttachFace.FLOOR){
				d0 = (double)pos.getX() + 0.5D;
				d1 = (double)pos.getY() + 0.4D;
				d2 = (double)pos.getZ() + 0.5D;
				worldIn.addParticle(ParticleTypes.SMOKE, d0 + d4 * (double)direction.getXOffset(), d1 + d3, d2 + d4 * (double)direction.getZOffset(), 0.0D, 0.0D, 0.0D);
				worldIn.addParticle(ParticleTypes.FLAME, d0 + d4 * (double)direction.getXOffset(), d1 + d3, d2 + d4 * (double)direction.getZOffset(), 0.0D, 0.0D, 0.0D);
			}else if((AttachFace)state.get(FACE) == AttachFace.CEILING){
				d0 = (double)pos.getX() + 0.5D;
				d1 = (double)pos.getY() + 0.4D;
				d2 = (double)pos.getZ() + 0.5D;
				worldIn.addParticle(ParticleTypes.SMOKE, d0 + d4 * (double)direction.getXOffset(), d1 + d3, d2 + d4 * (double)direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
				worldIn.addParticle(ParticleTypes.FLAME, d0 + d4 * (double)direction.getXOffset(), d1 + d3, d2 + d4 * (double)direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
			}else{//WALL
				switch(state.get(HORIZONTAL_FACING)){
					case SOUTH:
						d0 = (double)pos.getX() + 0.0D;
						d1 = (double)pos.getY() + 0.7D;
						d2 = (double)pos.getZ() - 0.2D;
					break;
					case NORTH:
						d0 = (double)pos.getX() + 0.0D;
						d1 = (double)pos.getY() + 0.7D;
						d2 = (double)pos.getZ() + 0.15D;
					break;
					case WEST:
						d0 = (double)pos.getX() + 0.15D;
						d1 = (double)pos.getY() + 0.7D;
						d2 = (double)pos.getZ() + 0.0D;
					break;
					case EAST:
						d0 = (double)pos.getX() - 0.15D;
						d1 = (double)pos.getY() + 0.7D;
						d2 = (double)pos.getZ() + 0.0D;
					break;
				}
				worldIn.addParticle(ParticleTypes.SMOKE, d0 + d4 * (double)direction1.getXOffset(), d1 + d3, d2 + d4 * (double)direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
				worldIn.addParticle(ParticleTypes.FLAME, d0 + d4 * (double)direction1.getXOffset(), d1 + d3, d2 + d4 * (double)direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
			}
			if(rand.nextFloat() < 0.25F){
				addParticles(state, worldIn, pos, d0 + d4 * (double)direction.getXOffset(), d1 + d3, d2 + d4 * (double)direction.getZOffset(), 0.5F);
			}
		}else if(!state.get(POWERED)){
			if((AttachFace)state.get(FACE) == AttachFace.FLOOR){//FLOOR
				worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
				worldIn.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			}else if((AttachFace)state.get(FACE) == AttachFace.CEILING){//CEILING
				d0 = (double)pos.getX() + 0.5D;
				d1 = (double)pos.getY() + 0.4D;
				d2 = (double)pos.getZ() + 0.5D;
				worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
				worldIn.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			}else{//WALL
				d0 = (double)pos.getX() + 0.5D;
				d1 = (double)pos.getY() + 0.7D;
				d2 = (double)pos.getZ() + 0.5D;
				
				worldIn.addParticle(ParticleTypes.SMOKE, d0 + d4 * (double)direction1.getXOffset(), d1 + d3, d2 + d4 * (double)direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
				worldIn.addParticle(ParticleTypes.FLAME, d0 + d4 * (double)direction1.getXOffset(), d1 + d3, d2 + d4 * (double)direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
			}
		}
	}

	private static void addParticles(BlockState state, IWorld worldIn, BlockPos pos, double x, double y, double z, float alpha) {
		worldIn.addParticle(new RedstoneParticleData(1.0F, 0.0F, 0.0F, alpha), x, y, z, 0.0D, 0.0D, 0.0D);
	}
	
	private static void addParticles(BlockState state, IWorld worldIn, BlockPos pos, float alpha) {
		double d0 = (double)pos.getX() + 0.5D;
		double d1 = (double)pos.getY() + 0.7D;
		double d2 = (double)pos.getZ() + 0.5D;
		if((AttachFace)state.get(FACE) == AttachFace.FLOOR){
			d0 = (double)pos.getX() + 0.5D;
			d1 = (double)pos.getY() + 0.7D;
			d2 = (double)pos.getZ() + 0.5D;
		}else if((AttachFace)state.get(FACE) == AttachFace.CEILING){
			d0 = (double)pos.getX() + 0.5D;
			d1 = (double)pos.getY() + 0.7D;
			d2 = (double)pos.getZ() + 0.5D;
	    }else{
	    	d0 = (double)pos.getX() + 0.5D;
			d1 = (double)pos.getY() + 0.7D;
			d2 = (double)pos.getZ() + 0.5D;
	    }
	   worldIn.addParticle(new RedstoneParticleData(1.0F, 0.0F, 0.0F, alpha), d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}
	/**
	 * LEVER STUFF
	 */
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
	      if (worldIn.isRemote) {
	         BlockState blockstate1 = state.cycle(POWERED);
	         if (blockstate1.get(POWERED)) {
	            addParticles(blockstate1, worldIn, pos, 1.0F);
	         }

	         return ActionResultType.SUCCESS;
	      } else {
	         BlockState blockstate = this.func_226939_d_(state, worldIn, pos);
	         float f = blockstate.get(POWERED) ? 0.6F : 0.5F;
	         worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, f);
	         return ActionResultType.SUCCESS;
	      }
	}
}
