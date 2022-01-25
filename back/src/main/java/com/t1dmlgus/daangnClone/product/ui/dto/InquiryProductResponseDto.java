package com.t1dmlgus.daangnClone.product.ui.dto;

import com.t1dmlgus.daangnClone.likes.ui.dto.ProductLikesStatus;
import com.t1dmlgus.daangnClone.product.domain.Category;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.domain.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InquiryProductResponseDto {

    private Long productId;

    private String title;

    private int price;

    private Category category;

    private LocalDateTime createTime;

    private SaleStatus status;

    private String nickName;

    private String caption;

    private String place;

    private List<String> images;

    List<InquiryProductTopFourResponseDto> t4Prods;

    private boolean likeStatus;

    public InquiryProductResponseDto(Product product, List<String> images, ProductLikesStatus productLikesStatus, List<InquiryProductTopFourResponseDto> t4Prod) {

        this.productId = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.category = product.getCategory();
        this.createTime = product.getCreatedDate();
        this.category = product.getCategory();
        this.status = product.getStatus();
        this.nickName = product.getUser().getNickName();
        this.caption = product.getCaption();
        this.place = product.getUser().getPlace();
        this.images = images;
        this.likeStatus = productLikesStatus.isLikesStatus();
        this.t4Prods = t4Prod;

    }
}
