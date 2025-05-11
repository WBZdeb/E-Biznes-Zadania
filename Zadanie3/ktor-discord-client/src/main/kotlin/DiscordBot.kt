import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent

val kategorie = listOf("Elektronika", "Książki", "Ubrania")

val produkty = mapOf(
    "Elektronika" to listOf("Smartfon", "Laptop", "Słuchawki"),
    "Książki" to listOf("Wiedźmin", "Lalka", "Zbrodnia i kara"),
    "Ubrania" to listOf("Koszula", "Spodnie", "Kurtka")
)

object DiscordBot {

    fun start(token: String) {
        JDABuilder.createDefault(token)
            .enableIntents(GatewayIntent.MESSAGE_CONTENT)
            .addEventListeners(MessageListener())
            .build()
    }

    private class MessageListener : ListenerAdapter() {
        override fun onMessageReceived(event: MessageReceivedEvent) {
            val message: Message = event.message
            val content = message.contentRaw
            val author = event.author

            if (author.isBot) return

            println("Wiadomość od ${author.name}: $content")

            if (!content.startsWith("b!")) return

            val args = content.removePrefix("b!").trim().split(" ")
            val command = args[0].lowercase()

            when (command) {
                "ping" -> {
                    event.channel.sendMessage("pong!").queue()
                }

                "kategorie" -> {
                    val response = kategorie.joinToString("\n") { "- $it" }
                    event.channel.sendMessage("Kategorie:\n$response").queue()
                }

                "produkty" -> {
                    if (args.size < 2) {
                        event.channel.sendMessage("Podaj kategorię. Użycie: `b!produkty [kategoria]`").queue()
                        return
                    }

                    val nazwaKategorii = args.drop(1).joinToString(" ")
                    val lista = produkty[nazwaKategorii]

                    if (lista != null) {
                        val response = lista.joinToString("\n") { "- $it" }
                        event.channel.sendMessage("Produkty w kategorii *$nazwaKategorii*:\n$response").queue()
                    } else {
                        event.channel.sendMessage("Nie znaleziono kategorii: $nazwaKategorii. Jeśli chcesz sprawdzić kategorie, użyj `b!kategorie`").queue()
                    }
                }

                else -> {
                    event.channel.sendMessage("Nieznane polecenie. Użyj `b!kategorie`, `b!produkty [kategoria]` lub `b!ping`").queue()
                }
            }
        }
    }
}