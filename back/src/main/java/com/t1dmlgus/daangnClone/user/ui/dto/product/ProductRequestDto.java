package com.t1dmlgus.daangnClone.user.ui.dto.product;

import com.t1dmlgus.daangnClone.product.domain.Category;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRequestDto {

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    //@NotBlank(message = "카테고리를 골라주세요")
    private Category category;

    @NotBlank(message = "가격을 입력해주세요")
    private int price;

    @NotBlank(message = "본문을 입력해주세요")
    private String caption;


    public Product toEntity(User user){

        return Product.builder()
                .title(title)
                .price(price)
                .caption(caption)
                .user(user)
                .build();
    }
}
