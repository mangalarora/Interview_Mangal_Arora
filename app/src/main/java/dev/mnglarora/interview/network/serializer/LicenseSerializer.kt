package dev.mnglarora.interview.network.serializer

import dev.mnglarora.interview.model.License
import kotlinx.serialization.KSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

object LicenseSerializer : KSerializer<License> {
    override fun deserialize(decoder: Decoder): License =
        Json { ignoreUnknownKeys = true;isLenient = true }.decodeFromString(decoder.decodeString())

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "type",
        kind = PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: License) =
        encoder.encodeString(value.name)
}