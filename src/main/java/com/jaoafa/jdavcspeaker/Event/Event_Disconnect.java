package com.jaoafa.jdavcspeaker.Event;

import com.jaoafa.jdavcspeaker.Lib.MsgFormatter;
import com.jaoafa.jdavcspeaker.Lib.MultipleServer;
import com.jaoafa.jdavcspeaker.Lib.VoiceText;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.text.MessageFormat;

/**
 * When someone leaves the VC, notify the VC text channel.
 */
public class Event_Disconnect extends ListenerAdapter {
    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        if (!MultipleServer.isTargetServer(event.getGuild())) {
            return;
        }
        if (event.getMember().getUser().isBot()) {
            return;
        }
        User user = event.getMember().getUser();
        VoiceChannel channel = event.getChannelLeft();
        if (MultipleServer.getVCChannel(event.getGuild()) == null) return;
        MultipleServer
            .getVCChannel(event.getGuild())
            .sendMessage(MessageFormat.format(":outbox_tray: `{0}` が <#{1}> から退出しました。",
                user.getName(),
                channel.getId()))
            .queue(
                message ->
                    new VoiceText().play(message,
                        MessageFormat.format("{0}が{1}から退出しました。",
                            user.getName(),
                            MsgFormatter.formatChannelName(channel)))
            );
    }
}
