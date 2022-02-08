package com.t1dmlgus.daangnClone.chat.ui;

import com.t1dmlgus.daangnClone.chat.application.ChatServiceImpl;
import com.t1dmlgus.daangnClone.chat.ui.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
    private final ChatServiceImpl chatService;

    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDto message){

         // (추가예정) 해당 요일 채팅에서 처음 말할때, 상단에 오늘 날짜 출력
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDto message) throws IOException {

        message.toSimpleChatTime(LocalDateTime.now());
        chatService.updateChat(message);
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}