package ru.fazziclay.openvkindiscord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.json.JSONArray;

import javax.security.auth.login.LoginException;

public class DiscordBot extends ListenerAdapter {
    public static JDA bot;

    public static void loadDiscordBot() throws LoginException, InterruptedException {
        bot = JDABuilder.createDefault(Config.discordToken)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_PRESENCES)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(new DiscordBot())
                .build();

        DiscordBot.bot.awaitReady();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        JSONArray channels = OpenVkInDiscord.data.optJSONArray("channels");

    }
}
