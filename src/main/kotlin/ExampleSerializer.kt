import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.descriptors.*

import kotlinx.serialization.modules.*
import java.util.Date
import java.text.SimpleDateFormat

object DateAsLongSerializer : KSerializer<Date> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.LONG)
    override fun serialize(encoder: Encoder, value: Date) = encoder.encodeLong(value.time)
    override fun deserialize(decoder: Decoder): Date = Date(decoder.decodeLong())
}

@Serializable
class ProgrammingLanguage(
    val name: String,
    @Contextual
    val stableReleaseDate: Date
)

private val module = SerializersModule {
    contextual(DateAsLongSerializer)
}

val format = Json { serializersModule = module }

fun main() {
    val data = ProgrammingLanguage("Kotlin", SimpleDateFormat("yyyy-MM-ddX").parse("2016-02-15+00"))

//    println(Json.encodeToString(data))
    println(format.encodeToString(data))
}
