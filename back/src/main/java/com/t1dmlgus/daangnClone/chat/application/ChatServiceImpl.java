package com.t1dmlgus.daangnClone.chat.application;

import com.t1dmlgus.daangnClone.chat.domain.ChatRoom;
import com.t1dmlgus.daangnClone.chat.domain.ChatRoomRepository;
import com.t1dmlgus.daangnClone.chat.ui.dto.ChatMessageDto;
import com.t1dmlgus.daangnClone.chat.ui.dto.ChatRoomRequestDto;
import com.t1dmlgus.daangnClone.chat.ui.dto.ChatRoomResponseDto;
import com.t1dmlgus.daangnClone.handler.exception.CustomApiException;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.domain.ProductRepository;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.domain.UserRepository;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService{

    @Value("${file.path}")
    String fileUploadPath;

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public ResponseDto<?> createChatRoom(ChatRoomRequestDto chatRoomRequestDto) {

        // 1. 우선 채팅방 조회
        ChatRoom loadChatRoom = chatRoomRepository.findBySellerIdAndBuyerIdAndProductId(chatRoomRequestDto.getSellerId(), chatRoomRequestDto.getBuyer().getId(), chatRoomRequestDto.getProductId());
        if (loadChatRoom != null) {
            return new ResponseDto<>("채팅방이 조회되었습니다.", new ChatRoomResponseDto(loadChatRoom));
        }
        return createChat(chatRoomRequestDto);
    }

    protected ResponseDto<?> createChat(ChatRoomRequestDto chatRoomRequestDto) {

        User seller = userRepository.findById(chatRoomRequestDto.getSellerId()).orElseThrow(
                () -> {return new CustomApiException("해당 유저가 없습니다.");});
        Product product = productRepository.findById(chatRoomRequestDto.getProductId()).orElseThrow(
                () -> { return new CustomApiException("해당 상품은 없는 상품입니다.");});

        ChatRoom createChatRoom = chatRoomRepository.save(new ChatRoom(seller, chatRoomRequestDto.getBuyer(), product));
        return new ResponseDto<>("채팅방이 생성되었습니다.", new ChatRoomResponseDto(createChatRoom));
    }


    @Transactional
    @Override
    public ResponseDto<?> getChatRoom(String roomId) {

        ChatRoom loadChatRoom = chatRoomRepository.getByRoomId(roomId);
        return new ResponseDto<>("채팅방이 조회되었습니다.", new ChatRoomResponseDto(loadChatRoom));
    }


    @Transactional
    @Override
    public ResponseDto<?> allChatRoomBySeller(Long productId) {

        List<ChatRoom> chatRooms = chatRoomRepository.findAllByProductId(productId);

        List<ChatRoomResponseDto> collect = chatRooms.stream().map(ChatRoomResponseDto::new).collect(Collectors.toList());
        return new ResponseDto<>("해당 유저의 채팅목록이 조회되었습니다.", collect);
    }

    public void updateChat(ChatMessageDto message){

        try {
            Path directories = Files.createDirectories(Path.of(fileUploadPath));
            File file = new File(String.valueOf(directories),message.getRoomId() + ".txt");

            if (file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println("{\"roomId\":\""+message.getRoomId()+"\",\"writer\":\""+message.getWriter()+"\",\"message\":" +
                    "\""+message.getMessage()+"\",\"time\":\""+message.getTime()+"\"}");
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}