package com.jaoafa.jdavcspeaker.Player;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public record TrackInfo(SpeakFromType speakFromType, Message message) {
    public Message getMessage() {
        return message;
    }

    public User getUser() {
        return message.getAuthor();
    }

    public TextChannel getChannel() {
        return message.getTextChannel();
    }

    public SpeakFromType getSpeakFromType() {
        return speakFromType;
    }

    @Override
    public String toString() {
        return "TrackInfo{" +
            "speakFromType=" + speakFromType +
            ", message=" + message +
            '}';
    }

    public enum SpeakFromType {
        /** 通常メッセージが送信された */
        RECEIVED_MESSAGE,
        /** ファイルが送信された */
        RECEIVED_FILE,
        /** 画像が送信された */
        RECEIVED_IMAGE,
        /** VCにユーザーが参加した */
        JOINED_VC,
        /** VCでユーザーが移動した */
        MOVED_VC,
        /** VCからユーザーが退出した */
        QUITED_VC,
        /** GoLiveを開始した */
        STARTED_GOLIVE,
        /** GoLiveを終了した */
        ENDED_GOLIVE,
        /** VCタイトルを変えた */
        CHANGED_TITLE
    }
}
