package com.t1dmlgus.daangnClone.likes.application;


import com.t1dmlgus.daangnClone.likes.domain.LikesRepository;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.domain.SaleStatus;
import com.t1dmlgus.daangnClone.user.domain.Role;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@ExtendWith(MockitoExtension.class)
class LikesServiceImplTest {

    @InjectMocks
    private LikesServiceImpl likesServiceImpl;

    @Mock
    private LikesRepository likesRepository;


    private static User testUser;
    private static Product testProduct;


    @BeforeAll
    static void beforeAll() {
        testUser = new User(1L, "dmlgusgngl@gmail.com", "1234", "이의현", "010-1234-1234", "t1dmlgus", Role.ROLE_USER);
        testProduct = new Product(1L, "상품명", null, 2000, "상품내용", SaleStatus.SALE, testUser);
    }


    @DisplayName("서비스 - 좋아요 추가 테스트")
    @Test
    public void addLikesTest() throws Exception{
        //given
        //doNothing().when(likesRepository).likes(testUser.getId(), testProduct.getId());

        //when
        ResponseDto<?> addLikesDto = likesServiceImpl.addLikes(testProduct.getId(), testUser.getId());

        //then
        assertThat(addLikesDto.getMessage()).isEqualTo("좋아요가 추가되었습니다.");
    }

    @DisplayName("서비스 - 좋아요 취소 테스트")
    @Test
    public void unLikesTest() throws Exception{
        //given

        //when
        ResponseDto<?> unLikesDto = likesServiceImpl.unLikes(testProduct.getId(), testUser.getId());

        //then
        assertThat(unLikesDto.getMessage()).isEqualTo("좋아요가 취소되었습니다.");
    }



}