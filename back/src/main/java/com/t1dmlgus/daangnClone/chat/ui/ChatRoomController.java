package com.t1dmlgus.daangnClone.chat.ui;

import com.t1dmlgus.daangnClone.auth.domain.PrincipalDetails;
import com.t1dmlgus.daangnClone.chat.application.ChatService;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Slf4j
public class ChatRoomController {

    private final ChatService chatService;

    //채팅방 목록 조회(판매자)
    @GetMapping(value = "/rooms/{productId}")
    public String rooms(@PathVariable Long productId, Model model){

        System.out.println("productId = " + productId);

        log.info("# All Chat Rooms");
        ResponseDto<?> responseDto = chatService.allChatRoomBySeller(productId);

        System.out.println("responseDto = " + responseDto);
        model.addAttribute("lists", responseDto.getData());

        return "/chat/chatRoomList";
    }


    //채팅방 조회
    @GetMapping("/room")
    public String getRoom(String roomId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException {

        log.info("# get Chat Room, roomID : " + roomId);
        ResponseDto<?> chatRoomDto = chatService.getChatRoom(roomId);
        model.addAttribute("room", chatRoomDto.getData());
        model.addAttribute("principal", principalDetails.getUser());
        return "/chat/chatRoom";
    }

}
