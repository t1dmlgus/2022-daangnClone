package com.t1dmlgus.daangnClone.product.ui.dto;


import com.t1dmlgus.daangnClone.likes.ui.dto.ProductLikesStatus;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.util.RegisterProductTimeFromNow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponseDto {

    private Long productId;
    private String title;
    private int price;
    private String place;
    private boolean likesStatus;
    private int likesCount;
    private String registerTime;

    private String coverImage;


    // 랜딩 dto
    public ProductResponseDto(Product product, String registerTime, ProductLikesStatus productLikesStatus, String coverImage) {
        this.productId = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.place = product.getUser().getPlace();
        this.likesStatus = productLikesStatus.isLikesStatus();
        this.likesCount = productLikesStatus.getLikesCount();
        this.registerTime = registerTime;
        this.coverImage = coverImage;
    }


    // 랜딩 DTO(querydsl)
    public ProductResponseDto(Product product, String coverImage, Boolean likesStatus, Long likesCount) {

        this.productId = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.place = product.getUser().getPlace();
        this.coverImage = coverImage;
        this.likesStatus = likesStatus;
        this.likesCount = likesCount.intValue();
        this.registerTime = RegisterProductTimeFromNow.calculateTime(product.getCreatedDate());
    }
}
