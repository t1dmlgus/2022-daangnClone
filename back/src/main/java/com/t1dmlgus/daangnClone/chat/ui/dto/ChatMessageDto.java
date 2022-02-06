package com.t1dmlgus.daangnClone.chat.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatMessageDto {

    private String roomId;
    private String writer;
    private String message;
}
