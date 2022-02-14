package com.t1dmlgus.daangnClone.product.domain.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t1dmlgus.daangnClone.handler.aop.LogExecutionTime;
import com.t1dmlgus.daangnClone.product.domain.Category;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.domain.SaleStatus;
import com.t1dmlgus.daangnClone.product.ui.dto.InquiryProductResponseDto;
import com.t1dmlgus.daangnClone.product.ui.dto.TopFourProduct;
import com.t1dmlgus.daangnClone.user.domain.Role;
import com.t1dmlgus.daangnClone.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.t1dmlgus.daangnClone.likes.domain.QLikes.likes;
import static com.t1dmlgus.daangnClone.product.domain.QImage.image;
import static com.t1dmlgus.daangnClone.product.domain.QProduct.product;

@Transactional
@SpringBootTest
class ProductRepositoryImplTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;


    private User testUser;
    private Product testProduct;


    @BeforeEach

    public void beforeEach() {

        queryFactory = new JPAQueryFactory(em);

        testUser = new User("dmlgusgngl@gmail.com", "1234", "이의현", "010-1234-1234", "t1dmlgus", Role.ROLE_USER, "박달1동");
        testProduct = new Product( "상품명", 20000, "상품내용", SaleStatus.SALE, Category.BEAUTY, testUser);       //em.persist(testProduct);

        em.persist(testUser);
        em.persist(testProduct);

//        em.flush();
//        em.clear();
    }


    @LogExecutionTime
    @Test
    public void inquiryProductDetail() throws Exception {
        //given
        boolean contains = em.contains(testUser);
        System.out.println("contains = " + contains);

        //when
        // 유저: 2L, 상품: 19L
        InquiryProductResponseDto inquiryProductResponseDto = queryFactory
                .select(Projections.constructor(InquiryProductResponseDto.class,
                        product,
                        ExpressionUtils.as(
                                image.fileName, "coverImage"),
                        ExpressionUtils.as(
                                JPAExpressions.selectFrom(likes).where(likes.user.id.eq(2L).and(likes.product.eq(product))).exists(), "likesStatus"),
                        ExpressionUtils.as(
                                JPAExpressions.select(likes.count()).from(likes).where(likes.product.eq(product)), "likesCount")))
                .from(product, image)
                .join(image.product, product)
                .where(product.id.eq(19L))
                .limit(1)
                .fetchOne();

        System.out.println("inquiryProductResponseDto = " + inquiryProductResponseDto);

        //then
    }


    @LogExecutionTime
    @Test
    public void top4() throws Exception{
        //given

        //when
        List<TopFourProduct> topFourProduct = queryFactory
                .select(Projections.constructor(
                        TopFourProduct.class,
                        product, image.fileName))
                .from(product, image)
                .join(image.product, product)
                .where(product.user.eq(product.user).and(product.id.ne(20L)))//productId
                .distinct()
                .limit(4)
                .fetch();

        for (TopFourProduct fetch1 : topFourProduct) {
            System.out.println("fetch1 = " + fetch1);
        }

        //then
    }


    @Test
    public void productAll() throws Exception{
        //given

        Long userId = 2L;
        PageRequest page = PageRequest.of(0, 10);


        //when
        QueryResults<InquiryProductResponseDto> randingProducts = queryFactory
                .select(Projections.constructor(InquiryProductResponseDto.class,
                        product,
                        ExpressionUtils.as(
                                image.fileName, "coverImage"),
                        ExpressionUtils.as(
                                JPAExpressions.selectFrom(likes).where(likes.user.id.eq(userId).and(likes.product.eq(product))).exists(), "likesStatus"),
                        ExpressionUtils.as(
                                JPAExpressions.select(likes.count()).from(likes).where(likes.product.eq(product)), "likesCount")))
                .from(product, image)
                .join(image.product, product)
                .distinct()
                .offset(page.getOffset())
                .limit(page.getPageSize())
                .fetchResults();

        PageImpl<InquiryProductResponseDto> inquiryProductResponseDtos = new PageImpl<>(randingProducts.getResults(), page, randingProducts.getTotal());

        for (InquiryProductResponseDto inquiryProductResponseDto : inquiryProductResponseDtos) {
            System.out.println("inquiryProductResponseDto = " + inquiryProductResponseDto);
        }
        //then

    }

}