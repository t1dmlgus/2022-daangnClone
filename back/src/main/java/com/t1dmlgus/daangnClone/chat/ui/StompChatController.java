package com.t1dmlgus.daangnClone.chat.ui;

import com.t1dmlgus.daangnClone.chat.application.ChatServiceImpl;
import com.t1dmlgus.daangnClone.chat.ui.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
    private final ChatServiceImpl chatService;

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestination Prefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDto message){

         // (추가예정) 추후 채팅에서 그날 처음 말할때, 오늘 날짜 출력
//        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
//        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDto message){

        message.setTime(LocalDateTime.now());
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}