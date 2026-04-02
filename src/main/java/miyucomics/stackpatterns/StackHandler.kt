package miyucomics.stackpatterns

import at.petrak.hexcasting.api.casting.SpellList
import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.PatternIota
import at.petrak.hexcasting.api.casting.math.HexAngle
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.api.utils.lightPurple

class StackHandler(private val newCode: SpellList) : SpecialHandler {
	override fun act() = OpInsertHermes(newCode)
	override fun getName() = "hexcasting.special.stackpatterns:stack".lightPurple

	class Factory : SpecialHandler.Factory<StackHandler> {
		override fun tryMatch(pattern: HexPattern, env: CastingEnvironment): StackHandler? {
			val signature = pattern.anglesSignature()
			if (!signature.contains('s'))
				return null
			return StackHandler(SpellList.LList(createPattern(pattern).map(::PatternIota)))
		}

		companion object {
			fun createPattern(pattern: HexPattern): List<HexPattern> {
				val output = mutableListOf<HexPattern>()
				var buffer = HexPattern.fromAngles("", pattern.startDir)

				for (character in pattern.anglesSignature()) {
					if (character == 's') {
						output.add(buffer)
						buffer = HexPattern.fromAngles("", buffer.finalDir().rotatedBy(HexAngle.BACK))
						continue
					}

					buffer.angles.add(when (character) {
						'a' -> HexAngle.LEFT_BACK
						'q' -> HexAngle.LEFT
						'w' -> HexAngle.FORWARD
						'e' -> HexAngle.RIGHT
						'd' -> HexAngle.RIGHT_BACK
						else -> throw IllegalArgumentException("If this ever triggers, I will be impressed.")
					})
				}

				if (!buffer.angles.isEmpty())
					output.add(buffer)

				return output
			}
		}
	}
}