package com.t1dmlgus.daangnClone.product.domain.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t1dmlgus.daangnClone.product.ui.dto.InquiryProductResponseDto;
import com.t1dmlgus.daangnClone.product.ui.dto.TopFourProduct;
import lombok.RequiredArgsConstructor;

import java.util.List;
import static com.t1dmlgus.daangnClone.likes.domain.QLikes.likes;
import static com.t1dmlgus.daangnClone.product.domain.QImage.image;
import static com.t1dmlgus.daangnClone.product.domain.QProduct.product;


@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public InquiryProductResponseDto inquiryProductDetail(Long productId, Long userId) {

        InquiryProductResponseDto inquiryProductResponseDto1 = queryFactory
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
                .where(product.id.eq(productId))
                .distinct()
                .fetchOne();


        List<TopFourProduct> topFourProduct = queryFactory
                .select(Projections.constructor(
                        TopFourProduct.class,
                        product, image.fileName))
                .from(product, image)
                .join(image.product, product)
                .where(product.user.eq(product.user).and(product.id.ne(productId)))//productId
                .distinct()
                .orderBy(product.id.desc())
                .limit(4)
                .fetch();

        inquiryProductResponseDto1.setT4Prods(topFourProduct);

        return inquiryProductResponseDto1;

    }



}
