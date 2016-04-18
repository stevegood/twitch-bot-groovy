package tv.extrememoderation.twitch.chat

import org.pircbotx.hooks.ListenerAdapter
import org.pircbotx.hooks.events.ConnectEvent
import org.pircbotx.hooks.events.QuitEvent

/**
 * Created by steve on 4/17/16.
 */
class DefaultListener extends ListenerAdapter {
    @Override
    void onConnect(ConnectEvent event) throws Exception {
        TwitchBot bot = event.bot

        if (bot.useTwitchCapabilities) {
            bot.sendRaw().rawLineNow 'CAP REQ :twitch.tv/commands'
            bot.sendRaw().rawLineNow 'CAP REQ :twitch.tv/tags'
            bot.sendRaw().rawLineNow 'CAP REQ :twitch.tv/membership'
        }
    }

    @Override
    void onQuit(QuitEvent event) throws Exception {
        TwitchBot bot = event.bot
        bot.groupServer.quit()
    }
}
