package pepe.frog.autoattacker;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class OptionsScreen extends Screen {
	protected OptionsScreen() {
		super(Text.of("Auto Attacker options"));
	}

	@Override
	protected void init() {
		int x = this.width / 2, y = this.height / 2;
		this.addDrawableChild(new OptionsSliderWidget(
			x - 130/2, y,
			130, 20,
			Text.of("Delay"),
			AutoAttacker.C.GetDelay()/3000, value -> AutoAttacker.C.SetDelay(value)));
		this.addDrawableChild(new OptionsSliderWidget(
			x - 130/2, y+25,
			130, 20,
			Text.of("Randomness"),
			AutoAttacker.C.GetRandom()/3000, value -> AutoAttacker.C.SetRandom(value)));
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		// textRenderer.drawWithShadow(
		//     matrices,
		//     Text.of("Attack"),
		//     this.width / 2f - 135,
		//     this.height / 2f - 56,
		//     0xFFFFFF);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if(keyCode == AutoAttacker.openGUI.getDefaultKey().getCode()) {
			this.onClose();
			return true;
		}
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
