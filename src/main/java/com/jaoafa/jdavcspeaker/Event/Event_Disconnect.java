package com.jaoafa.jdavcspeaker.Event;

import com.jaoafa.jdavcspeaker.Lib.VoiceText;
import com.jaoafa.jdavcspeaker.StaticData;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Event_Disconnect extends ListenerAdapter {
    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        if (event.getMember().getUser().isBot()) {
            return;
        }
        event.getJDA().getTextChannelById(StaticData.vcTextChannel).sendMessage(":outbox_tray: `" + event.getMember().getUser().getName() + "`が`" + event.getChannelLeft().getName() + "`から退出しました。").queue();
        VoiceText.speak(event.getJDA().getTextChannelById(StaticData.vcTextChannel), event.getMember().getUser().getName() + "が" + event.getChannelLeft().getName() + "から退出しました。", null);
    }
}
