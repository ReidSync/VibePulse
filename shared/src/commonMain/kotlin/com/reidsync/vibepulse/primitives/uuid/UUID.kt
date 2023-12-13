package com.reidsync.vibepulse.primitives.uuid

/**
 * Created by Reid on 2023/12/12.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable( with = UUIDSerializer::class )
data class UUID(
	val mostSignificantBits : ULong,
	val leastSignificantBits: ULong
) {
	companion object {}
	override fun toString(): String {
		val msbDigits = mostSignificantBits.toString(16).padStart(16,'0')
		val lsbDigits = leastSignificantBits.toString(16).padStart(16,'0')
		return msbDigits.substring(0..7) + "-" +
				msbDigits.substring(8..11) + "-" +
				msbDigits.substring(12..15) + "-" +
				lsbDigits.substring(0..3) + "-" +
				lsbDigits.substring(4..15)
	}
}

fun UUID( uuidString: String ) : UUID? {
	val stringComponents = uuidString.split('-')

	if( stringComponents.size != 5 ||
		stringComponents[0].length != 8 ||
		stringComponents[1].length != 4 ||
		stringComponents[2].length != 4 ||
		stringComponents[3].length != 4 ||
		stringComponents[4].length != 12 )
	{
		return null
	}
	val numericComponents = stringComponents.map { it.toULongOrNull(16) ?: return null }
	val msb = (numericComponents[0] shl 32) or (numericComponents[1] shl 16) or (numericComponents[2])
	val lsb = (numericComponents[3] shl 48) or (numericComponents[4])
	return UUID(msb,lsb)
}

object UUIDSerializer : KSerializer<UUID> {

	override fun serialize(encoder: Encoder, value: UUID) {
		encoder.encodeString(value.toString())
	}

	override fun deserialize(decoder: Decoder): UUID {
		return UUID(decoder.decodeString()) ?: throw SerializationException("Not a valid UUID")
	}

	override val descriptor: SerialDescriptor =
		PrimitiveSerialDescriptor(
			serialName = "com.reidsync.vibepulse.primitives.uuid.UUIDSerializer",
			kind = PrimitiveKind.STRING
		)
}

expect fun UUID.Companion.randomUUID() : UUID


