package com.t1dmlgus.daangnClone.chat.application;

import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;

public interface ChatService {

    // 채팅방 개설
    ResponseDto<?> createChatRoom(Long userId, User user);

    // 채팅방 목록 조회(판매자)
    ResponseDto<?> allChatRoomByBuyer(Long sellerId);

    // 채팅방 조회
    ResponseDto<?> getChatRoom(String roomId);



}
