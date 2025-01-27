package com.jaoafa.jdavcspeaker.Framework.Command;

import com.jaoafa.jdavcspeaker.Lib.LibClassFinder;
import com.jaoafa.jdavcspeaker.Lib.LibFlow;
import com.jaoafa.jdavcspeaker.Lib.LibReporter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.ArrayList;

public class CmdRegister {
    public CmdRegister(JDA jda) {
        LibFlow cmdRegisterFlow = new LibFlow("CmdRegister");
        cmdRegisterFlow.header("Command Register");
        ArrayList<CommandData> commandList = new ArrayList<>();
        try {
            for (Class<?> cmdClass : new LibClassFinder().findClasses("com.jaoafa.jdavcspeaker.Command")) {
                if (!cmdClass.getSimpleName().startsWith("Cmd_")
                    || cmdClass.getEnclosingClass() != null
                    || cmdClass.getName().contains("$")) {
                    cmdRegisterFlow.error("%sはCommandクラスではありません。スキップします...", cmdClass.getSimpleName());
                    continue;
                }
                CmdSubstrate cmd = (CmdSubstrate) cmdClass.getConstructor().newInstance();
                commandList.add(cmd.detail().getData());
                cmdRegisterFlow.success("%sを登録キューに挿入しました。", cmdClass.getSimpleName());
            }
        } catch (Exception e) {
            new LibReporter(null, e);
        }

        //全てのサーバーで登録
        for (Guild guild : jda.getGuilds()) {
            guild.updateCommands().addCommands(commandList).queue(
                s -> cmdRegisterFlow.success("%sへの登録に成功しました。", guild.getName()),
                t -> cmdRegisterFlow.error("%sへの登録に失敗しました。(" + t.getMessage() + ")", guild.getName())
            );
        }
        cmdRegisterFlow.success("全てのGuildにコマンドの登録をリクエストしました。");
    }
}
