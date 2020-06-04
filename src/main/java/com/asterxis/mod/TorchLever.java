package com.asterxis.mod;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.asterxis.blocks.TorchLeverBlock;

@Mod("torch-lever")
public class TorchLever
{
	public static Block torch_lever_block = new TorchLeverBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.0F).lightValue(14).sound(SoundType.WOOD)).setRegistryName("torch-lever", "torch_lever");
    public static final Logger LOGGER = LogManager.getLogger();

    public TorchLever() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    	RenderTypeLookup.setRenderLayer(torch_lever_block, RenderType.getCutout());
        LOGGER.info("Fixed torch_lever rendering!");
    }
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> e) {
    		e.getRegistry().register(torch_lever_block);
    		ForgeRegistries.ITEMS.register(new BlockItem(torch_lever_block, new Item.Properties().group(ItemGroup.REDSTONE)) {
    			@Override
    			public String getCreatorModId(ItemStack itemStack) {
    				return "torch-lever";
    			}
    		}.setRegistryName(torch_lever_block.getRegistryName()));
        }
    }
}