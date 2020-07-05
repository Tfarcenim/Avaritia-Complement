package jackyy.avaritiatweaks.client;

import jackyy.avaritiatweaks.config.ModConfig;
import jackyy.avaritiatweaks.tweaks.ModEventsHandler;
import jackyy.avaritiatweaks.tweaks.ModTweaks;
import morph.avaritia.init.ModItems;
import morph.avaritia.item.tools.ItemSwordInfinity;
import morph.avaritia.util.TextUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;

import java.util.List;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy {

    public static final KeyBinding NOCLIP = new KeyBinding(ModConfig.infinityArmor.infinityArmorNoClip ? "key.infinity_armor.noclip" : "key.infinity_armor.noclip.disabled", new IKeyConflictContext() {
        @Override
        public boolean isActive() {
            return KeyConflictContext.IN_GAME.isActive();
        }
        @Override
        public boolean conflicts(IKeyConflictContext other) {
            return other == this || KeyConflictContext.IN_GAME.isActive();
        }
    }, Keyboard.KEY_APOSTROPHE, "key.categories.avaritiatweaks");

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent e) {
        ModTweaks.initModels();
        ModTweaks.initIntegrationsClient();
        ClientRegistry.registerKeyBinding(NOCLIP);
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        List<String> tooltip = event.getToolTip();
        if (item instanceof ItemSwordInfinity && ModConfig.tweaks.fixInfinitySwordTooltip) {
            for (int x = 0; x < tooltip.size(); x++) {
                if (tooltip.get(x).contains(I18n.format("attribute.name.generic.attackDamage"))) {
                    tooltip.set(x, " " + TextUtils.makeFabulous(I18n.format("tip.infinity")) + " "
                            + TextFormatting.GRAY + I18n.format("attribute.name.generic.attackDamage"));
                    return;
                }
            }
        }

        if (item == ModItems.infinity_chestplate && ModConfig.infinityArmor.infinityArmorNoClip
                && stack.getTagCompound() != null && stack.getTagCompound().getInteger("enhanced") == 1) {
            for (int x = 0; x < tooltip.size(); x++) {
                if (tooltip.get(x).contains(I18n.format("attribute.name.generic.armorToughness")) && event.getEntityPlayer() != null) {
                    tooltip.add(x - 1, ModEventsHandler.SET.contains(event.getEntityPlayer().getUniqueID())
                            ? I18n.format("tooltips.avaritiatweaks.noclip.enabled")
                            : I18n.format("tooltips.avaritiatweaks.noclip.disabled"));
                    tooltip.add(x, " ");
                    return;
                }
            }
        }
    }
}
