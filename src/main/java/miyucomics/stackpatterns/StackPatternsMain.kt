package miyucomics.stackpatterns

import at.petrak.hexcasting.xplat.IXplatAbstractions
import net.fabricmc.api.ModInitializer
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

class StackPatternsMain : ModInitializer {
	override fun onInitialize() {
		Registry.register(IXplatAbstractions.INSTANCE.specialHandlerRegistry, Identifier("stackpatterns", "stack"), StackHandler.Factory())
	}
}