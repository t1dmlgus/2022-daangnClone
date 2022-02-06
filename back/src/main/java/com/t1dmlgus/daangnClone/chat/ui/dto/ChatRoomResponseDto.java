package com.t1dmlgus.daangnClone.chat.ui.dto;

import com.t1dmlgus.daangnClone.chat.domain.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatRoomResponseDto {

    private String buyer;
    private String seller;
    private String roomId;

    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.buyer = chatRoom.getBuyer().getNickName();
        this.seller = chatRoom.getSeller().getNickName();
        this.roomId = chatRoom.getRoomId();
    }
}
