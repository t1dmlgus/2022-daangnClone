package com.t1dmlgus.daangnClone.chat.application;

import com.t1dmlgus.daangnClone.chat.ui.dto.ChatMessageDto;
import com.t1dmlgus.daangnClone.chat.ui.dto.ChatRoomRequestDto;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import java.io.IOException;

public interface ChatService {

    // 채팅방 개설
    ResponseDto<?> createChatRoom(ChatRoomRequestDto chatRoomRequestDto);

    // 채팅방 목록 조회(상품)
    ResponseDto<?> allChatRoomByProduct(Long productId);

    // 채팅방 조회
    ResponseDto<?> getChatRoom(String roomId) throws IOException;

    // 채팅 파일 저장
    void updateChat(ChatMessageDto message) throws IOException;


}
