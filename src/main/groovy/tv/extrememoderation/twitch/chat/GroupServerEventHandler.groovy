package tv.extrememoderation.twitch.chat

import org.pircbotx.hooks.events.ConnectEvent
import org.pircbotx.hooks.events.PrivateMessageEvent

/**
 * Created by steve on 4/17/16.
 */
interface GroupServerEventHandler {
    void onConnect(ConnectEvent event)
    void onPrivateMessage(PrivateMessageEvent event)
}