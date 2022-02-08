package com.t1dmlgus.daangnClone.chat.ui.dto;

import com.t1dmlgus.daangnClone.chat.domain.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatRoomResponseDto {

    private String buyer;
    private String seller;
    private String roomId;
    private List<ChatMessageDto> reloadChat;


    public ChatRoomResponseDto(ChatRoom chatRoom, List<ChatMessageDto> reloadChat) {
        this.buyer = chatRoom.getBuyer().getNickName();
        this.seller = chatRoom.getSeller().getNickName();
        this.roomId = chatRoom.getRoomId();
        this.reloadChat = reloadChat;
    }

    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.buyer = chatRoom.getBuyer().getNickName();
        this.seller = chatRoom.getSeller().getNickName();
        this.roomId = chatRoom.getRoomId();
    }
}
