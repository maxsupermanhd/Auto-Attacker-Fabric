package pepe.frog.autoattacker;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;

public class OptionsSliderWidget extends SliderWidget {
	public Consumer<Integer> onUpdate;

	public OptionsSliderWidget(int x, int y, int width, int height, Text text, double value, Consumer<Integer> onUpdate) {
		super(x, y, width, height, text, value);
		this.onUpdate = onUpdate;
		this.updateMessage();
	}

	@Override
	protected void updateMessage() {
		this.setMessage(Text.of(Double.toString(this.value * 3000)));
	}

	@Override
	protected void applyValue() {
		this.onUpdate.accept((int) (this.value * 3000));
	}
	
	public void setValue(double mouseX) {
		LogManager.getLogger("autoattacker-fabric").info("Set value " + mouseX);
	}
	
	public void setValueFromMouse(double mouseX) {
		LogManager.getLogger("autoattacker-fabric").info("Set value Mouse " + mouseX);
	}
	
	public void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
		LogManager.getLogger("autoattacker-fabric").info("Drag " + mouseX + " " + mouseY + " " + deltaX + " " + deltaY);
	}
	
//	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
//		return active;
//		
//	}
}
