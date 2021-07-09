package pepe.frog.autoattacker;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import java.lang.Math;

//@Environment(EnvType.CLIENT)
public class AutoAttacker implements ModInitializer {
	public static final String MOD_ID = "autoattacker-fabric";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	public static final KeyBinding openGUI = KeyBindingHelper.registerKeyBinding(new KeyBinding("Open GUI", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_O, "Auto Attacker"));
	private static final KeyBinding toggleAttack = KeyBindingHelper.registerKeyBinding(new KeyBinding("Toggle attack", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_I, "Auto Attacker"));
	private boolean isActive = false;
	public static Configuration C = new Configuration(800, 250);
	public static long last_attack = System.currentTimeMillis();
//	private static long cooldown_done = System.currentTimeMillis();
	private static long last_rng = 250;

	@Override
	public void onInitialize() {
		LOGGER.info("Auto Attacker Starting up");
		ClientTickEvents.END_CLIENT_TICK.register(this::clientTickEvent);
		LOGGER.info("Auto Attacker Client tick registered");
		HudRenderCallback.EVENT.register(this::RenderGameOverlayEvent);
		LOGGER.info("Auto Attacker Initialised");
	}

	private void RenderGameOverlayEvent(MatrixStack matrixStack, float delta) {
		if(this.isActive) {
			MinecraftClient.getInstance().textRenderer.drawWithShadow(
				matrixStack,
				"AUTO ATTACK ACTIVE delay:" + AutoAttacker.C.GetDelay() + " rng:"+AutoAttacker.C.GetRandom(),
				10,	10,
				0xffffff);
		}
	}

	private void clientTickEvent(MinecraftClient mc) {
		if(mc.player == null || mc.world == null) { // || mc.currentScreen != null) {
			return;
		}
		if(this.isActive) {
			if(mc.player.getAttackCooldownProgress(0) == 1.0F &&
			   last_attack+AutoAttacker.C.GetDelay()+AutoAttacker.last_rng < System.currentTimeMillis()) {
				this.attemptMobAttack(mc);
				last_attack = System.currentTimeMillis();
				AutoAttacker.last_rng = (long)(C.GetRandom()*Math.random());
			}
		}
		this.keyInputEvent(mc);
	}

	private void attemptMobAttack(MinecraftClient mc) {
		HitResult rayTrace = mc.crosshairTarget;
		if(rayTrace instanceof EntityHitResult && mc.interactionManager != null) {
			mc.interactionManager.attackEntity(mc.player, ((EntityHitResult) rayTrace).getEntity());
		}
	}

	private void keyInputEvent(MinecraftClient mc) {
		assert mc.player != null;
		while(AutoAttacker.toggleAttack.wasPressed()) {
			this.isActive = !this.isActive;
			mc.player.sendMessage(Text.of(this.isActive ? "Auto attack enabled" : "Auto attack disabled"), true);
		}
		while (AutoAttacker.openGUI.wasPressed()) {
			mc.openScreen(new OptionsScreen());
		}
	}
}
