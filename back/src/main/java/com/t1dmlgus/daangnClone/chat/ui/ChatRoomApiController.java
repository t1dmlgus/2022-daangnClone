package com.t1dmlgus.daangnClone.chat.ui;

import com.t1dmlgus.daangnClone.auth.domain.PrincipalDetails;
import com.t1dmlgus.daangnClone.chat.application.ChatService;
import com.t1dmlgus.daangnClone.chat.ui.dto.ChatRoomRequestDto;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/chat")
@Slf4j
public class ChatRoomApiController {

    private final ChatService chatService;

    //채팅방 생성 및 조회
    @PostMapping(value = "/room")
    public ResponseEntity<?> chat(@RequestBody ChatRoomRequestDto chatRoomRequestDto, @AuthenticationPrincipal PrincipalDetails principalDetails){

        // 채팅방 생성 요청 DTO
        chatRoomRequestDto.setBuyer(principalDetails.getUser());

        log.info("# Create Chat Room");
        ResponseDto<?> chatRoomDto = chatService.createChatRoom(chatRoomRequestDto);
        return new ResponseEntity<>(chatRoomDto, HttpStatus.OK);
    }


    //채팅방 목록 조회(판매자)
    @GetMapping(value = "/rooms/{productId}")
    public ResponseEntity<?> rooms(@PathVariable Long productId){

        log.info("# All Chat Rooms");
        ResponseDto<?> responseDto = chatService.allChatRoomBySeller(productId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


}
