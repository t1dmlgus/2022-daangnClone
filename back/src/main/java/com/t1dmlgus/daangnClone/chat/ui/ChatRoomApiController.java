package com.t1dmlgus.daangnClone.chat.ui;

import com.t1dmlgus.daangnClone.auth.domain.PrincipalDetails;
import com.t1dmlgus.daangnClone.chat.application.ChatService;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/chat")
@Slf4j
public class ChatRoomApiController {

    private final ChatService chatService;

    //채팅방 생성 및 조회
    @PostMapping(value = "/room/{userId}")
    public ResponseEntity<?> chat(@PathVariable Long userId, @AuthenticationPrincipal PrincipalDetails principalDetails){

        log.info("# Create Chat Room");
        ResponseDto<?> chatRoomDto = chatService.createChatRoom(userId, principalDetails.getUser());
        return new ResponseEntity<>(chatRoomDto, HttpStatus.OK);
    }

}
