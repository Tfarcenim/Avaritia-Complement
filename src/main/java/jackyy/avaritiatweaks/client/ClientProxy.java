package jackyy.avaritiatweaks.client;

import jackyy.avaritiatweaks.config.ModConfig;
import jackyy.avaritiatweaks.tweaks.ModTweaks;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;

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
}
