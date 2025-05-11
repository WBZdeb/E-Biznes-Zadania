import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRouting()

    val discordToken = environment.config.property("ktor.discord.token").getString()

    DiscordBot.start(discordToken)
}
