package miyucomics.stackpatterns

import at.petrak.hexcasting.api.casting.SpellList
import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.PatternIota
import at.petrak.hexcasting.api.casting.math.HexAngle
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.api.utils.lightPurple

class StackHandler(private val newCode: SpellList) : SpecialHandler {
	override fun act() = OpInsertHermes(newCode)
	override fun getName() = "hexcasting.special.stackpatterns:stack".lightPurple

	class Factory : SpecialHandler.Factory<StackHandler> {
		override fun tryMatch(pattern: HexPattern, env: CastingEnvironment): StackHandler? {
			val sig = pattern.anglesSignature()
			if (!sig.contains('s'))
				return null
			val new = sig.split('s').filterNot(String::isEmpty).map { createPattern(it, HexDir.NORTH_WEST) }.map(::PatternIota)
			return StackHandler(SpellList.LList(new))
		}

		companion object {
			fun createPattern(signature: String, startDir: HexDir): HexPattern {
				val out = HexPattern(startDir)
				var compass = startDir

				for ((idx, c) in signature.withIndex()) {
					val angle = when (c) {
						'w' -> HexAngle.FORWARD
						'e' -> HexAngle.RIGHT
						'd' -> HexAngle.RIGHT_BACK
						's' -> HexAngle.BACK
						'a' -> HexAngle.LEFT_BACK
						'q' -> HexAngle.LEFT
						else -> throw IllegalArgumentException("Cannot match $c at idx $idx to a direction")
					}
					compass *= angle
					out.angles.add(compass.angleFrom(out.finalDir()))
				}
				return out
			}
		}
	}
}