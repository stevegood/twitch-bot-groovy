package tv.extrememoderation.twitch.chat

import org.pircbotx.Configuration
import org.pircbotx.PircBotX
import org.pircbotx.hooks.ListenerAdapter
import org.pircbotx.hooks.events.ConnectEvent
import org.pircbotx.hooks.events.PrivateMessageEvent

/**
 * Created by steve on 4/17/16.
 */
class GroupServer extends ListenerAdapter implements Runnable {
    static final String IP = 'irc.twitch.tv'
    static final Integer PORT = 6667

    PircBotX bot
    String channel
    GroupServerEventHandler handler

    protected void setup(String username, String authtoken, String channel) {
        Configuration config = new Configuration.Builder()
            .setName(username)
            .addServer(IP, PORT)
            .setServerPassword(authtoken)
            .setMessageDelay(0)
            .addAutoJoinChannel(channel)
            .addListener(this)
            .setAutoSplitMessage(false)
            .buildConfiguration()

        this.channel = channel

        bot = new PircBotX(config)
    }

    @Override
    void onConnect(ConnectEvent event) throws Exception {
        bot.sendRaw().rawLineNow 'CAP REQ :twitch.tv/commands'

        handler?.onConnect event
    }

    @Override
    void onPrivateMessage(PrivateMessageEvent event) throws Exception {
        handler?.onPrivateMessage event
    }

    void startBot() {
        try {
            bot.startBot()
        } catch (e) {
            e.printStackTrace()
        }
    }

    void sendWhisper(String user, String message) {
        bot.send().message channel, "/w $user $message"
    }

    void run() {
        startBot()
    }

    void quit() {
        bot.send().quitServer()
    }

    boolean isConnected() {
        bot.isConnected()
    }
}
