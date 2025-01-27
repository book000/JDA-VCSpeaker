package com.jaoafa.jdavcspeaker.Command;

import com.jaoafa.jdavcspeaker.Framework.Command.CmdDetail;
import com.jaoafa.jdavcspeaker.Framework.Command.CmdSubstrate;
import com.jaoafa.jdavcspeaker.Lib.LibEmbedColor;
import com.jaoafa.jdavcspeaker.Player.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public class Cmd_Clear implements CmdSubstrate {
    @Override
    public CmdDetail detail() {
        return new CmdDetail()
            .setEmoji(":boom:")
            .setData(
                new CommandData(this.getClass().getSimpleName().substring(4).toLowerCase(), "今までのメッセージの読み上げをすべてキャンセルします")
            );
    }

    @Override
    public void hooker(JDA jda, Guild guild,
                       MessageChannel channel, ChannelType type,
                       Member member, User user,
                       SlashCommandEvent event, String subCmd) {
        clear(guild, event);
    }

    void clear(Guild guild, SlashCommandEvent event) {
        PlayerManager.getINSTANCE().getGuildMusicManager(guild).scheduler.queue.clear();
        PlayerManager.getINSTANCE().getGuildMusicManager(guild).player.destroy();

        cmdFlow.success("%s が読み上げキューをクリアしました。", event.getUser().getAsTag());
        event.replyEmbeds(new EmbedBuilder()
            .setTitle(":stop_button: 読み上げキューをクリアしました！")
            .setColor(LibEmbedColor.success)
            .build()
        ).queue();
    }
}
