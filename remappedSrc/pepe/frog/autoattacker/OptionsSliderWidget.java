package pepe.frog.autoattacker;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

import java.util.function.Consumer;

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
}
