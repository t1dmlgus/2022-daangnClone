package com.t1dmlgus.daangnClone.chat.application;


import com.t1dmlgus.daangnClone.chat.domain.ChatRoom;
import com.t1dmlgus.daangnClone.chat.domain.ChatRoomRepository;
import com.t1dmlgus.daangnClone.chat.ui.dto.ChatRoomRequestDto;
import com.t1dmlgus.daangnClone.product.domain.Category;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.domain.SaleStatus;
import com.t1dmlgus.daangnClone.product.domain.repository.ProductRepository;
import com.t1dmlgus.daangnClone.user.domain.Role;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.domain.UserRepository;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import com.t1dmlgus.daangnClone.util.RegisterProductTimeFromNow;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
class ChatServiceImplTest {

    @InjectMocks
    private ChatServiceImpl chatServiceImpl;

    @Mock
    private ChatRoomRepository chatRoomRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;

    private static User seller;
    private static User buyer;
    private static Product product;
    private static ChatRoomRequestDto chatRoomRequestDto;


    @BeforeAll
    static void beforeAll() {
        seller = new User(1L, "dmlgus01@gmail.com", "1234", "1의현", "010-1234-1234", "t1dmlgus", Role.ROLE_USER, "박달1동");
        buyer = new User(2L, "dmlgus02@gmail.com", "1234", "2의현", "010-1234-1234", "t2dmlgus", Role.ROLE_USER, "박달2동");
        product = new Product(1L, "상품명", Category.BEAUTY, 2000, "상품내용", SaleStatus.SALE, seller);

        chatRoomRequestDto = new ChatRoomRequestDto(seller.getId(),product.getId(),buyer);
    }



    @DisplayName("서비스 - 채팅방 개설 테스트(기존 채팅방 조회)")
    @Test
    public void createChatRoomReloadTest() throws Exception{
        //given
        doReturn(new ChatRoom(seller, buyer,product)).when(chatRoomRepository).findBySellerIdAndBuyerIdAndProductId(seller.getId(), buyer.getId(), product.getId());

        //when
        ResponseDto<?> chatRoom = chatServiceImpl.createChatRoom(chatRoomRequestDto);
        //then
        assertThat(chatRoom.getMessage()).isEqualTo("채팅방이 조회되었습니다.");
    }

    @DisplayName("서비스 - 채팅방 개설 테스트(채팅방 생성)")
    @Test
    public void createChatRoomCreateTest() throws Exception{
        //given

        doReturn(null).when(chatRoomRepository).findBySellerIdAndBuyerIdAndProductId(seller.getId(), buyer.getId(), product.getId());
        doReturn(Optional.of(seller)).when(userRepository).findById(any(Long.class));
        doReturn(Optional.of(product)).when(productRepository).findById(any(Long.class));
        doReturn(new ChatRoom(1L,"CR_roodId",seller,buyer,product)).when(chatRoomRepository).save(any(ChatRoom.class));

        //when
        ResponseDto<?> chatRoom = chatServiceImpl.createChatRoom(chatRoomRequestDto);
        //then
        assertThat(chatRoom.getMessage()).isEqualTo("채팅방이 생성되었습니다.");
    }


    @DisplayName("서비스 - 채팅방 조회 테스트(채팅방 Id로 조회)")
    @Test
    public void getChatRoomTest() throws Exception{
        //given
        String roomId = "roodId";

        doReturn(new ChatRoom(1L, "CR_roodIc", seller, buyer, product))
                .when(chatRoomRepository).getByRoomId(any(String.class));

        //when
        ResponseDto<?> chatRoom = chatServiceImpl.getChatRoom(roomId);
        //then
        assertThat(chatRoom.getMessage()).isEqualTo("채팅방이 조회되었습니다.");
    }

    @DisplayName("해당 상품에 대한 채팅방 조회 테스트")
    @Test
    public void allChatRoomByProductTest() throws Exception{
        //given
        doReturn(List.of(new ChatRoom(1L, "CR_roodIc", seller, buyer, product)))
                .when(chatRoomRepository).findAllByProductId(any(Long.class));

        //when
        ResponseDto<?> allChatRoomByProduct = chatServiceImpl.allChatRoomByProduct(ChatServiceImplTest.product.getId());

        //then
        assertThat(allChatRoomByProduct.getMessage()).isEqualTo("해당 상품의 채팅목록이 조회되었습니다.");
    }

}