package com.t1dmlgus.daangnClone.chat.ui.dto;


import com.t1dmlgus.daangnClone.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatRoomRequestDto {

    private Long sellerId;
    private Long productId;
    private User buyer;

}
