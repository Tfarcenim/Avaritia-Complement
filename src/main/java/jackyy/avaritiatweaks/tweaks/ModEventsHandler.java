package jackyy.avaritiatweaks.tweaks;

import jackyy.avaritiatweaks.client.ClientProxy;
import jackyy.avaritiatweaks.config.ModConfig;
import jackyy.avaritiatweaks.packet.PacketHandler;
import jackyy.avaritiatweaks.packet.PacketToggleNoClip;
import morph.avaritia.handler.AvaritiaEventHandler;
import morph.avaritia.init.ModItems;
import morph.avaritia.item.ItemArmorInfinity;
import morph.avaritia.item.tools.ItemSwordInfinity;
import morph.avaritia.util.TextUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.GetCollisionBoxesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class ModEventsHandler {

    public static final HashSet<UUID> SET = new HashSet<>();

    @SubscribeEvent
    public void onLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = event.getItemStack();
        EnumFacing facing = event.getFace();
        Vec3d vec = event.getHitVec();
        if (!ModConfig.tweaks.makeWorldBreakerGreatAgain || facing == null || world.isRemote
                || stack.isEmpty() || player.capabilities.isCreativeMode) {
            return;
        }
        if (state.getBlockHardness(world, pos) <= -1 && stack.getItem() == ModItems.infinity_pickaxe) {
            if (stack.getTagCompound() != null && stack.getTagCompound().getBoolean("hammer")) {
                ModItems.infinity_pickaxe.onBlockStartBreak(player.getHeldItemMainhand(), pos, player);
            } else {
                ItemStack drop = block.getPickBlock(state, new RayTraceResult(vec, facing), world, pos, player);
                event.getWorld().destroyBlock(pos, false);
                world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), drop));
            }
        }
    }

    @SubscribeEvent
    public void armorTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            EntityPlayer player = event.player;
            if (!player.world.isRemote && player.world.getWorldInfo().getWorldTotalTime() % 100 == 0) {
                if (isArmorValid(player, EntityEquipmentSlot.HEAD)) {
                    checkAndAddEffect(player, ModConfig.infinityArmor.infinityHelmetPotionEffects);
                }
                if (isArmorValid(player, EntityEquipmentSlot.CHEST)) {
                    checkAndAddEffect(player, ModConfig.infinityArmor.infinityChestplatePotionEffects);
                }
                if (isArmorValid(player, EntityEquipmentSlot.LEGS)) {
                    checkAndAddEffect(player, ModConfig.infinityArmor.infinityLeggingsPotionEffects);
                }
                if (isArmorValid(player, EntityEquipmentSlot.FEET)) {
                    checkAndAddEffect(player, ModConfig.infinityArmor.infinityBootsPotionEffects);
                }
            }

            if (!AvaritiaEventHandler.isInfinite(player)  && !player.isSpectator()) {
                SET.remove(player.getUniqueID());
            }
        }
    }

    @SubscribeEvent
    public void noclip(PlayerSPPushOutOfBlocksEvent event) {
        if (SET.contains(event.getEntityPlayer().getUniqueID()))event.setCanceled(true);
    }

    @SubscribeEvent
    public void noclip(GetCollisionBoxesEvent event) {
        if (event.getEntity() instanceof EntityPlayer && SET.contains(event.getEntity().getUniqueID()))
        event.getCollisionBoxesList().clear();
    }

    @SubscribeEvent
    public void noclip(RenderBlockOverlayEvent event) {
        if (SET.contains(event.getPlayer().getUniqueID()))
            event.setCanceled(true);
    }

    private static boolean isArmorValid(EntityPlayer player, EntityEquipmentSlot slot) {
        ItemStack armor = player.getItemStackFromSlot(slot);
        return armor != ItemStack.EMPTY && armor.getItem() instanceof ItemArmorInfinity
                && armor.getTagCompound() != null && armor.getTagCompound().getInteger("enhanced") == 1;
    }

    private static void checkAndAddEffect(EntityPlayer player, String[] potions) {
        for (String potion : potions) {
            Potion effect = Potion.getPotionFromResourceLocation(potion);
            if (effect != null) {
                player.addPotionEffect(new PotionEffect(effect, 400, 0, false, false));
            }
        }
    }

    public static void toggleNoClip(EntityPlayer player) {
        if (isArmorValid(player, EntityEquipmentSlot.CHEST)) {
            if (SET.contains(player.getUniqueID())) {
                SET.remove(player.getUniqueID());
                player.sendStatusMessage(new TextComponentTranslation("msg.avaritiatweaks.noclip.disabled"), true);
            } else {
                SET.add(player.getUniqueID());
                player.sendStatusMessage(new TextComponentTranslation("msg.avaritiatweaks.noclip.enabled"), true);
            }
        }
    }

    @SubscribeEvent @SideOnly(Side.CLIENT)
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (ModConfig.infinityArmor.infinityArmorNoClip) {
            if (ClientProxy.NOCLIP.isPressed()) {
                PacketHandler.INSTANCE.sendToServer(new PacketToggleNoClip());
            }
        }
    }

}
