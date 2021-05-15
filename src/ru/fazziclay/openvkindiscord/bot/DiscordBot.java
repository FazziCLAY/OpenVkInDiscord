package ru.fazziclay.openvkindiscord.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import ru.fazziclay.openvkindiscord.Config;
import ru.fazziclay.openvkindiscord.console.Debugger;

import javax.security.auth.login.LoginException;

public class DiscordBot extends ListenerAdapter {
    public static JDA discordBot;

    public static void loadDiscordBot() throws LoginException, InterruptedException {
        Debugger debugger = new Debugger("DiscordBot", "loadDiscordBot");
        discordBot = JDABuilder.createDefault(Config.discordToken)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_PRESENCES)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(new DiscordBot())
                .build();

        discordBot.awaitReady();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Debugger debugger = new Debugger("DiscordBot", "onMessageReceived", event.toString());
    }
}
