package com.jaoafa.jdavcspeaker.Framework;

import com.jaoafa.jdavcspeaker.Framework.Action.ActionSubstrate;
import com.jaoafa.jdavcspeaker.Framework.Command.CmdSubstrate;
import com.jaoafa.jdavcspeaker.Lib.LibReporter;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;

public class FunctionHooker extends ListenerAdapter {
    final String ROOT_PACKAGE = "com.jaoafa.jdavcspeaker";

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        String subCmdGroup = event.getSubcommandGroup();
        String subCmdName = event.getSubcommandName();

        boolean isSubCmdOnly = subCmdGroup == null && subCmdName != null;
        boolean isSubCmdAndGroup = subCmdGroup != null && subCmdName != null;

        String subCmd = null;
        if (isSubCmdOnly) subCmd = subCmdName;
        if (isSubCmdAndGroup) subCmd = "%s:%s".formatted(subCmdGroup, subCmdName);

        execute(new FunctionContainer(
            FunctionType.Command,
            event.getName(),
            subCmd,
            null,
            event.getJDA(),
            event.getGuild(),
            event.getChannel(),
            event.getChannelType(),
            event.getMember(),
            event.getUser(),
            event,
            null,
            null
        ));
    }

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        String[] buttonData = event.getId().split(":json");
        String buttonId = buttonData[0];
        JSONObject buttonJSON = new JSONObject(buttonData[1]);
        execute(new FunctionContainer(
            FunctionType.Action,
            buttonId,
            null,
            buttonJSON,
            event.getJDA(),
            event.getGuild(),
            event.getChannel(),
            event.getChannelType(),
            event.getMember(),
            event.getUser(),
            event,
            event.getMessage(),
            event.getButton()
        ));
    }

    private void execute(FunctionContainer container) {
        Object substrate;
        try {
            substrate = Class
                .forName("%s.%s.Cmd_%s".formatted(ROOT_PACKAGE, container.functionType(), container.functionName()))
                .getConstructor()
                .newInstance();
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            new LibReporter(container.channel(), e);
            return;
        }

        switch (container.functionType()) {
            case Command -> ((CmdSubstrate) substrate)
                .hooker(
                    container.jda(),
                    container.guild(),
                    container.channel(),
                    container.channelType(),
                    container.member(),
                    container.user(),
                    (SlashCommandEvent) container.event(),
                    container.subFunction()
                );
            case Action -> ((ActionSubstrate) substrate)
                .hooker(
                    container.jda(),
                    container.guild(),
                    container.channel(),
                    container.channelType(),
                    container.member(),
                    container.user(),
                    container.message(),
                    container.button(),
                    (ButtonClickEvent) container.event(),
                    container.data()
                );
        }
    }
}
