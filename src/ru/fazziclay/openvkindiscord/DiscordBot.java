package ru.fazziclay.openvkindiscord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

public class DiscordBot extends ListenerAdapter {
    public static JDA bot;

    public static void loadDiscordBot() throws LoginException, InterruptedException {
        Debugger debugger = new Debugger("DiscordBot", "loadDiscordBot");
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
        Debugger debugger = new Debugger("DiscordBot", "onMessageReceived", event.toString());
        debugger.log("message(raw)="+event.getMessage().getContentRaw());

        if (event.getMessage().getAuthor().isBot() || !event.getChannelType().isGuild()) {
            debugger.log("author is bot. returned");
            return;
        }

        Channel channel = Channel.getChannelByDiscord(event.getChannel().getId());
        if (channel == null) {
            debugger.error("Channel is not equal in vk!");
            event.getChannel().sendMessage("Channel not equal in vk!").queue();
            return;
        }

        channel.sendToVk(event.getMessage().getContentRaw());
    }
}
