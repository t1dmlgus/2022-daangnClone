package com.t1dmlgus.daangnClone.product.ui.dto;


import com.t1dmlgus.daangnClone.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class InquiryProductTopFourResponseDto {

    private Long productId;

    private String title;
    private int price;

    private String coverImage;

    public InquiryProductTopFourResponseDto(Product product, String coverImage) {
        this.productId = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.coverImage = coverImage;
    }
}
