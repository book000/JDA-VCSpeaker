package com.jaoafa.jdavcspeaker.Event;

import com.jaoafa.jdavcspeaker.Util.EmbedColors;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class AutoMove {
    @SubscribeEvent
    public void onMemberMove(GuildVoiceMoveEvent event) {
        if (event.getMember().getUser().isBot()){
            return;
        }
        if (event.getGuild().getSelfMember().getVoiceState().getChannel() == null||event.getGuild().getSelfMember().getVoiceState().getChannel() == event.getChannelJoined()){
            return;
        }
        String vcName = event.getGuild().getSelfMember().getVoiceState().getChannel().getName();
        //event.getGuild().getAudioManager().closeAudioConnection();

        AudioManager audioManager = event.getGuild().getAudioManager();
        audioManager.openAudioConnection(event.getChannelJoined());

        EmbedBuilder joinSuccess = new EmbedBuilder();
        joinSuccess.setTitle(":white_check_mark: AutoMoved");
        joinSuccess.setDescription("`"+vcName+"`から`"+event.getChannelJoined().getName()+"`に移動しました。");
        joinSuccess.setColor(EmbedColors.success);
        event.getJDA().getTextChannelById("623153228267388958").sendMessage(joinSuccess.build()).queue();
    }
}
