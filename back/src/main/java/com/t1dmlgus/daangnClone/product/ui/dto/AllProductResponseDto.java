package com.t1dmlgus.daangnClone.product.ui.dto;


import com.t1dmlgus.daangnClone.likes.ui.dto.ProductLikesStatus;
import com.t1dmlgus.daangnClone.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AllProductResponseDto {

    private Long productId;
    private String title;
    private int price;
    private String place;
    private boolean likesStatus;
    private int likesCount;
    private String registerTime;

    private String coverImage;

    public AllProductResponseDto(Product product,String registerTime, ProductLikesStatus productLikesStatus, String coverImage) {
        this.productId = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.place = product.getUser().getPlace();
        this.likesStatus = productLikesStatus.isLikesStatus();
        this.likesCount = productLikesStatus.getLikesCount();
        this.registerTime = registerTime;
        this.coverImage = coverImage;
    }
}
