package miyucomics.stackpatterns.mixin;

import at.petrak.hexcasting.api.casting.math.HexAngle;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.client.gui.GuiSpellcasting;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = GuiSpellcasting.class, remap = false)
public abstract class GuiSpellcastingMixin {
	@WrapOperation(method = "drawMove", at = @At(value = "INVOKE", target = "Lat/petrak/hexcasting/api/casting/math/HexPattern;tryAppendDir(Lat/petrak/hexcasting/api/casting/math/HexDir;)Z"))
	private boolean alwaysAppend(HexPattern pattern, HexDir direction, Operation<Boolean> original) {
		pattern.getAngles().add(direction.angleFrom(pattern.finalDir()));
		return true;
	}

	@WrapOperation(method = "drawMove", at = @At(value = "INVOKE", target = "Lat/petrak/hexcasting/api/casting/math/HexDir;rotatedBy(Lat/petrak/hexcasting/api/casting/math/HexAngle;)Lat/petrak/hexcasting/api/casting/math/HexDir;"))
	private HexDir neverBackwards(HexDir direction, HexAngle angle, Operation<HexDir> original) {
		if (Screen.hasShiftDown())
			return original.call(direction, angle);
		return null;
	}
}