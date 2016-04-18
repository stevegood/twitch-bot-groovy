package tv.extrememoderation.twitch.chat

import groovy.util.logging.Log4j
import org.pircbotx.exception.IrcException
import org.pircbotx.hooks.Listener

import static org.pircbotx.Configuration.Builder
import org.pircbotx.Configuration
import org.pircbotx.PircBotX

/**
 * Created by steve on 4/17/16.
 */
class TwitchBot extends PircBotX {

    static final String SERVER = 'irc.twitch.tv'
    static final Integer PORT = 6667

    boolean useTwitchCapabilities
    GroupServer groupServer

    TwitchBot(String username, String authtoken, String... channels) {
        this(getConfig(username, authtoken, channels))
    }

    TwitchBot(Configuration configuration) {
        super(configuration)

        groupServer = new GroupServer()
    }

    void addListener(Listener listener) {
        configuration.listenerManager.addListener listener
    }

    @Override
    void startBot() throws IOException, IrcException {
        groupServer.setup configuration.name, configuration.serverPassword, "#${configuration.name}"
        Thread thread = new Thread(groupServer)
        thread.daemon = true
        thread.start()
        super.startBot()
    }

    static Configuration getConfig(String username, String authtoken, String... channels) {
        Builder builder = new Builder()

        builder.addServer SERVER, PORT
        builder.name = username
        builder.serverPassword = authtoken
        builder.messageDelay = 0
        builder.addListener new DefaultListener()
        builder.autoReconnect = false
        builder.autoSplitMessage = false

        channels?.each {
            builder.addAutoJoinChannel it
        }

        builder.buildConfiguration()
    }
}
