package io.github.hnosmium0001.hcdemo;

import io.github.hnosmium0001.hcdemo.block.ExampleBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DemoMod.MODID)
public class DemoMod {

    public static final String MODID = "hcdemo";

    public DemoMod() {
        exampleBlock = new ExampleBlock(Block.Properties.create(Material.ROCK));
        exampleBlock.setRegistryName(new ResourceLocation(MODID, "example_block"));

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addGenericListener(Block.class, this::onBlockRegister);
        eventBus.addGenericListener(Item.class, this::onItemRegister);
    }

    public final ExampleBlock exampleBlock;

    private void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(exampleBlock);
    }

    private void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new BlockItem(exampleBlock, new Item.Properties()).setRegistryName(exampleBlock.getRegistryName()));
    }
}
