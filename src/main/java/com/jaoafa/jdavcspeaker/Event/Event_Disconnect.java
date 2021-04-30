package com.jaoafa.jdavcspeaker.Event;

import com.jaoafa.jdavcspeaker.Lib.VoiceText;
import com.jaoafa.jdavcspeaker.StaticData;
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
        if (event.getMember().getUser().isBot()) {
            return;
        }
        User user = event.getMember().getUser();
        VoiceChannel channel = event.getChannelLeft();
        if (StaticData.textChannel == null) return;
        StaticData.textChannel.sendMessage(MessageFormat.format(":outbox_tray: `{0}`が`{1}`から退出しました。", user.getName(), channel.getName())).queue(
            message -> VoiceText.speak(message, MessageFormat.format("{0}が{1}から退出しました。", user.getName(), channel.getName()))
        );
    }
}
