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


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Slf4j
public class ChatRoomController {

    private final ChatService chatService;

    //채팅방 목록 조회(판매자)
    @GetMapping(value = "/rooms/{sellerId}")
    public String rooms(@PathVariable Long sellerId, Model model){

        log.info("# All Chat Rooms");
        ResponseDto<?> responseDto = chatService.allChatRoomBySeller(sellerId);
        model.addAttribute("lists", responseDto.getData());

        return "/chat/chatRoomList";
    }


    //채팅방 조회
    @GetMapping("/room")
    public String getRoom(String roomId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){

        log.info("# get Chat Room, roomID : " + roomId);
        ResponseDto<?> chatRoomDto = chatService.getChatRoom(roomId);
        model.addAttribute("room", chatRoomDto.getData());
        model.addAttribute("principal", principalDetails.getUser());
        return "/chat/chatRoom";
    }

}
