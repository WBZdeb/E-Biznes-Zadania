import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking


@Serializable
data class DiscordMessage(val content: String)

suspend fun sendMessageToDiscord(webhookUrl: String, message: String) {
    val client = HttpClient(CIO) {
        install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
            json(Json { prettyPrint = true })
        }
    }

    try {
        val payload = DiscordMessage(content = message)
        val response = client.post(webhookUrl) {
            contentType(ContentType.Application.Json)
            setBody(payload)
        }
        println("Wiadomość wysłana z odpowiedzią: ${response.status}")
    } catch (e: Exception) {
        println("Błąd podczas wysyłania wiadomości: $e")
    } finally {
        client.close()
    }
}

fun main() = runBlocking {
    val webhookUrl = "https://discord.com/api/webhooks/1370695340054810695/aGddVeMiQiZbHtQrj1vxI7X-CFOLWiMm_C8fA8h_80ITIHKpShK83IZXQQIpD_yvHJ_g"
    val message = "Witaj, Discordzie! To jest wiadomość wysłana z aplikacji Ktor!"
    sendMessageToDiscord(webhookUrl, message)
}
