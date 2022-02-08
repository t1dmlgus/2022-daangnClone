package com.t1dmlgus.daangnClone.chat.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatMessageDto {

    private String roomId;
    private String writer;
    private String message;
    private String time;


    public void toSimpleChatTime(LocalDateTime time) {
        this.time = time.format(DateTimeFormatter.ofPattern("a h시 mm분"));
    }
}
