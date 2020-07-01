package jackyy.avaritiatweaks;

import jackyy.avaritiatweaks.packet.PacketHandler;
import jackyy.avaritiatweaks.tweaks.ModEventsHandler;
import jackyy.avaritiatweaks.tweaks.ModTweaks;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = AvaritiaTweaks.MODID, version = AvaritiaTweaks.VERSION, name = AvaritiaTweaks.MODNAME, dependencies = AvaritiaTweaks.DEPENDS, acceptedMinecraftVersions = AvaritiaTweaks.MCVERSION, useMetadata = true)
@Mod.EventBusSubscriber
public class AvaritiaTweaks {

    public static final String MODID = "avaritiatweaks";
    public static final String MODNAME = "Avaritia Tweaks";
    public static final String VERSION = "@VERSION@";
    public static final String MCVERSION = "[1.12,1.13)";
    public static final String DEPENDS = "required-after:avaritia;required-after:codechickenlib;after:botania;";
    public static final CreativeTabs TAB = new CreativeTabs(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModTweaks.enhancementCrystal);
        }
    };

    public static Logger logger = LogManager.getLogger(MODNAME);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        PacketHandler.registerMessages(AvaritiaTweaks.MODID);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ModEventsHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ModTweaks.initIntegrations();
    }

    @Mod.EventHandler
    public void onFingerprintViolation(FMLFingerprintViolationEvent event) {
        //logger.warn("Invalid fingerprint detected! The file " + event.getSource().getName() + " may have been modified. This will NOT be supported by the mod author!");
    }

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> e) {
        ModTweaks.initItems(e);
    }

    @SubscribeEvent
    public static void onBlockRegistry(RegistryEvent.Register<Block> e) {
        ModTweaks.initBlocks(e);
    }

    @SubscribeEvent
    public static void onRecipeRegistry(RegistryEvent.Register<IRecipe> e) {
        ModTweaks.initRecipes(e);
    }

}
