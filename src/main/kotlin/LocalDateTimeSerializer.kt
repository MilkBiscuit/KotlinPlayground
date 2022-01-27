import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object LocalDateTimeSerializer: KSerializer<LocalDateTime> {

    override fun deserialize(decoder: Decoder): LocalDateTime = LocalDateTime.parse(decoder.decodeString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
    }

}

@Serializable
class SomethingWithLocalDateTime(
    val name: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val dateTime: LocalDateTime
)

fun main() {
    val data1 = SomethingWithLocalDateTime("Valentine", LocalDateTime.of(2022, 2, 14, 0, 0))
    val data2 = SomethingWithLocalDateTime("Now", LocalDateTime.now())
    val jsonString = Json.encodeToString(data1)
    println(jsonString)
    println(Json.encodeToString(data2))

    val parsedData = Json.decodeFromString<SomethingWithLocalDateTime>(jsonString)
    println(parsedData.dateTime)
}
