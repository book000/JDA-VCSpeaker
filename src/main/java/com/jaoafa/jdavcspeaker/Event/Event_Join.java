package com.jaoafa.jdavcspeaker.Event;

import com.jaoafa.jdavcspeaker.StaticData;
import com.jaoafa.jdavcspeaker.Util.EmbedColors;
import com.jaoafa.jdavcspeaker.Util.VoiceText;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class Event_Join {
    @SubscribeEvent
    public void onMemberJoin(GuildVoiceJoinEvent event) {
        if (event.getMember().getUser().isBot()){
            return;
        }
        event.getJDA().getTextChannelById(StaticData.vcTextChannel).sendMessage(":outbox_tray: `"+event.getMember().getUser().getName()+"`が`"+event.getChannelJoined().getName()+"`に参加しました。").queue();
        VoiceText.speak(event.getJDA().getTextChannelById(StaticData.vcTextChannel),event.getMember().getUser().getName()+"が"+event.getChannelJoined().getName()+"に参加しました。");
    }
}
