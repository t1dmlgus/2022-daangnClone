package com.t1dmlgus.daangnClone.chat.application;

import com.t1dmlgus.daangnClone.chat.domain.ChatRoom;
import com.t1dmlgus.daangnClone.chat.domain.ChatRoomRepository;
import com.t1dmlgus.daangnClone.chat.ui.dto.ChatRoomResponseDto;
import com.t1dmlgus.daangnClone.handler.exception.CustomApiException;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.domain.UserRepository;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService{

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;


    @Transactional
    @Override
    public ResponseDto<?> createChatRoom(Long sellerId, User buyer) {

        // 1. 우선 채팅방 조회
        ChatRoom loadChatRoom = chatRoomRepository.findBySellerIdAndBuyerId(sellerId, buyer.getId());

        // 2. 없으면 생성
        if (loadChatRoom == null) {
            User seller = userRepository.findById(sellerId).orElseThrow(
                    () -> {return new CustomApiException("해당 유저가 없습니다.");});
            ChatRoom createChatRoom = chatRoomRepository.save(new ChatRoom(seller, buyer));
            return new ResponseDto<>("채팅방이 생성되었습니다.", new ChatRoomResponseDto(createChatRoom));
        }

        return new ResponseDto<>("채팅방이 조회되었습니다.", new ChatRoomResponseDto(loadChatRoom));
    }


    @Transactional
    @Override
    public ResponseDto<?> getChatRoom(String roomId) {

        ChatRoom loadChatRoom = chatRoomRepository.getByRoomId(roomId);
        return new ResponseDto<>("채팅방이 조회되었습니다.", new ChatRoomResponseDto(loadChatRoom));
    }


    @Transactional
    @Override
    public ResponseDto<?> allChatRoomBySeller(Long sellerId) {

        List<ChatRoom> chatRooms = chatRoomRepository.findAllBySellerId(sellerId);

        List<ChatRoomResponseDto> collect = chatRooms.stream().map(ChatRoomResponseDto::new).collect(Collectors.toList());
        return new ResponseDto<>("해당 유저의 채팅목록이 조회되었습니다.", collect);
    }

}