package com.t1dmlgus.daangnClone.likes.domain;

import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.domain.SaleStatus;
import com.t1dmlgus.daangnClone.user.domain.Role;
import com.t1dmlgus.daangnClone.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LikesRepositoryTest {

    @Autowired
    private LikesRepository likesRepository;

    private static User testUser;
    private static Product testProduct;

    @BeforeAll
    static void beforeAll() {
        testUser = new User(1L, "dmlgusgngl@gmail.com", "1234", "이의현", "010-1234-1234", "t1dmlgus", Role.ROLE_USER);
        testProduct = new Product(3L, "상품명", null, 2000, "상품내용", SaleStatus.SALE, testUser);
    }

    @DisplayName("레포지토리 - 좋아요 상태 확인 테스트")
    @Test
    public void checkLikesStatus() throws Exception {
        //given
        
        //when
        boolean likeStatus = likesRepository.existsByProductIdAndUserId(testProduct.getId(), testUser.getId());

        //then
        Assertions.assertThat(likeStatus).isEqualTo(true);
    }

}